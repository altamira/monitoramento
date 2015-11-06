package br.com.altamira.monitoramento.controller;

import java.io.IOException;
import java.util.Date;
import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;

import br.com.altamira.monitoramento.model.IHM;
import br.com.altamira.monitoramento.model.IHMLog;
import br.com.altamira.monitoramento.model.MaquinaLog;
import br.com.altamira.monitoramento.model.MaquinaLogParametro;
import br.com.altamira.monitoramento.msg.ParametroMsg;
import br.com.altamira.monitoramento.msg.StatusMsg;
import br.com.altamira.monitoramento.repository.IHMLogRepository;
import br.com.altamira.monitoramento.repository.IHMRepository;
import br.com.altamira.monitoramento.repository.MaquinaLogParametroRepository;
import br.com.altamira.monitoramento.repository.MaquinaLogRepository;
import br.com.altamira.monitoramento.repository.MaquinaRepository;
import br.com.altamira.monitoramento.repository.MedidaRepository;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
public class MonitoramentoController {

	@Autowired
	private MaquinaRepository maquinaRepository;
	
	@Autowired
	private MaquinaLogRepository maquinaLogRepository;
	
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
		System.out.println(String.format(
				"\n--------------------------------------------------------------------------------\nCHEGOU MENSAGEM DE IHM-STATUS\n--------------------------------------------------------------------------------\n%s\n--------------------------------------------------------------------------------\n", msg));
		
		ObjectMapper mapper = new ObjectMapper();
		StatusMsg statusMsg = mapper.readValue(msg, StatusMsg.class);
		
		IHM ihm = ihmRepository.findOne(statusMsg.getIHM());
		
		if (ihm == null) {
			System.out.println(String.format(
					"\n--------------------------------------------------------------------------------\nIHM NAO CADASTRADA: %s\n--------------------------------------------------------------------------------\n", statusMsg.getIHM()));
			return;
		}
		
		IHMLog ihmLog = new IHMLog(
				statusMsg.getIHM().toUpperCase(),
				statusMsg.getDatahora(),
				statusMsg.getModo(),
				statusMsg.getTempo(),
				statusMsg.getOperador()
				);
		
		ihmLogRepository.saveAndFlush(ihmLog);
		
		System.out.println(String.format("IHM LOG ID: %d", ihmLog.getId()));
		
		if (ihm.getMaquina() != null) {
			
			MaquinaLog maquinaLog = new MaquinaLog(
					ihm.getMaquina().toUpperCase(),
					statusMsg.getDatahora(),
					statusMsg.getModo(),
					statusMsg.getTempo(),
					statusMsg.getOperador()
					);
			
			maquinaLogRepository.saveAndFlush(maquinaLog);
			
			System.out.println(String.format("LOG ID: %d", maquinaLog.getId()));
			
			maquinaLog.setParametros(new HashSet<MaquinaLogParametro>());
			
			if (statusMsg.getParametros() != null) {
				for (ParametroMsg medidaMsg : statusMsg.getParametros()) {
					MaquinaLogParametro maquinaLogParametro = new MaquinaLogParametro(
							maquinaLog.getId(), 
							medidaMsg.getMedida(), 
							medidaMsg.getUnidade(), 
							medidaMsg.getValor());
					maquinaLogParametroRepository.saveAndFlush(maquinaLogParametro);
				}
			}
			
		}
		
		String approximateFirstReceiveTimestamp = String.valueOf(new Date().getTime());
		
        try {
            this.sendingTextWebSocketHandler.broadcastToSessions(new DataWithTimestamp<StatusMsg>(statusMsg, approximateFirstReceiveTimestamp));
        } catch (IOException e) {
        	System.out.println(String.format("\n********************************************************************************\nWas not able to push the message to the client.\n********************************************************************************\n%s\n********************************************************************************\n", e.getMessage()));
        }
	
		
	}
	
}
