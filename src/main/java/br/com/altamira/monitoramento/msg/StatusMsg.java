package br.com.altamira.monitoramento.msg;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class StatusMsg {

	private String ihm;
	
	private String maquina;
	
	private Date recebidoEm;
	
	private Date datahora;
	
	private String operador;
	
	private int modo;
	
	private long sequencia;
	
	private int tempo;
	
	private String versao;
	
	private String tempoFormatado;
	
	private List<ParametroMsg> parametros = new ArrayList<ParametroMsg>();

	public StatusMsg() {
		super();
		this.setTempoFormatado(this.tempo);
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
		this.setTempoFormatado(this.tempo);
	}

	public StatusMsg(String ihm, String maquina, Date datahora,
			String operador, int modo, long sequencia, int tempo) {
		super();
		this.ihm = ihm;
		this.maquina = maquina;
		this.datahora = datahora;
		this.operador = operador;
		this.modo = modo;
		this.sequencia = sequencia;
		this.tempo = tempo;
		this.setTempoFormatado(this.tempo);
	}

	public StatusMsg(String ihm, String maquina, Date datahora,
			String operador, int modo, long sequencia, int tempo, String versao) {
		super();
		this.ihm = ihm;
		this.maquina = maquina;
		this.datahora = datahora;
		this.operador = operador;
		this.modo = modo;
		this.sequencia = sequencia;
		this.tempo = tempo;
		this.versao = versao;
		this.setTempoFormatado(this.tempo);
	}

	public StatusMsg(String ihm, String maquina, Date recebidoEm, Date datahora,
			String operador, int modo, long sequencia, int tempo, String versao) {
		super();
		this.ihm = ihm;
		this.maquina = maquina;
		this.recebidoEm = recebidoEm;
		this.datahora = datahora;
		this.operador = operador;
		this.modo = modo;
		this.sequencia = sequencia;
		this.tempo = tempo;
		this.versao = versao;
		this.setTempoFormatado(this.tempo);
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

	public Date getRecebidoEm() {
		return recebidoEm;
	}

	public void setRecebidoEm(Date recebidoEm) {
		this.recebidoEm = recebidoEm;
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

	public long getSequencia() {
		return sequencia;
	}

	public void setSequencia(long sequencia) {
		this.sequencia = sequencia;
	}

	public int getTempo() {
		return tempo;
	}

	public void setTempo(int tempo) {
		this.tempo = tempo;
		this.setTempoFormatado(tempo);
	}
	
	public String getVersao() {
		return versao;
	}

	public void setVersao(String versao) {
		this.versao = versao;
	}

	public String getTempoFormatado() {
		return tempoFormatado;
	}

	public void setTempoFormatado(int segundos) {
		int segundo = segundos % 60; 
		int minutos = segundos / 60; 
		int minuto = minutos % 60; 
		int horas = minutos / 60;
		int hora = horas % 24;
		int dias = horas / 24; 
		int dia = dias;
		
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
