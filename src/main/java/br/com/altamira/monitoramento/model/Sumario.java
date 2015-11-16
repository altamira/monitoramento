package br.com.altamira.monitoramento.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "MAQUINA_LOG_STATUS_SUMARIO")
public class Sumario {

	@Id
	@Column(name = "ID")
	private int id;
	
	@Column(name = "MAQUINA")
	private String maquina;
	
	@Column(name = "SITUACAO")
	private String situacao;
	
	@Column(name = "DESCRICAO")
	private String descricao;
	
	@Column(name = "TEMPO")
	private Long tempo;

	public String getMaquina() {
		return maquina;
	}

	public void setMaquina(String maquina) {
		this.maquina = maquina;
	}

	public String getSituacao() {
		return situacao;
	}

	public void setSituacao(String situacao) {
		this.situacao = situacao;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Long getTempo() {
		return tempo;
	}

	public void setTempo(Long tempo) {
		this.tempo = tempo;
	}
		
}
