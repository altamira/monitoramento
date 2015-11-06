package br.com.altamira.monitoramento.model;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "MAQUINA_LOG_PARAMETRO")
public class MaquinaLogParametro {

	@Id
	@Column(name = "ID")
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	@Column(name="MAQUINA_LOG")
	private Long maquinaLog;

	@Column(name = "MEDIDA")
	private String medida;
	
	@Column(name = "UNIDADE")
	private String unidade;
	
	@Column(name = "VALOR")
	private BigDecimal valor;

	public MaquinaLogParametro() {
		super();
	}

	public MaquinaLogParametro(Long maquinaLog, String medida, String unidade,
			BigDecimal valor) {
		super();
		this.maquinaLog = maquinaLog;
		this.medida = medida;
		this.unidade = unidade;
		this.valor = valor;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getMaquinaLog() {
		return maquinaLog;
	}

	public void setMaquinaLog(Long maquinaLog) {
		this.maquinaLog = maquinaLog;
	}

	public String getUnidade() {
		return unidade;
	}

	public String getMedida() {
		return medida;
	}

	public void setMedida(String medida) {
		this.medida = medida;
	}

	public void setUnidade(String unidade) {
		this.unidade = unidade;
	}

	public BigDecimal getValor() {
		return valor;
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}
	
}
