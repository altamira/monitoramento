package br.com.altamira.monitoramento.msg;

import java.util.Date;
import java.util.List;

public class MonitoramentoMsg {

	private String maquina;
	
	private Date datahora;
	
	private String operador;
	
	private int modo;
	
	private int tempo;
	
	private List<MedidaMsg> parametros;

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

	public List<MedidaMsg> getParametros() {
		return parametros;
	}

	public void setParametros(List<MedidaMsg> parametros) {
		this.parametros = parametros;
	}
	
}
