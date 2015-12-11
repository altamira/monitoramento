package br.com.altamira.monitoramento.controller;

import java.io.IOException;
import java.util.Date;
import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;

import br.com.altamira.monitoramento.model.IHM;
import br.com.altamira.monitoramento.model.IHMLog;
import br.com.altamira.monitoramento.model.IHMLogErro;
import br.com.altamira.monitoramento.model.Maquina;
import br.com.altamira.monitoramento.model.MaquinaLog;
import br.com.altamira.monitoramento.model.MaquinaLogErro;
import br.com.altamira.monitoramento.model.MaquinaLogParametro;
import br.com.altamira.monitoramento.msg.ParametroMsg;
import br.com.altamira.monitoramento.msg.StatusMsg;
import br.com.altamira.monitoramento.repository.IHMLogErroRepository;
import br.com.altamira.monitoramento.repository.IHMLogRepository;
import br.com.altamira.monitoramento.repository.IHMRepository;
import br.com.altamira.monitoramento.repository.MaquinaLogErroRepository;
import br.com.altamira.monitoramento.repository.MaquinaLogParametroRepository;
import br.com.altamira.monitoramento.repository.MaquinaLogRepository;
import br.com.altamira.monitoramento.repository.MaquinaRepository;
import br.com.altamira.monitoramento.repository.MedidaRepository;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
@RequestMapping("/api/monitor")
public class MonitoramentoController {

	@Autowired
	private MaquinaRepository maquinaRepository;
	
	@Autowired
	private MaquinaLogRepository maquinaLogRepository;
	
	@Autowired
	private MaquinaLogErroRepository maquinaLogErroRepository;	
	
	@Autowired
	private MaquinaLogParametroRepository maquinaLogParametroRepository;

	@Autowired
	private IHMRepository ihmRepository;
	
	@Autowired
	private IHMLogRepository ihmLogRepository;
	
	@Autowired
	private IHMLogErroRepository ihmLogErroRepository;	
	
	@Autowired
	private MedidaRepository medidaRepository;
	
	@Autowired
	@Qualifier("WebSocketHandler") 
	private SendingTextWebSocketHandler sendingTextWebSocketHandler;

	@Transactional
	@JmsListener(destination = "IHM-STATUS")
	public void monitoramentoStatus(String msg) throws JsonParseException, JsonMappingException, IOException {
		System.out.println(String.format(
				"\n--------------------------------------------------------------------------------\nCHEGOU MENSAGEM DE IHM-STATUS\n--------------------------------------------------------------------------------\n%s\n--------------------------------------------------------------------------------\n", msg));

		ObjectMapper mapper = new ObjectMapper();
		StatusMsg statusMsg = mapper.readValue(msg, StatusMsg.class);
		
		IHM ihm = ihmRepository.findOne(statusMsg.getIHM());
		
		if (ihm == null) {
			System.out.println(String.format(
					"\n--------------------------------------------------------------------------------\nIHM NAO CADASTRADA: %s\n--------------------------------------------------------------------------------\n", statusMsg.getIHM()));
			
			ihm = new IHM(statusMsg.getIHM());
			
			IHMLogErro ihmLogErro = new IHMLogErro(new Date(), statusMsg.getIHM().toUpperCase(), "IHM nao cadastrada", msg);
			ihmLogErroRepository.saveAndFlush(ihmLogErro);
		
			return;
		}
		
		if (statusMsg.getTempo() < 0 || statusMsg.getTempo() > 45) {
			System.out.println(String.format(
					"\n--------------------------------------------------------------------------------\nINTERVALO DE TEMPO INVALIDO: %s %d\n--------------------------------------------------------------------------------\n", statusMsg.getIHM().toUpperCase(), statusMsg.getTempo()));

			IHMLogErro ihmLogErro = new IHMLogErro(new Date(), ihm.getCodigo().toUpperCase(), String.format("Intervalo de tempo invalido, esperado entre 0s e 45s, recebido %ds", statusMsg.getTempo()), msg);
			ihmLogErroRepository.saveAndFlush(ihmLogErro);

			statusMsg.setTempo(0);
		}
		
		if ((statusMsg.getSequencia() < 0) || (statusMsg.getSequencia() > 0 && ihm.getSequencia() + 1 != statusMsg.getSequencia())) {
			System.out.println(String.format(
					"\n--------------------------------------------------------------------------------\nERRO DE SEQUENCIA: %s\n--------------------------------------------------------------------------------\n", ihm.getMaquina().toUpperCase()));
			
			IHMLogErro ihmLogErro = new IHMLogErro(new Date(), ihm.getCodigo().toUpperCase(), String.format("Erro de sequencia, esperado %d, recebido %d", ihm.getSequencia() + 1, statusMsg.getSequencia()), msg);
			ihmLogErroRepository.saveAndFlush(ihmLogErro);
			
			if (statusMsg.getSequencia() < 0) return; // sequencia invalida
			if (statusMsg.getSequencia() < ihm.getSequencia() + 1) { // pacote duplicado ou perda de pacote
				ihmLogErro = new IHMLogErro(new Date(), ihm.getCodigo().toUpperCase(), String.format("ALERTA: Possivel perda de pacote ou pacote duplicado, esperado %d, recebido %d", ihm.getSequencia() + 1, statusMsg.getSequencia()), msg);
				ihmLogErroRepository.saveAndFlush(ihmLogErro);
			}
		}
		
		if (ihm.getSituacao() == statusMsg.getModo()) {
			ihm.setTempo(ihm.getTempo() + statusMsg.getTempo());
		} else {
			ihm.setTempo(statusMsg.getTempo());
		}
		
		ihm.setOperador(statusMsg.getOperador());
		ihm.setVersao(statusMsg.getVersao());
		ihm.setSituacao(statusMsg.getModo());
		ihm.setSequencia(statusMsg.getSequencia());
		ihm.setAtualizacao(new Date());
		ihm.setFalhaComunicacao(0);
		
		ihmRepository.saveAndFlush(ihm);
		
		IHMLog ihmLog = new IHMLog(
				statusMsg.getIHM().toUpperCase(),
				statusMsg.getDatahora(),
				statusMsg.getModo(),
				statusMsg.getSequencia(),
				statusMsg.getTempo(),
				statusMsg.getOperador()
				);
		
		ihmLogRepository.saveAndFlush(ihmLog);
		
		if (ihm.getMaquina() != null && !ihm.getMaquina().isEmpty()) {
			
			Maquina maquina = maquinaRepository.findOne(ihm.getMaquina().toUpperCase());
			
			if (maquina == null) {
				System.out.println(String.format(
						"\n--------------------------------------------------------------------------------\nMAQUINA NAO ENCONTRADA: %s\n--------------------------------------------------------------------------------\n", ihm.getMaquina().toUpperCase()));
				
				MaquinaLogErro maquinaLogErro = new MaquinaLogErro(new Date(), maquina.getCodigo().toUpperCase(), String.format("Maquina nao encontrada %s", ihm.getMaquina().toUpperCase()), msg);
				maquinaLogErroRepository.saveAndFlush(maquinaLogErro);
				
				return;
			} else {
				statusMsg.setMaquina(maquina.getCodigo());
			}
			
			if (statusMsg.getSequencia() > 0 && maquina.getSequencia() + 1 != statusMsg.getSequencia()) {
				System.out.println(String.format(
						"\n--------------------------------------------------------------------------------\nERRO DE SEQUENCIA: %s\n--------------------------------------------------------------------------------\n", ihm.getMaquina().toUpperCase()));
				
				MaquinaLogErro maquinaLogErro = new MaquinaLogErro(new Date(), maquina.getCodigo().toUpperCase(), String.format("Erro de sequencia, esperado %d, recebido %d", maquina.getSequencia() + 1, statusMsg.getSequencia()), msg);
				maquinaLogErroRepository.saveAndFlush(maquinaLogErro);
			}
			
			if (maquina.getSituacao() == statusMsg.getModo()) {
				maquina.setTempo(maquina.getTempo() + statusMsg.getTempo());
			} else {
				maquina.setTempo(statusMsg.getTempo());
			}
		
			maquina.setSituacao(statusMsg.getModo());
			maquina.setSequencia(statusMsg.getSequencia());
			maquina.setOperador(statusMsg.getOperador());
			maquina.setAtualizacao(new Date());
			maquina.setFalhaComunicacao(0);
			
			maquinaRepository.saveAndFlush(maquina);
			
			MaquinaLog maquinaLog = new MaquinaLog(
					maquina.getCodigo().toUpperCase(),
					statusMsg.getDatahora(),
					statusMsg.getModo(),
					statusMsg.getSequencia(),
					statusMsg.getTempo(),
					statusMsg.getOperador()
					);
			
			maquinaLogRepository.saveAndFlush(maquinaLog);
			
			/* FUNCAO TEMPORARIAMENTE DESABILITADA
			 * PARA EVITAR ARMAZENAMENTO DE LOGS DESNECESSARIOS POIS 
			 * A LEITURA DOS PARAMETROS NAO ESTA IMPLANTADA NA IHM POR ENQUANTO
			 */
			/*if (statusMsg.getParametros() != null) {
				maquinaLog.setParametros(new HashSet<MaquinaLogParametro>());
				
				for (ParametroMsg medidaMsg : statusMsg.getParametros()) {
					MaquinaLogParametro maquinaLogParametro = new MaquinaLogParametro(
							maquinaLog.getId(), 
							medidaMsg.getMedida(), 
							medidaMsg.getUnidade(), 
							medidaMsg.getValor());
					maquinaLogParametroRepository.saveAndFlush(maquinaLogParametro);
				}
			}*/
			
			statusMsg.setTempo(maquina.getTempo());
			statusMsg.setRecebidoEm(new Date());
			
		}
		
		String approximateFirstReceiveTimestamp = String.valueOf(new Date().getTime());
		
        try {
            this.sendingTextWebSocketHandler.broadcastToSessions(new DataWithTimestamp<StatusMsg>(statusMsg, approximateFirstReceiveTimestamp));
        } catch (IOException e) {
        	System.out.println(String.format("\n********************************************************************************\nErro de broadcast para o clientes conectados via JSocket.\n********************************************************************************\n%s\n********************************************************************************\n", e.getMessage()));

        	MaquinaLogErro maquinaLogErro = new MaquinaLogErro(new Date(), statusMsg.getIHM().toUpperCase(), "Erro de broadcast", msg);
			maquinaLogErroRepository.saveAndFlush(maquinaLogErro);
        }
		
	}
	
}
