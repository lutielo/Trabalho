package br.unisul.dados;

public class Receita_Ingrediente {
	
	private Receita receita;
	private Ingrediente ingrediente;
	private Unidade unidade;
	private Double quantidade;
	
	
	public Receita_Ingrediente (){
		
	}
	
	public Receita_Ingrediente(Receita receita,	Ingrediente ingredientes, Unidade unidade, Double quantidade) {
		this.receita = receita;
		this.ingrediente = ingredientes;
		this.unidade = unidade;
		this.quantidade = quantidade;
	}

	public Receita getReceita() {
		return receita;
	}
	
	public void setReceita(Receita receita) {
		this.receita = receita;
	}
	
	public Ingrediente getIngrediente() {
		return ingrediente;
	}
	
	public void setIngrediente(Ingrediente ingrediente) {
		this.ingrediente = ingrediente;
	}
	
	
	public Unidade getUnidade() {
		return unidade;
	}

	public void setUnidade(Unidade unidade) {
		this.unidade = unidade;
	}

	public Double getQuantidade() {
		return quantidade;
	}
	
	public void setQuantidade(Double quantidade) {
		this.quantidade = quantidade;
	}
}