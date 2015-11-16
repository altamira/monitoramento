package br.com.altamira.monitoramento.msg;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class StatusMsg {

	private String ihm;
	
	private String maquina;
	
	private Date datahora;
	
	private String operador;
	
	private int modo;
	
	private int tempo;
	
	private String tempoFormatado;
	
	private List<ParametroMsg> parametros = new ArrayList<ParametroMsg>();

	public StatusMsg() {
		super();
	}

	public StatusMsg(String ihm, String maquina, Date datahora,
			String operador, int modo, int tempo) {
		super();
		this.ihm = ihm;
		this.maquina = maquina;
		this.datahora = datahora;
		this.operador = operador;
		this.modo = modo;
		this.tempo = tempo;
	}

	public String getIHM() {
		return ihm;
	}

	public void setIHM(String ihm) {
		this.ihm = ihm;
	}

	public String getMaquina() {
		return maquina;
	}

	public void setMaquina(String maquina) {
		this.maquina = maquina;
	}

	public Date getDatahora() {
		return datahora;
	}

	public void setDatahora(Date datahora) {
		this.datahora = datahora;
	}

	public String getOperador() {
		return operador;
	}

	public void setOperador(String operador) {
		this.operador = operador;
	}

	public int getModo() {
		return modo;
	}

	public void setModo(int modo) {
		this.modo = modo;
	}

	public int getTempo() {
		return tempo;
	}

	public void setTempo(int tempo) {
		this.tempo = tempo;
		setTempoFormatado(tempo);
	}

	public String getTempoFormatado() {
		return tempoFormatado;
	}

	public void setTempoFormatado(int segundos) {
		int segundo = segundos % 60; 
		int minutos = segundos / 60; 
		int minuto = minutos % 60; 
		int horas = minutos / 60;
		int hora = horas % 60;
		int dias = horas / 24; 
		int dia = dias % 24;
		
		if (dia > 1) {
			this.tempoFormatado = String.format("%d dias", dia);
		} else if (dia == 1) {
			this.tempoFormatado = String.format("%d dia", dia);
		} else if (hora > 0) {
			this.tempoFormatado = String.format("%d h", hora);
		} else if (minuto > 0) {
			this.tempoFormatado = String.format("%d min", minuto);
		} else {
			this.tempoFormatado = String.format("%d s", segundo);
		}
	}

	public List<ParametroMsg> getParametros() {
		return parametros;
	}

	public void setParametros(List<ParametroMsg> parametros) {
		this.parametros = parametros;
	}
	
}
