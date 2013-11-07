package br.unisul.dados;

public class Autor {

	private Integer codigo;
	private String nome;
	private Sexo sexo;

	public Autor() {

	}

	public Autor(Integer codigo, String nome, Sexo sexo) {
		this.codigo = codigo;
		this.nome = nome;
		this.sexo = sexo;
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

	public Sexo getSexo() {
		return sexo;
	}

	public void setSexo(Sexo sexo) {
		this.sexo = sexo;
	}
	
}