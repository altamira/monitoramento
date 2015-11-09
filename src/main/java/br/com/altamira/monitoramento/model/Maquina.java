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
	
	@Column(name = "SITUACAO")
	private int situacao;
	
	@Column(name = "TEMPO")
	private int tempo;

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

	public int getSituacao() {
		return situacao;
	}

	public void setSituacao(int situacao) {
		this.situacao = situacao;
	}

	public int getTempo() {
		return tempo;
	}

	public void setTempo(int tempo) {
		this.tempo = tempo;
	}
		
}
