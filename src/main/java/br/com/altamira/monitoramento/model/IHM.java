package br.com.altamira.monitoramento.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "IHM")
public class IHM {

	@Id
	@Column(name = "CODIGO")
	private String codigo;
	
	@Column(name = "SETOR")
	private String setor;
	
	@Column(name = "IP")
	private String ip;
	
	@Column(name = "MAQUINA")
	private String maquina;
	
	@Column(name = "OPERADOR")
	private String operador;
	
	public IHM() {
		super();
	}

	public IHM(String codigo) {
		super();
		this.codigo = codigo;
	}

	public IHM(String codigo, String setor, String ip, String maquina) {
		super();
		this.codigo = codigo;
		this.setor = setor;
		this.ip = ip;
		this.maquina = maquina;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getSetor() {
		return setor;
	}

	public void setSetor(String setor) {
		this.setor = setor;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getMaquina() {
		return maquina;
	}

	public void setMaquina(String maquina) {
		this.maquina = maquina;
	}

	public String getOperador() {
		return operador;
	}

	public void setOperador(String operador) {
		this.operador = operador;
	}
		
}
