package br.unisul.dados;

public class Receita_Ingrediente {
	
	private Integer codigo;
	private Receita receita;
	private Ingrediente ingredientes;
	private Double quantidade;
	
	
	public Receita_Ingrediente (){
		
	}
	
	public Receita_Ingrediente(Integer codigo, Receita receita,	Ingrediente ingredientes, Double quantidade) {
		this.codigo = codigo;
		this.receita = receita;
		this.ingredientes = ingredientes;
		this.quantidade = quantidade;
	}

	public Integer getCodigo() {
		return codigo;
	}

	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
	}

	public Receita getReceita() {
		return receita;
	}
	
	public void setReceita(Receita receita) {
		this.receita = receita;
	}
	
	public Ingrediente getIngredientes() {
		return ingredientes;
	}
	
	public void setIngredientes(Ingrediente ingredientes) {
		this.ingredientes = ingredientes;
	}
	
	public Double getQuantidade() {
		return quantidade;
	}
	
	public void setQuantidade(Double quantidade) {
		this.quantidade = quantidade;
	}
}