package br.unisul;

public interface Constantes {

	public interface Autor {
		String QUERY_INSERT_AUTOR = "INSERT INTO autor (nm_autor)" 
								  + " VALUES (?)";
		
		String QUERY_LIST_TODOS_AUTORES = "SELECT cd_autor, nm_autor" 
										+ " FROM autor" 
										+ " ORDER BY nm_autor";
		
		//TESTE
		
		String QUERY_LIST_AUTORES_NOME = "SELECT cd_autor, nm_autor " 
									   + " FROM autor " 
									   + " WHERE upper(nm_autor) LIKE  upper(?) "
									   + " ORDER BY nm_autor ";
		
		String QUERY_LIST_AUTOR_COD = "SELECT cd_autor, nm_autor" 
									+ " FROM autor"
									+ " WHERE cd_autor = (?) ";
		
		String QUERY_DELETE_AUTOR = "DELETE FROM autor WHERE cd_autor = (?)";
	}

	public interface Ingrediente {
		String QUERY_INSERT_INGREDIENTE = "INSERT INTO ingrediente (nm_ingrediente, cd_unidade)" 
										+ " VALUES (?, ?)";
		
		String QUERY_LIST_TODOS_INGREDIENTES = "SELECT cd_ingrediente, nm_ingrediente, unidade.cd_unidade, unidade.tp_unidade "
											 + " FROM ingrediente " 
										     + " JOIN unidade ON ingrediente.cd_unidade = unidade.cd_unidade " 
											 + " ORDER BY tp_unidade";
		
		String QUERY_LIST_INGREDIENTES_NOME = "SELECT cd_ingrediente, nm_ingrediente, unidade.cd_unidade, unidade.tp_unidade "
											+ " FROM ingrediente " 
											+ " JOIN unidade ON ingrediente.cd_unidade = unidade.cd_unidade "
											+ " WHERE upper(nm_ingrediente) LIKE  upper(?) ORDER BY nm_ingrediente ";
		
		String QUERY_LIST_INGREDIENTE_COD = "SELECT cd_ingrediente, nm_ingrediente, cd_unidade" 
										  + " FROM ingrediente "
										  +	" WHERE cd_ingrediente = (?) ";
		
		String QUERY_DELETE_INGREDIENTE = "DELETE FROM ingrediente WHERE cd_ingrediente = (?)";
	}
	
	public interface Receita_Ingrediente {
		 String QUERY_INSERT_RECEITA_INGREDIENTE = "INSERT INTO receita_ingrediente (cd_ingrediente, cd_receita, quantidade) " 
			 									 + " VALUES (?, ?, ?)";
		 
		 String QUERY_LIST_TODAS_RECEITAS_INGREDIENTES = "SELECT cd_receita_ingrediente, receita.cd_receita, ingrediente.cd_ingrediente, quantidade "
		 											   + " FROM receita_ingrediente " 
		 											   + " ORDER BY cd_receita";
		 
		 String QUERY_LIST_INGREDIENTES_MAIS_UTILIZADOS = "SELECT COUNT(i.nm_ingrediente), i.nm_ingrediente " 
			 											+ " FROM receita_ingrediente ri " 
			 											+ " JOIN ingrediente i ON i.cd_ingrediente = ri.cd_ingrediente " 
			 											+ " GROUP BY i.nm_ingrediente " 
			 											+ " ORDER BY COUNT(i.nm_ingrediente) DESC";
	}
	
	public interface Receita {
		 String QUERY_INSERT_RECEITA = "INSERT INTO receita (nm_receita, cd_autor, dt_criacao, tx_modo_preparo) " 
			 						 + " VALUES (?, ?, ?, ?)";
		 
		 String QUERY_LIST_TODAS_RECEITAS = "SELECT cd_receita, nm_receita, dt_criacao, tx_modo_preparo, autor.cd_autor" 
			 							  + " FROM receita "
			 							  + " JOIN autor ON receita.cd_autor = autor.cd_autor "
			 							  + " ORDER BY nm_receita ";
		 
		 String QUERY_LIST_RECEITAS_NOME = "SELECT cd_receita, nm_receita, dt_criacao, tx_modo_preparo, autor.cd_autor, autor.nm_autor" 
			 							 + " FROM receita " 
			 							 + " JOIN autor ON receita.cd_autor = autor.cd_autor " 
			 							 + " WHERE upper(nm_receita) LIKE  upper(?) ORDER BY nm_receita ";
		 
		 String QUERY_LIST_RECEITA_COD = "SELECT cd_receita, nm_receita, dt_criacao, tx_modo_preparo, autor.cd_autor, autor.nm_autor" 
			 						   + " FROM receita " 
			 						   + " JOIN autor ON receita.cd_autor = autor.cd_autor " 
			 						   + " WHERE cd_receita = (?) ";
		 
		 String QUERY_DELETE_RECEITA = "DELETE FROM receita WHERE cd_receita = (?)";
	}
	
	public interface Unidade {
		 String QUERY_INSERT_UNIDADE = "INSERT INTO unidade (tp_unidade)" 
			 						 + " VALUES (?)";
		 
		 String QUERY_LIST_TODAS_UNIDADES = "SELECT cd_unidade, tp_unidade" 
			 							  + " FROM unidade" 
			 							  + " ORDER BY tp_unidade";
		 
		 String QUERY_LIST_UNIDADES_NOME = "SELECT cd_unidade, tp_unidade" 
			 							 + " FROM unidade" 
			 							 + " WHERE upper(tp_unidade)  LIKE  upper(?)" 
			 							 + " ORDER BY tp_unidade";
		 
		 String QUERY_LIST_UNIDADE_COD = "SELECT cd_unidade, tp_unidade" 
			 						   + " FROM unidade" 
			 						   + " WHERE cd_unidade = (?) ";
		 
		 String QUERY_DELETE_UNIDADE = "DELETE FROM unidade WHERE cd_unidade = (?)";
	}
}
