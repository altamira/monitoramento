package br.com.altamira.monitoramento.controller;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import br.com.altamira.monitoramento.model.IHM;
import br.com.altamira.monitoramento.model.Maquina;
import br.com.altamira.monitoramento.model.MaquinaLogErro;
import br.com.altamira.monitoramento.msg.StatusMsg;
import br.com.altamira.monitoramento.repository.IHMRepository;
import br.com.altamira.monitoramento.repository.MaquinaLogErroRepository;
import br.com.altamira.monitoramento.repository.MaquinaRepository;

@Component
public class MonitorScheduler {

	@Autowired
	private MaquinaRepository maquinaRepository;
	
	@Autowired
	private IHMRepository ihmRepository;
	
	@Autowired
	@Qualifier("WebSocketHandler") 
	private SendingTextWebSocketHandler sendingTextWebSocketHandler;
	
	@Autowired
	private MaquinaLogErroRepository maquinaLogErroRepository;		

	final static long SECONDS_PER_DAY = 24 * 3600;
	
	@Scheduled(fixedDelay = 30000)
	// @Scheduled(fixedRate = 5000)
	public void demoServiceMethod() {
		System.out.println(String.format(
				"\n--------------------------------------------------------------------------------\nVerificando o estado das conexoes das maquinas...\n--------------------------------------------------------------------------------\n"));

		List<Maquina> maquinas = maquinaRepository.findByDesatualizadas(60);
		
		for (Maquina maquina : maquinas) {

			if (maquina.getSituacao() != 7) {

				System.out.println(String.format(
						"\n--------------------------------------------------------------------------------\nTIMEOUT na conexao com a maquina %s %s\n--------------------------------------------------------------------------------\n", maquina.getCodigo(), maquina.getNome()));
				
				//maquina.setSituacao(99);
				//maquina.setTempo(0);
				//maquina.setOperador("");
				//maquina.setAtualizacao(new Date());
				
				//maquinaRepository.saveAndFlush(maquina);
				
				List<IHM> ihms = ihmRepository.findAllByMaquina(maquina.getCodigo());
				
				if (ihms != null && !ihms.isEmpty()) {
					for (IHM ihm : ihms) {
						ihm.setVersao("?");
						ihmRepository.saveAndFlush(ihm);
					}
				}
				
				long timeDiff = (new Date()).getTime() - maquina.getAtualizacao().getTime();
				long minutes = Math.round(timeDiff / ((double)SECONDS_PER_DAY));
				
				int status = 99;
				
				StatusMsg statusMsg = new StatusMsg("", maquina.getCodigo(), new Date(), "", status, minutes);
				
				String approximateFirstReceiveTimestamp = String.valueOf(new Date().getTime());
				
		        try {
		            this.sendingTextWebSocketHandler.broadcastToSessions(new DataWithTimestamp<StatusMsg>(statusMsg, approximateFirstReceiveTimestamp));
		        } catch (IOException e) {
		        	System.out.println(String.format("\n********************************************************************************\nWas not able to push the message to the client.\n********************************************************************************\n%s\n********************************************************************************\n", e.getMessage()));
		        }
				
	        	MaquinaLogErro maquinaLogErro = new MaquinaLogErro(new Date(), maquina.getCodigo(), String.format("TIMEOUT na conexao com a maquina %s %s", maquina.getCodigo(), maquina.getNome()), "");
				maquinaLogErroRepository.saveAndFlush(maquinaLogErro);
				
			}
		}
	}
}
