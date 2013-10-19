package br.unisul.dados;

import java.util.Date;

import br.unisul.util.DateUtils;

public class Receita {
	
	private Integer codigo;
	private String nome;
	private Date dt_criacao;
	private String modo_preparo;
	private Autor autor;
	
	
	public Receita(){
		
	}
	
	public Receita(Integer codigo, String nome, Date dt_criacao,
			String modo_preparo, Autor autor) {
		this.codigo = codigo;
		this.nome = nome;
		this.dt_criacao = dt_criacao;
		this.modo_preparo = modo_preparo;
		this.autor = autor;
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

	public Date getDt_criacao() {
		return dt_criacao;
	}

	public void setDt_criacao(Date dt_criacao) {
		this.dt_criacao = dt_criacao;
	}

	public String getModo_preparo() {
		return modo_preparo;
	}

	public void setModo_preparo(String modo_preparo) {
		this.modo_preparo = modo_preparo;
	}

	public Autor getAutor() {
		return autor;
	}

	public void setAutor(Autor autor) {
		this.autor = autor;
	}

	public String getDataCriacaoFormatada() {
		return DateUtils.toString(getDt_criacao());
	}
}
