package br.com.altamira.monitoramento.msg;

import java.math.BigDecimal;

public class MedidaMsg {

	private String medida;
	
	private String unidade;
	
	private BigDecimal valor;

	public String getMedida() {
		return medida;
	}

	public void setMedida(String medida) {
		this.medida = medida;
	}

	public String getUnidade() {
		return unidade;
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
