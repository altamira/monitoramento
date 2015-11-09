package br.com.altamira.monitoramento.msg;

import java.util.Date;
import java.util.List;

public class StatusMsg {

	private String ihm;
	
	private String maquina;
	
	private Date datahora;
	
	private String operador;
	
	private int modo;
	
	private int tempo;
	
	private List<ParametroMsg> parametros;

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
	}

	public List<ParametroMsg> getParametros() {
		return parametros;
	}

	public void setParametros(List<ParametroMsg> parametros) {
		this.parametros = parametros;
	}
	
}
