package br.com.altamira.monitoramento.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "IHM_LOG_ERRO")
public class IHMLogErro {

	@Id
	@Column(name = "ID")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(name = "DATAHORA")
	@Temporal(TemporalType.TIMESTAMP)
	private Date datahora;
	
	@Column(name = "IHM")
	private String ihm;
	
	@Column(name = "ERRO")
	private String erro;
	
	@Column(name = "MENSAGEM")
	private String mensagem;

	public IHMLogErro() {
		super();
	}

	public IHMLogErro(Date datahora, String erro, String mensagem) {
		super();
		this.datahora = datahora;
		this.erro = erro;
		this.mensagem = mensagem;
	}

	public IHMLogErro(Date datahora, String ihm, String erro,
			String mensagem) {
		super();
		this.datahora = datahora;
		this.ihm = ihm;
		this.erro = erro;
		this.mensagem = mensagem;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getDatahora() {
		return datahora;
	}

	public void setDatahora(Date datahora) {
		this.datahora = datahora;
	}

	public String getIHM() {
		return ihm;
	}

	public void setIHM(String ihm) {
		this.ihm = ihm;
	}

	public String getErro() {
		return erro;
	}

	public void setErro(String erro) {
		this.erro = erro;
	}

	public String getMensagem() {
		return mensagem;
	}

	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}
	
}
