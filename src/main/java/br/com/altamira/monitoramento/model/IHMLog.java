package br.com.altamira.monitoramento.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "IHM_LOG")
public class IHMLog {

	@Id
	@Column(name = "ID")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator="IHM_LOG_SEQ")
	@SequenceGenerator(name="IHM_LOG_SEQ",sequenceName="IHM_LOG_SEQUENCE", allocationSize=1)
	private Long id;
	
	@Column(name = "IHM")
	private String ihm;

	@Column(name = "RECEBIDOEM", insertable = false, updatable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date recebidoEm;
	
	@Column(name = "DATAHORA")
	@Temporal(TemporalType.TIMESTAMP)
	private Date datahora;
	
	@Column(name = "MODO")
	private int modo;
	
	@Column(name = "SEQUENCIA")
	private long sequencia;
	
	@Column(name = "TEMPO")
	private long tempo;
	
	@Column(name = "OPERADOR")
	private String operador;

	public IHMLog() {
		super();
	}

	public IHMLog(String ihm, Date datahora, int modo, long sequencia,
			long tempo, String operador) {
		super();
		this.ihm = ihm;
		this.datahora = datahora;
		this.modo = modo;
		this.sequencia = sequencia;
		this.tempo = tempo;
		this.operador = operador;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getIHM() {
		return ihm;
	}

	public void setIHM(String ihm) {
		this.ihm = ihm;
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

	public int getModo() {
		return modo;
	}

	public void setModo(int modo) {
		this.modo = modo;
	}

	public String getOperador() {
		return operador;
	}

	public void setOperador(String operador) {
		this.operador = operador;
	}

	public long getSequencia() {
		return sequencia;
	}

	public void setSequencia(long sequencia) {
		this.sequencia = sequencia;
	}

	public long getTempo() {
		return tempo;
	}

	public void setTempo(long tempo) {
		this.tempo = tempo;
	}
	
}
