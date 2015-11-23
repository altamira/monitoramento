package br.com.altamira.monitoramento.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

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
	
	@Column(name = "VERSAO")
	private String versao;
	
	@Column(name = "ATIVO")
	private Boolean ativo;
	
	@Column(name = "SITUACAO")
	private int situacao;
	
	@Column(name = "SEQUENCIA")
	private long sequencia;
	
	@Column(name = "TEMPO")
	private long tempo;
	
	@Column(name = "ULTIMA_ATUALIZACAO")
	@Temporal(TemporalType.TIMESTAMP)
	private Date atualizacao;
	
	@Column(name = "FALHA_COMUNICACAO")
	private long falha;
	
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

	public IHM(String codigo, String setor, String ip, String maquina,
			String operador, String versao) {
		super();
		this.codigo = codigo;
		this.setor = setor;
		this.ip = ip;
		this.maquina = maquina;
		this.operador = operador;
		this.versao = versao;
	}

	public IHM(String operador, String versao, int situacao, long sequencia,
			long tempo, Date atualizacao) {
		super();
		this.operador = operador;
		this.versao = versao;
		this.situacao = situacao;
		this.sequencia = sequencia;
		this.tempo = tempo;
		this.atualizacao = atualizacao;
	}

	public IHM(String codigo, String setor, String ip, String maquina,
			String operador, String versao, Boolean ativo, int situacao,
			long sequencia, long tempo, Date atualizacao) {
		super();
		this.codigo = codigo;
		this.setor = setor;
		this.ip = ip;
		this.maquina = maquina;
		this.operador = operador;
		this.versao = versao;
		this.ativo = ativo;
		this.situacao = situacao;
		this.sequencia = sequencia;
		this.tempo = tempo;
		this.atualizacao = atualizacao;
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

	public String getVersao() {
		return versao;
	}

	public void setVersao(String versao) {
		this.versao = versao;
	}

	public Boolean getAtivo() {
		return ativo;
	}

	public void setAtivo(Boolean ativo) {
		this.ativo = ativo;
	}

	public int getSituacao() {
		return situacao;
	}

	public void setSituacao(int situacao) {
		this.situacao = situacao;
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

	public Date getAtualizacao() {
		return atualizacao;
	}

	public void setAtualizacao(Date atualizacao) {
		this.atualizacao = atualizacao;
	}

	public long getFalhaComunicacao() {
		return falha;
	}

	public void setFalhaComunicacao(long falha) {
		this.falha = falha;
	}

}
