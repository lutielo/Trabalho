package br.unisul.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.unisul.Constantes;
import br.unisul.dados.Ingrediente;

public class IngredienteDAO extends GenericDAO {

	public void cadastreIngrediente(Ingrediente ingrediente) throws DAOException {
		Connection connection = null;
		PreparedStatement pstmt = null;
		try {
			connection = getConnection();
			pstmt = connection.prepareStatement(Constantes.Ingrediente.QUERY_INSERT_INGREDIENTE);
			pstmt.setString(1, ingrediente.getNome());
			pstmt.executeUpdate();
		} catch (SQLException e) {
			throw new DAOException("Ocorreu um erro no banco de dados " + "ao cadastrar o ingrediente", e);
		} finally {
			close(pstmt);
			close(connection);
		}
	}

	public List<Ingrediente> listeTodosIngredientes() throws DAOException {
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			connection = getConnection();
			pstmt = connection.prepareStatement(Constantes.Ingrediente.QUERY_LIST_TODOS_INGREDIENTES);
			rs = pstmt.executeQuery();
			List<Ingrediente> lista = new ArrayList<Ingrediente>();
			while (rs.next()) {
				Integer codigo = rs.getInt("cd_ingrediente");
				String nome = rs.getString("nm_ingrediente");
				Ingrediente i = new Ingrediente(codigo, nome);
				lista.add(i);
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

	public List<Ingrediente> listeTodosIngredientesPeloNome(String nomeRecebido) throws DAOException {
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			connection = getConnection();
			pstmt = connection.prepareStatement(Constantes.Ingrediente.QUERY_LIST_INGREDIENTES_NOME);
			pstmt.setString(1, "%" + nomeRecebido + "%");
			rs = pstmt.executeQuery();
			List<Ingrediente> lista = new ArrayList<Ingrediente>();
			while (rs.next()) {
				Integer codigo = rs.getInt("cd_ingrediente");
				String nome = rs.getString("nm_ingrediente");

				Ingrediente i = new Ingrediente(codigo, nome);
				lista.add(i);
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

	public Ingrediente listeDadosDoIngredientePeloCodigo(int codigoRecebido) throws DAOException {
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			connection = getConnection();
			pstmt = connection.prepareStatement(Constantes.Ingrediente.QUERY_LIST_INGREDIENTE_COD);
			pstmt.setInt(1, codigoRecebido);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				Integer codigo = rs.getInt("cd_ingrediente");
				String nome = rs.getString("nm_ingrediente");
				
				return new Ingrediente(codigo, nome);
			} else {
				return null;
			}
		} catch (SQLException e) {
			throw new DAOException("Ocorreu um erro ao localizar um ingrediente. Código = " + codigoRecebido, e);
		} finally {
			close(rs);
			close(pstmt);
			close(connection);
		}
	}

	public void deletaIngrediente(Ingrediente ingrediente) throws DAOException {
		Connection connection = null;
		PreparedStatement pstmt = null;

		try {
			connection = getConnection();
			pstmt = connection.prepareStatement(Constantes.Ingrediente.QUERY_DELETE_INGREDIENTE);
			pstmt.setInt(1, ingrediente.getCodigo());
			pstmt.executeUpdate();
		} catch (Exception e) {
			throw new DAOException("Ocorreu um erro ao deletar o ingrediente", e);
		} finally {
			close(pstmt);
			close(connection);
		}
	}
}
