package model;

import java.io.Serializable;
import java.sql.Date;

public class ModelVendas implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Long idVendas;
	
	private Long fk_cliente;
	
	private Date dataVenda;
	
	private Double valor;
	
	private Double valorTotal;
	
	private Double desconto;
	
	public boolean isNovo() {
		if(this.idVendas == null) {
			return true;
		}else if(this.idVendas != null && this.idVendas > 0) {
			return false;
		}
		
		return idVendas == null;
	}
	
	public ModelVendas() {
	}

	public Long getIdVendas() {
		return idVendas;
	}

	public void setIdVendas(Long idVendas) {
		this.idVendas = idVendas;
	}

	public Long getFk_cliente() {
		return fk_cliente;
	}

	public void setFk_cliente(Long fk_cliente) {
		this.fk_cliente = fk_cliente;
	}

	public Date getDataVenda() {
		return dataVenda;
	}

	public void setDataVenda(Date dataVenda) {
		this.dataVenda = dataVenda;
	}

	public Double getValor() {
		return valor;
	}

	public void setValor(Double valor) {
		this.valor = valor;
	}

	public Double getValorTotal() {
		return valorTotal;
	}

	public void setValorTotal(Double valorTotal) {
		this.valorTotal = valorTotal;
	}

	public Double getDesconto() {
		return desconto;
	}

	public void setDesconto(Double desconto) {
		this.desconto = desconto;
	}

}
