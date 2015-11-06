package br.com.altamira.monitoramento.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "MAQUINA")
public class Maquina {

	@Id
	@Column(name = "CODIGO")
	private String codigo;
	
	@Column(name = "NOME")
	private String nome;
	
	@Column(name = "SETOR")
	private String setor;
	
	@Column(name = "IP_IHM")
	private String ipIHM;
	
	@Column(name = "IP_CLP")
	private String ipCLP;

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getSetor() {
		return setor;
	}

	public void setSetor(String setor) {
		this.setor = setor;
	}

	public String getIpIHM() {
		return ipIHM;
	}

	public void setIpIHM(String ipIHM) {
		this.ipIHM = ipIHM;
	}

	public String getIpCLP() {
		return ipCLP;
	}

	public void setIpCLP(String ipCLP) {
		this.ipCLP = ipCLP;
	}
		
}
