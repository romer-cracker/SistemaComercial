package model;

import java.io.Serializable;

public class ModelCliente implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Long idCliente;
	
	private String nome;
	
	private String endereco;
	
	private String bairro;
	
	private String cidade;
	
	private String cep;
	
	private String uf;
	
	private String telefone;
	
	public boolean isNovo() {
		if(this.idCliente == null) {
			return true;
		}else if(this.idCliente != null && this.idCliente > 0) {
			return false;
		}
		
		return idCliente == null;
	}
	
	public ModelCliente() {
		
	}

	public Long getIdCliente() {
		return idCliente;
	}

	public void setIdCliente(Long idCliente) {
		this.idCliente = idCliente;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	public String getBairro() {
		return bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	public String getCidade() {
		return cidade;
	}

	public void setCidade(String cidade) {
		this.cidade = cidade;
	}

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public String getUf() {
		return uf;
	}

	public void setUf(String uf) {
		this.uf = uf;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

}
