package br.com.altamira.monitoramento.msg;

import javax.xml.crypto.Data;

public class ErroMsg {

	private Data datahora;
	
	private String erro;
	
	private String mensagem;

	public ErroMsg() {
		super();
	}

	public ErroMsg(Data datahora, String erro, String mensagem) {
		super();
		this.datahora = datahora;
		this.erro = erro;
		this.mensagem = mensagem;
	}

	public Data getDatahora() {
		return datahora;
	}

	public void setDatahora(Data datahora) {
		this.datahora = datahora;
	}

	public String getErro() {
		return erro;
	}

	public void setErro(String erro) {
		this.erro = erro;
	}

	public String getStatusMsg() {
		return mensagem;
	}

	public void setStatusMsg(String mensagem) {
		this.mensagem = mensagem;
	}
	
}
