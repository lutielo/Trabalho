package br.unisul.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.unisul.dados.Autor;

public class AutorDAO extends GenericDAO {

	private final String QUERY_INSERT_AUTOR = "INSERT INTO autor (nm_autor) values (?)";
	private final String QUERY_LIST_TODOS_AUTORES = "SELECT cd_autor, nm_autor FROM autor ORDER BY nm_autor";
	private final String QUERY_LIST_AUTORES_NOME = "SELECT cd_autor, nm_autor FROM autor WHERE upper(nm_autor) LIKE  upper(?) ORDER BY nm_autor ";
	private final String QUERY_LIST_AUTOR_COD = "SELECT cd_autor, nm_autor FROM autor WHERE cd_autor = (?) ";
	private final String QUERY_DELETE_AUTOR = "DELETE FROM autor WHERE cd_autor = (?)";

	public void cadastreAutor(Autor autor) throws DAOException {
		Connection connection = null;
		PreparedStatement pstmt = null;
		try {
			connection = getConnection();
			pstmt = connection.prepareStatement(QUERY_INSERT_AUTOR);
			pstmt.setString(1, autor.getNome());
			pstmt.executeUpdate();
		} catch (SQLException e) {
			throw new DAOException("Ocorreu um erro no banco de dados " + "ao cadastrar o autor da receita", e);
		} finally {
			close(pstmt);
			close(connection);
		}
	}

	public List<Autor> listeTodosAutores() throws DAOException {
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			connection = getConnection();
			pstmt = connection.prepareStatement(QUERY_LIST_TODOS_AUTORES);
			rs = pstmt.executeQuery();
			List<Autor> lista = new ArrayList<Autor>();
			while (rs.next()) {
				Integer codigo = rs.getInt("cd_autor");
				String nome = rs.getString("nm_autor");
				Autor a = new Autor(codigo, nome);
				lista.add(a);
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

	public List<Autor> listeTodosAutoresPeloNome(String nomeRecebido) throws DAOException {
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			connection = getConnection();
			pstmt = connection.prepareStatement(QUERY_LIST_AUTORES_NOME);
			pstmt.setString(1, "%" + nomeRecebido + "%");
			rs = pstmt.executeQuery();
			List<Autor> lista = new ArrayList<Autor>();
			while (rs.next()) {
				Integer codigo = rs.getInt("cd_autor");
				String nome = rs.getString("nm_autor");
				Autor a = new Autor(codigo, nome);
				lista.add(a);
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

	public Autor listeDadosDoAutorPeloCodigo(int codigoRecebido) throws DAOException {
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			connection = getConnection();
			pstmt = connection.prepareStatement(QUERY_LIST_AUTOR_COD);
			pstmt.setInt(1, codigoRecebido);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				int codigo = rs.getInt("cd_autor");
				String nome = rs.getString("nm_autor");
				return new Autor(codigo, nome);
			} else {
				return null;
			}
		} catch (SQLException e) {
			throw new DAOException("Ocorreu um erro ao localizar um autor. Código = " + codigoRecebido, e);
		} finally {
			close(rs);
			close(pstmt);
			close(connection);
		}
	}

	public void deletaAutor(Autor autor) throws DAOException {
		Connection connection = null;
		PreparedStatement pstmt = null;
		try {
			connection = getConnection();
			pstmt = connection.prepareStatement(QUERY_DELETE_AUTOR);
			pstmt.setInt(1, autor.getCodigo());
			pstmt.executeUpdate();
		} catch (Exception e) {
			throw new DAOException("Ocorreu um erro ao deletar o autor da receita", e);
		} finally {
			close(pstmt);
			close(connection);
		}
	}
}