package model;

import java.io.Serializable;

public class ModelVendasProdutos implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Long idVenda_produto;
	
	private Long fk_cliente;
	
	private Long fk_produto;
	
	private Long fk_venda;
	
	private Double valor;
	
	private int quantidade;
	
	public boolean isNovo() {
		if(this.idVenda_produto == null) {
			return true;
		}else if(this.idVenda_produto != null && this.idVenda_produto > 0) {
			return false;
		}
		
		return idVenda_produto == null;
	}
	
	public Long getFk_cliente() {
		return fk_cliente;
	}

	public void setFk_cliente(Long fk_cliente) {
		this.fk_cliente = fk_cliente;
	}

	public ModelVendasProdutos() {
		
	}

	public Long getIdVenda_produto() {
		return idVenda_produto;
	}

	public void setIdVenda_produto(Long idVenda_produto) {
		this.idVenda_produto = idVenda_produto;
	}

	public Long getFk_produto() {
		return fk_produto;
	}

	public void setFk_produto(Long fk_produto) {
		this.fk_produto = fk_produto;
	}

	public Long getFk_venda() {
		return fk_venda;
	}

	public void setFk_venda(Long fk_venda) {
		this.fk_venda = fk_venda;
	}

	public Double getValor() {
		return valor;
	}

	public void setValor(Double valor) {
		this.valor = valor;
	}

	public int getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(int quantidade) {
		this.quantidade = quantidade;
	}
	
	

}
