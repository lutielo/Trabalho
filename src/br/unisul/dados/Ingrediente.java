package br.unisul.dados;

public class Ingrediente {

	private Integer codigo;
	private String nome;
	private Unidade unidade;
	
	
	public Ingrediente (){
		
	}
	
	public Ingrediente(Integer codigo, String nome, Unidade unidade) {
		this.codigo = codigo;
		this.nome = nome;
		this.unidade = unidade;
	}

	public Integer getCodigo() {
		return codigo;
	}
	
	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
	}
	
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public Unidade getUnidade() {
		return unidade;
	}

	public void setUnidade(Unidade unidade) {
		this.unidade = unidade;
	}
}
