package br.unisul.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.unisul.dados.Ingrediente;
import br.unisul.dados.Unidade;

public class IngredienteDAO extends GenericDAO {

	private final String QUERY_INSERT_INGREDIENTE = "INSERT INTO ingrediente (nm_ingrediente, cd_unidade) values (?, ?)";
	private final String QUERY_LIST_TODOS_INGREDIENTES = "SELECT cd_ingrediente, nm_ingrediente, unidade.cd_unidade, unidade.tp_unidade " + " FROM ingrediente " + " JOIN unidade ON ingrediente.cd_unidade = unidade.cd_unidade " + " ORDER BY tp_unidade";
	private final String QUERY_LIST_INGREDIENTES_NOME = "SELECT cd_ingrediente, nm_ingrediente, unidade.cd_unidade, unidade.tp_unidade " + " FROM ingrediente " + " JOIN unidade ON ingrediente.cd_unidade = unidade.cd_unidade " + " WHERE upper(nm_ingrediente) LIKE  upper(?) ORDER BY nm_ingrediente ";
	private final String QUERY_LIST_INGREDIENTE_COD = "SELECT cd_ingrediente, nm_ingrediente, cd_unidade FROM ingrediente WHERE cd_ingrediente = (?) ";
	private final String QUERY_DELETE_INGREDIENTE = "DELETE FROM ingrediente WHERE cd_ingrediente = (?)";

	public void cadastreIngrediente(Ingrediente ingrediente) throws DAOException {
		Connection connection = null;
		PreparedStatement pstmt = null;
		try {
			connection = getConnection();
			pstmt = connection.prepareStatement(QUERY_INSERT_INGREDIENTE);
			pstmt.setString(1, ingrediente.getNome());
			pstmt.setInt(2, ingrediente.getUnidade().getCodigo());
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
			pstmt = connection.prepareStatement(QUERY_LIST_TODOS_INGREDIENTES);
			rs = pstmt.executeQuery();
			List<Ingrediente> lista = new ArrayList<Ingrediente>();
			while (rs.next()) {
				Integer codigo = rs.getInt("cd_ingrediente");
				String nome = rs.getString("nm_ingrediente");
				String nomeUnidade = rs.getString("tp_unidade");
				Integer codigo_unidade = rs.getInt("cd_unidade");
				Unidade unidade = new Unidade(codigo_unidade, nomeUnidade);
				Ingrediente i = new Ingrediente(codigo, nome, unidade);
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
			pstmt = connection.prepareStatement(QUERY_LIST_INGREDIENTES_NOME);
			pstmt.setString(1, "%" + nomeRecebido + "%");
			rs = pstmt.executeQuery();
			List<Ingrediente> lista = new ArrayList<Ingrediente>();
			while (rs.next()) {
				Integer codigo = rs.getInt("cd_ingrediente");
				String nome = rs.getString("nm_ingrediente");

				String nomeUnidade = rs.getString("tp_unidade");
				Integer codigo_unidade = rs.getInt("cd_unidade");
				Unidade unidade = new Unidade(codigo_unidade, nomeUnidade);
				Ingrediente i = new Ingrediente(codigo, nome, unidade);
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
			pstmt = connection.prepareStatement(QUERY_LIST_INGREDIENTE_COD);
			pstmt.setInt(1, codigoRecebido);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				Integer codigo = rs.getInt("cd_ingrediente");
				String nome = rs.getString("nm_ingrediente");
				Integer codigo_unidade = rs.getInt("cd_unidade");
				Unidade unidade = new Unidade(codigo_unidade, null);
				return new Ingrediente(codigo, nome, unidade);
			} else {
				return null;
			}
		} catch (SQLException e) {
			throw new DAOException("Ocorreu um erro ao localizar um ingrediente. C�digo = " + codigoRecebido, e);
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
			pstmt = connection.prepareStatement(QUERY_DELETE_INGREDIENTE);
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