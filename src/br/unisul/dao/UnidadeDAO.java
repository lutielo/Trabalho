package br.unisul.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.unisul.Constantes;
import br.unisul.dados.Unidade;

public class UnidadeDAO extends GenericDAO {

	public void cadastreUnidade(Unidade unidade) throws DAOException {

		Connection connection = null;
		PreparedStatement pstmt = null;
		try {
			connection = getConnection();
			pstmt = connection.prepareStatement(Constantes.Unidade.QUERY_INSERT_UNIDADE);
			pstmt.setString(1, unidade.getTipo());
			pstmt.executeUpdate();
		} catch (SQLException e) {
			throw new DAOException("Ocorreu um erro no banco de dados " + "ao cadastrar a unidade", e);
		} finally {
			close(pstmt);
			close(connection);
		}
	}

	public List<Unidade> listeTodasUnidades() throws DAOException {
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			connection = getConnection();
			pstmt = connection.prepareStatement(Constantes.Unidade.QUERY_LIST_TODAS_UNIDADES);
			rs = pstmt.executeQuery();
			List<Unidade> lista = new ArrayList<Unidade>();
			while (rs.next()) {
				Integer codigo = rs.getInt("cd_unidade");
				String nome = rs.getString("tp_unidade");
				Unidade u = new Unidade(codigo, nome);
				lista.add(u);
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

	public List<Unidade> listeTodasUnidadesPeloNome(String nomeRecebido) throws DAOException {
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			connection = getConnection();
			pstmt = connection.prepareStatement(Constantes.Unidade.QUERY_LIST_UNIDADES_NOME);
			pstmt.setString(1, "%" + nomeRecebido + "%");
			rs = pstmt.executeQuery();
			List<Unidade> lista = new ArrayList<Unidade>();
			while (rs.next()) {
				Integer codigo = rs.getInt("cd_unidade");
				String nome = rs.getString("tp_unidade");
				Unidade u = new Unidade(codigo, nome);
				lista.add(u);
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

	public Unidade listeDadosDaUnidadePeloCodigo(int codigoRecebido) throws DAOException {
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			connection = getConnection();
			pstmt = connection.prepareStatement(Constantes.Unidade.QUERY_LIST_UNIDADE_COD);
			pstmt.setInt(1, codigoRecebido);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				Integer codigo = rs.getInt("cd_unidade");
				String nome = rs.getString("tp_unidade");
				return new Unidade(codigo, nome);
			} else {
				return null;
			}
		} catch (SQLException e) {
			throw new DAOException("Ocorreu um erro ao localizar uma unidade. Código = " + codigoRecebido, e);
		} finally {
			close(rs);
			close(pstmt);
			close(connection);
		}
	}

	public void deletaUnidade(Unidade unidade) throws DAOException {
		Connection connection = null;
		PreparedStatement pstmt = null;

		try {
			connection = getConnection();
			pstmt = connection.prepareStatement(Constantes.Unidade.QUERY_DELETE_UNIDADE);
			pstmt.setInt(1, unidade.getCodigo());
			pstmt.executeUpdate();
		} catch (Exception e) {
			throw new DAOException("Ocorreu um erro ao deletar a unidade", e);
		} finally {
			close(pstmt);
			close(connection);
		}
	}
}