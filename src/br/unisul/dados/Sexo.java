package br.unisul.dados;


public class Sexo {
	
	private Integer codigo;
	private String nome;
	
	public Sexo (Integer codigo, String nome){
		this.codigo = codigo;
		this.nome = nome;
	}
	
	public Sexo(){
		
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
	
}
