package br.unisul.dados;

public class IngredientesMaisUtilizados {
	
	private Integer vezes;
	private String nome;
	private String unidade;
	
	public IngredientesMaisUtilizados (){
		
	}
	
	public IngredientesMaisUtilizados(Integer vezes, String nome, String unidade) {
		this.vezes = vezes;
		this.nome = nome;
		this.unidade = unidade;
	}

	public Integer getVezes() {
		return vezes;
	}
	
	public void setVezes(Integer vezes) {
		this.vezes = vezes;
	}
	
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getUnidade() {
		return unidade;
	}

	public void setUnidade(String unidade) {
		this.unidade = unidade;
	}
}
