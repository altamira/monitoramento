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
import br.com.altamira.monitoramento.model.Maquina;
import br.com.altamira.monitoramento.model.MaquinaLog;
import br.com.altamira.monitoramento.model.MaquinaLogErro;
import br.com.altamira.monitoramento.model.MaquinaLogParametro;
import br.com.altamira.monitoramento.msg.ParametroMsg;
import br.com.altamira.monitoramento.msg.StatusMsg;
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
	private MedidaRepository medidaRepository;
	
	@Autowired
	@Qualifier("WebSocketHandler") 
	private SendingTextWebSocketHandler sendingTextWebSocketHandler;

	@Transactional
	@JmsListener(destination = "IHM-STATUS")
	public void monitoramentoStatus(String msg) throws JsonParseException, JsonMappingException, IOException {
		ObjectMapper mapper = new ObjectMapper();
		StatusMsg statusMsg = mapper.readValue(msg, StatusMsg.class);
		
		if (statusMsg.getTempo() > 35) {
			System.out.println(String.format(
					"\n--------------------------------------------------------------------------------\nINTERVALO DE TEMPO INVALIDO: %s\n--------------------------------------------------------------------------------\n", statusMsg.getIHM().toUpperCase()));

			MaquinaLogErro maquinaLogErro = new MaquinaLogErro(new Date(), statusMsg.getIHM().toUpperCase(), String.format("Intervalo de tempo invalido, intevalo recebido %d", statusMsg.getTempo()), msg);
			maquinaLogErroRepository.saveAndFlush(maquinaLogErro);

		}
		
		IHM ihm = ihmRepository.findOne(statusMsg.getIHM());
		
		if (ihm == null) {
			System.out.println(String.format(
					"\n--------------------------------------------------------------------------------\nIHM NAO CADASTRADA: %s\n--------------------------------------------------------------------------------\n", statusMsg.getIHM()));
			
			ihm = new IHM(statusMsg.getIHM());
			
			MaquinaLogErro maquinaLogErro = new MaquinaLogErro(new Date(), statusMsg.getIHM().toUpperCase(), "IHM nao cadastrada", msg);
			maquinaLogErroRepository.saveAndFlush(maquinaLogErro);
		
		}
		
		ihm.setOperador(statusMsg.getOperador());
		ihm.setVersao(statusMsg.getVersao());
		
		ihmRepository.saveAndFlush(ihm);
		
		System.out.println(String.format(
				"\n--------------------------------------------------------------------------------\nCHEGOU MENSAGEM DE IHM-STATUS\n--------------------------------------------------------------------------------\n%s\n--------------------------------------------------------------------------------\n", msg));
		
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
			
			if (statusMsg.getParametros() != null) {
				maquinaLog.setParametros(new HashSet<MaquinaLogParametro>());
				
				for (ParametroMsg medidaMsg : statusMsg.getParametros()) {
					MaquinaLogParametro maquinaLogParametro = new MaquinaLogParametro(
							maquinaLog.getId(), 
							medidaMsg.getMedida(), 
							medidaMsg.getUnidade(), 
							medidaMsg.getValor());
					maquinaLogParametroRepository.saveAndFlush(maquinaLogParametro);
				}
			}
			
			statusMsg.setTempo(maquina.getTempo());
			statusMsg.setRecebidoEm(new Date());
			
		}
		
		String approximateFirstReceiveTimestamp = String.valueOf(new Date().getTime());
		
        try {
            this.sendingTextWebSocketHandler.broadcastToSessions(new DataWithTimestamp<StatusMsg>(statusMsg, approximateFirstReceiveTimestamp));
        } catch (IOException e) {
        	MaquinaLogErro maquinaLogErro = new MaquinaLogErro(new Date(), statusMsg.getIHM().toUpperCase(), "Erro de broadcast", msg);
			maquinaLogErroRepository.saveAndFlush(maquinaLogErro);
        	System.out.println(String.format("\n********************************************************************************\nErro de broadcast para o clientes conectados via JSocket.\n********************************************************************************\n%s\n********************************************************************************\n", e.getMessage()));
        }
		
	}
	
}
