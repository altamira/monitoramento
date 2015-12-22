package br.com.altamira.monitoramento.controller;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import br.com.altamira.monitoramento.model.IHM;
import br.com.altamira.monitoramento.model.Maquina;
import br.com.altamira.monitoramento.model.MaquinaLogErro;
import br.com.altamira.monitoramento.msg.StatusMsg;
import br.com.altamira.monitoramento.repository.IHMLogRepository;
import br.com.altamira.monitoramento.repository.IHMRepository;
import br.com.altamira.monitoramento.repository.MaquinaLogErroRepository;
import br.com.altamira.monitoramento.repository.MaquinaRepository;

@Component
@Transactional
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
	
	@Autowired
	private IHMLogRepository ihmLogRepository;

	@Scheduled(fixedDelay = 30000)
	// @Scheduled(fixedRate = 5000)
	public void demoServiceMethod() {
		
		/*
		Calendar calendar = Calendar.getInstance();
		//calendar.setTime(statusMsg.getDatahora());
		int hora = calendar.get(Calendar.HOUR_OF_DAY);
		int minuto = calendar.get(Calendar.MINUTE);
		//int segundo = calendar.get(Calendar.SECOND);
		
		if (hora < 7 || (hora == 9 && minuto <= 12) || (hora == 12) || (hora == 13 && minuto <= 12) || (hora >= 17 && minuto > 0)) {
			System.out.println(String.format(
					"\n--------------------------------------------------------------------------------\n%s MONITORAMENTO DAS CONEXOES DESATIVO FORA HORARIO DE EXPEDIENTE PARA NAO GERAR REGISTRO NO BANCO DE DADOS.\n--------------------------------------------------------------------------------\n", calendar.toString()));
			return;
		}
		*/
		
		System.out.println(String.format(
				"\n--------------------------------------------------------------------------------\nVerificando o estado das conexoes das maquinas...\n--------------------------------------------------------------------------------\n"));

		List<Maquina> maquinas = maquinaRepository.findByDesatualizadas(60);
		
		for (Maquina maquina : maquinas) {

			if (maquina.getSituacao() != 7) {

				System.out.println(String.format(
						"\n--------------------------------------------------------------------------------\nTIMEOUT na conexao com a maquina %s %s\n--------------------------------------------------------------------------------\n", maquina.getCodigo(), maquina.getNome()));
				
				long milisegundos = (new Date()).getTime() - maquina.getAtualizacao().getTime();
				long segundos = Math.round(milisegundos / 1000);
				
				StatusMsg statusMsg = new StatusMsg("", maquina.getCodigo(), new Date(), "", 99, segundos);

				//maquina.setSituacao(99);
				//maquina.setTempo(0);
				//maquina.setOperador("");
				//maquina.setAtualizacao(new Date());
				
				maquina.setFalhaComunicacao(statusMsg.getTempo());
				
				maquinaRepository.saveAndFlush(maquina);
				
				List<IHM> ihms = ihmRepository.findAllByMaquina(maquina.getCodigo());
				
				if (ihms != null && !ihms.isEmpty()) {
					for (IHM ihm : ihms) {
						
						//ihm.setVersao("?");
						ihm.setFalhaComunicacao(statusMsg.getTempo());
						
						ihmRepository.saveAndFlush(ihm);
						
						/*IHMLog ihmLog = new IHMLog(
								ihm.getCodigo().toUpperCase(),
								statusMsg.getDatahora(),
								statusMsg.getModo(),
								statusMsg.getSequencia(),
								statusMsg.getTempo(),
								statusMsg.getOperador()
								);
						
						ihmLogRepository.saveAndFlush(ihmLog);*/
					}
				}
				
	        	//MaquinaLogErro maquinaLogErro = new MaquinaLogErro(new Date(), maquina.getCodigo(), String.format("TIMEOUT na conexao com a maquina %s %s", maquina.getCodigo(), maquina.getNome()), "");
				//maquinaLogErroRepository.saveAndFlush(maquinaLogErro);

				String approximateFirstReceiveTimestamp = String.valueOf(new Date().getTime());

		        try {
		            this.sendingTextWebSocketHandler.broadcastToSessions(new DataWithTimestamp<StatusMsg>(statusMsg, approximateFirstReceiveTimestamp));
		        } catch (IOException e) {
		        	System.out.println(String.format("\n********************************************************************************\nErro ao notificar os clientes.\n********************************************************************************\n%s\n********************************************************************************\n", e.getMessage()));

		        	MaquinaLogErro broadcastErro = new MaquinaLogErro(new Date(), maquina.getCodigo(), String.format("Erro ao notificar os clientes sobre TIMEOUT na maquina %s %s", maquina.getCodigo(), maquina.getNome()), "");
					maquinaLogErroRepository.saveAndFlush(broadcastErro);
		        }
				
				
			}
		}
	}
}
