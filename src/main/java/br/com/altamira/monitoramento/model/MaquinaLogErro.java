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
@Table(name = "MAQUINA_LOG_ERRO")
public class MaquinaLogErro {

	@Id
	@Column(name = "ID")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(name = "DATAHORA")
	@Temporal(TemporalType.TIMESTAMP)
	private Date datahora;
	
	@Column(name = "MAQUINA")
	private String maquina;
	
	@Column(name = "ERRO")
	private String erro;
	
	@Column(name = "MENSAGEM")
	private String mensagem;

	public MaquinaLogErro() {
		super();
	}

	public MaquinaLogErro(Date datahora, String erro, String mensagem) {
		super();
		this.datahora = datahora;
		this.erro = erro;
		this.mensagem = mensagem;
	}

	public MaquinaLogErro(Date datahora, String maquina, String erro,
			String mensagem) {
		super();
		this.datahora = datahora;
		this.maquina = maquina;
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

	public String getMaquina() {
		return maquina;
	}

	public void setMaquina(String maquina) {
		this.maquina = maquina;
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
