package br.com.altamira.monitoramento.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "UNIDADE")
public class Unidade {

	@Id
	@Column(name = "SIMBOLO")
	private String simbolo;
	
	@Column(name = "NOME")
	private String nome;
	
	@Column(name = "GRANDEZA")
	private String grandeza;

	public Unidade() {
		super();
	}

	public Unidade(String simbolo) {
		super();
		this.simbolo = simbolo;
	}

	public Unidade(String simbolo, String nome, String grandeza) {
		super();
		this.simbolo = simbolo;
		this.nome = nome;
		this.grandeza = grandeza;
	}

	public String getSimbolo() {
		return simbolo;
	}

	public void setSimbolo(String simbolo) {
		this.simbolo = simbolo;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getGrandeza() {
		return grandeza;
	}

	public void setGrandeza(String grandeza) {
		this.grandeza = grandeza;
	}
	
}
