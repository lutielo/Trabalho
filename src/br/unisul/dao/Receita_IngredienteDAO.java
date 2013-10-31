package br.unisul.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.unisul.Constantes;
import br.unisul.dados.Ingrediente;
import br.unisul.dados.Receita;
import br.unisul.dados.Receita_Ingrediente;
import br.unisul.dados.Unidade;

public class Receita_IngredienteDAO extends GenericDAO {

	public void cadastreIngredienteNaReceita(Receita_Ingrediente receita_Ingrediente) throws DAOException {
		Connection connection = null;
		PreparedStatement pstmt = null;
		try {
			connection = getConnection();
			pstmt = connection.prepareStatement(Constantes.Receita_Ingrediente.QUERY_INSERT_RECEITA_INGREDIENTE);
			pstmt.setInt(1, receita_Ingrediente.getIngredientes().getCodigo());
			pstmt.setInt(2, receita_Ingrediente.getReceita().getCodigo());
			pstmt.setInt(3, receita_Ingrediente.getUnidade().getCodigo());
			pstmt.setDouble(4, receita_Ingrediente.getQuantidade());
			pstmt.executeUpdate();
		} catch (SQLException e) {
			throw new DAOException("Ocorreu um erro no banco de dados " + "ao cadastrar o ingrediente na receita", e);
		} finally {
			close(pstmt);
			close(connection);
		}
	}

	public List<Receita_Ingrediente> listeTodosIngredientesDasReceitas() throws DAOException {
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			connection = getConnection();
			pstmt = connection.prepareStatement(Constantes.Receita_Ingrediente.QUERY_LIST_TODAS_RECEITAS_INGREDIENTES);
			rs = pstmt.executeQuery();
			List<Receita_Ingrediente> lista = new ArrayList<Receita_Ingrediente>();
			while (rs.next()) {
				Integer codigo_receita = rs.getInt("cd_receita");
				Receita receita = new Receita(codigo_receita, null, null, null, null);
				
				Integer codigo_ingrediente = rs.getInt("cd_ingrediente");
				Ingrediente ingrediente = new Ingrediente(codigo_ingrediente, null);
				
				Integer codigo_unidade = rs.getInt("cd_unidade");
				Unidade unidade = new Unidade(codigo_unidade, null);
				
				double quantidade = rs.getDouble("quantidade");
				Receita_Ingrediente ri = new Receita_Ingrediente(receita, ingrediente, unidade, quantidade);
				lista.add(ri);
			}
			return lista;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		} finally {
			close(rs);
			close(pstmt);
			close(connection);
		}
	}

	public List<Receita_Ingrediente> listeIngredientesMaisUtilizados() throws DAOException {
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			connection = getConnection();
			pstmt = connection.prepareStatement(Constantes.Receita_Ingrediente.QUERY_LIST_INGREDIENTES_MAIS_UTILIZADOS);
			rs = pstmt.executeQuery();
			List<Receita_Ingrediente> lista = new ArrayList<Receita_Ingrediente>();
			while (rs.next()) {
				// O RESULT SET PODE SER TANTO:
				// O NUMERO DA POSICAO DO SELECT rs.getString(2)
				// COMO O NOME DO ATRIBUTO DO SELECT
				// rs.getString("nm_ingrediente")
				String nomeIngrediente = rs.getString(2);
				Ingrediente ingrediente = new Ingrediente(null, nomeIngrediente);
				
				double quantidade = rs.getDouble(1);
				
				Receita_Ingrediente ri = new Receita_Ingrediente(null, ingrediente, null, quantidade);
				lista.add(ri);
			}
			return lista;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		} finally {
			close(rs);
			close(pstmt);
			close(connection);
		}
	}
}
