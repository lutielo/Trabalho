package br.unisul;

public interface Constantes {

	public interface Autor {
		String QUERY_INSERT_AUTOR = "INSERT INTO autor (nm_autor, cd_sexo, status)" 
								  + " VALUES (?, ?, 'D')";
		
		String QUERY_LIST_TODOS_AUTORES = "SELECT cd_autor, nm_autor, sexo.de_sexo" 
										+ " FROM autor "
										+ " JOIN sexo ON autor.cd_sexo = sexo.cd_sexo "
										+ " WHERE status = 'D' "
										+ " ORDER BY nm_autor";
		
		String QUERY_LIST_AUTORES_NOME = "SELECT cd_autor, nm_autor, sexo.de_sexo" 
									   + " FROM autor " 
									   + " JOIN sexo ON autor.cd_sexo = sexo.cd_sexo "
									   + " WHERE upper(nm_autor) LIKE  upper(?) "
									   + " AND status = 'D' "
									   + " ORDER BY nm_autor ";
		
		String QUERY_LIST_AUTOR_COD = "SELECT cd_autor, nm_autor, sexo.de_sexo" 
									+ " FROM autor"
									+ " JOIN sexo ON autor.cd_sexo = sexo.cd_sexo "
									+ " WHERE cd_autor = (?) "
									+ " AND status = 'D' ";
		
		String QUERY_DELETE_AUTOR = "UPDATE autor SET status = 'I' WHERE cd_autor = (?)";
		
		String QUERY_ALTER_AUTOR = "UPDATE autor SET nm_autor = (?), cd_sexo = (?)" + 
			 						" WHERE cd_autor = (?)";
	}

	public interface Ingrediente {
		String QUERY_INSERT_INGREDIENTE = "INSERT INTO ingrediente (nm_ingrediente, status)" 
										+ " VALUES (?, 'D')";
		
		String QUERY_LIST_TODOS_INGREDIENTES = "SELECT cd_ingrediente, nm_ingrediente "
											 + " FROM ingrediente " 
											 + " WHERE status = 'D' "
											 + " ORDER BY nm_ingrediente";
		
		String QUERY_LIST_INGREDIENTES_NOME = "SELECT cd_ingrediente, nm_ingrediente "
											+ " FROM ingrediente " 
											+ " WHERE upper(nm_ingrediente) LIKE  upper(?) "
											+ " AND status = 'D' "
											+ " ORDER BY nm_ingrediente ";
		
		String QUERY_LIST_INGREDIENTE_COD = "SELECT cd_ingrediente, nm_ingrediente " 
										  + " FROM ingrediente "
										  +	" WHERE cd_ingrediente = (?) "
										  + " AND status = 'D' ";
		
		String QUERY_DELETE_INGREDIENTE = "UPDATE ingrediente SET status = 'I' WHERE cd_ingrediente = (?)";
		
		String QUERY_ALTER_INGREDIENTE = "UPDATE ingrediente SET nm_ingrediente = (?)" +
			 							 " WHERE cd_ingrediente = (?)";
		
	}
	
	public interface Receita_Ingrediente {
		 String QUERY_INSERT_RECEITA_INGREDIENTE = "INSERT INTO receita_ingrediente (cd_ingrediente, cd_receita, cd_unidade, quantidade) " 
			 									 + " VALUES (?, ?, ?, ?)";
		 
		 String QUERY_LIST_TODAS_RECEITAS_INGREDIENTES = "SELECT receita.cd_receita, ingrediente.cd_ingrediente, unidade.cd_unidade, quantidade "
		 											   + " FROM receita_ingrediente " 
		 											   + " ORDER BY cd_receita";
		 
		 String QUERY_LIST_INGREDIENTES_MAIS_UTILIZADOS = " SELECT COUNT(i.nm_ingrediente) as vezes, i.cd_ingrediente, i.nm_ingrediente, u.cd_unidade, u.tp_unidade "
														 +" FROM receita_ingrediente ri "
														 +" JOIN ingrediente i ON i.cd_ingrediente = ri.cd_ingrediente "
														 +" JOIN unidade u ON u.cd_unidade = ri.cd_unidade "
														 +" GROUP BY i.cd_ingrediente, i.nm_ingrediente, u.cd_unidade, u.tp_unidade "
														 +" ORDER BY COUNT(i.nm_ingrediente) DESC";
		 
		 String QUERY_LIST_RECEITAS_USAM_TAL_INGREDIENTE = " SELECT r.nm_receita, a.nm_autor, r.dt_criacao "
				 										  +" FROM receita_ingrediente ri "
				 										  +" JOIN receita r ON r.cd_receita = ri.cd_receita "
				 										  +" JOIN ingrediente i ON i.cd_ingrediente = ri.cd_ingrediente "
				 										  +" JOIN unidade u ON u.cd_unidade = ri.cd_unidade "
				 										  +" JOIN autor a ON a.cd_autor = r.cd_autor "
				 										  +" WHERE ri.cd_ingrediente = ? "
				 										  +" AND ri.cd_unidade = ? "
				 										  +" ORDER BY nm_receita";
		 
		 String QUERY_LIST_INGREDIENTES_DA_RECEITA = "SELECT i.cd_ingrediente, i.nm_ingrediente, r.cd_receita, r.nm_receita, u.cd_unidade, u.tp_unidade, quantidade"
			 									 +" FROM receita_ingrediente ri"
			 									 +" JOIN receita r ON r.cd_receita = ri.cd_receita"
			 									 +" JOIN ingrediente i ON i.cd_ingrediente = ri.cd_ingrediente"
			 									 +" JOIN unidade u ON u.cd_unidade = ri.cd_unidade"
			 									 +" WHERE r.cd_receita = (?)";
	}
	
	public interface Receita {
		 String QUERY_INSERT_RECEITA = "INSERT INTO receita (nm_receita, cd_autor, dt_criacao, tx_modo_preparo, status) " 
			 						 + " VALUES (?, ?, ?, ?, 'D')";
		 
		 String QUERY_LIST_TODAS_RECEITAS = "SELECT cd_receita, nm_receita, dt_criacao, tx_modo_preparo, autor.cd_autor, autor.nm_autor" 
			 							  + " FROM receita "
			 							  + " JOIN autor ON receita.cd_autor = autor.cd_autor "
			 							  + " WHERE receita.status = 'D' "
			 							  + " ORDER BY nm_receita ";
		 
		 String QUERY_LIST_RECEITAS_NOME = "SELECT cd_receita, nm_receita, dt_criacao, tx_modo_preparo, autor.cd_autor, autor.nm_autor" 
			 							 + " FROM receita " 
			 							 + " JOIN autor ON receita.cd_autor = autor.cd_autor " 
			 							 + " WHERE upper(nm_receita) LIKE  upper(?) ORDER BY nm_receita "
			 							 + " AND receita.status = 'D' ";
		 
		 String QUERY_LIST_RECEITA_COD = "SELECT cd_receita, nm_receita, dt_criacao, tx_modo_preparo, autor.cd_autor, autor.nm_autor" 
			 						   + " FROM receita " 
			 						   + " JOIN autor ON receita.cd_autor = autor.cd_autor " 
			 						   + " WHERE cd_receita = (?) "
			 						   + " AND receita.status = 'D' ";
		 
		 String QUERY_LIST_ULTIMO_REGISTRO = "SELECT MAX (cd_receita) as ultimo FROM receita ";
		 
		 String QUERY_DELETE_RECEITA = "UPDATE receita SET status = 'I' WHERE cd_receita = (?)";
		 
		 String QUERY_ALTER_RECEITA = "UPDATE receita SET nm_receita = (?), tx_modo_preparo = (?)" +
			 						  " WHERE cd_receita = (?)";
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
		 
		 String QUERY_ALTER_UNIDADE = "UPDATE unidade SET tp_unidade = (?)" +
			 						  " WHERE cd_unidade = (?)";
	}
}
