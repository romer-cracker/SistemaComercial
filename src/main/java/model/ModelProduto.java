package model;

import java.io.Serializable;

public class ModelProduto implements Serializable{

	private static final long serialVersionUID = 1L;

	private Long idProduto;
	
	private String nome;
	
	private Double valor;
	
	private int estoque;
	
	public ModelProduto() {
		
	}
	
	public boolean isNovo() {
		if(this.idProduto == null) {
			return true;
		}else if(this.idProduto != null && this.idProduto > 0) {
			return false;
		}
		
		return idProduto == null;
	}

	public Long getIdProduto() {
		return idProduto;
	}

	public void setIdProduto(Long idProduto) {
		this.idProduto = idProduto;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Double getValor() {
		return valor;
	}

	public void setValor(Double valor) {
		this.valor = valor;
	}

	public int getEstoque() {
		return estoque;
	}

	public void setEstoque(int estoque) {
		this.estoque = estoque;
	}
}
