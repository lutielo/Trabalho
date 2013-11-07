package br.unisul.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.unisul.Constantes;
import br.unisul.dados.Autor;
import br.unisul.dados.Sexo;

public class AutorDAO extends GenericDAO {

	public void cadastrarAutor(Autor autor) throws DAOException {
		Connection connection = null;
		PreparedStatement pstmt = null;
		try {
			connection = getConnection();
			pstmt = connection.prepareStatement(Constantes.Autor.QUERY_INSERT_AUTOR);
			pstmt.setString(1, autor.getNome());
			pstmt.setInt(2, autor.getSexo().getCodigo());
			pstmt.executeUpdate();
		} catch (SQLException e) {
			throw new DAOException("Ocorreu um erro no banco de dados " + "ao cadastrar o autor da receita", e);
		} finally {
			close(pstmt);
			close(connection);
		}
	}

	public void alterarAutor(Autor autor) throws DAOException {
		Connection connection = null;
		PreparedStatement pstmt = null;
		try {
			connection = getConnection();
			pstmt = connection.prepareStatement(Constantes.Autor.QUERY_ALTER_AUTOR);
			pstmt.setString(1, autor.getNome());
			pstmt.setInt(2, autor.getSexo().getCodigo());
			pstmt.setInt(3, autor.getCodigo());
			pstmt.executeUpdate();
		} catch (SQLException e) {
			throw new DAOException("Ocorreu um erro ao precesar sua alteração", e);
		} finally {
			close(pstmt);
			close(connection);
		}
	}

	public List<Autor> listarTodosAutores() throws DAOException {
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			connection = getConnection();
			pstmt = connection.prepareStatement(Constantes.Autor.QUERY_LIST_TODOS_AUTORES);
			rs = pstmt.executeQuery();
			List<Autor> lista = new ArrayList<Autor>();
			while (rs.next()) {
				Integer codigo = rs.getInt("cd_autor");
				String nome = rs.getString("nm_autor");
				
				String deSexo = rs.getString("de_sexo");
				Sexo sexo = new Sexo(null, deSexo);
				
				Autor a = new Autor(codigo, nome, sexo);
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

	public List<Autor> listarAutoresPeloNome(String nomeRecebido) throws DAOException {
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			connection = getConnection();
			pstmt = connection.prepareStatement(Constantes.Autor.QUERY_LIST_AUTORES_NOME);
			pstmt.setString(1, "%" + nomeRecebido + "%");
			rs = pstmt.executeQuery();
			List<Autor> lista = new ArrayList<Autor>();
			while (rs.next()) {
				Integer codigo = rs.getInt("cd_autor");
				String nome = rs.getString("nm_autor");

				String deSexo = rs.getString("de_sexo");
				Sexo sexo = new Sexo(null, deSexo);
				
				Autor a = new Autor(codigo, nome, sexo);
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

	public Autor listarAutorPeloCodigo(int codigoRecebido) throws DAOException {
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			connection = getConnection();
			pstmt = connection.prepareStatement(Constantes.Autor.QUERY_LIST_AUTOR_COD);
			pstmt.setInt(1, codigoRecebido);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				int codigo = rs.getInt("cd_autor");
				String nome = rs.getString("nm_autor");
				
				String deSexo = rs.getString("de_sexo");
				Sexo sexo = new Sexo(null, deSexo);
				
				return new Autor(codigo, nome, sexo);
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

	public void deletarAutor(Autor autor) throws DAOException {
		Connection connection = null;
		PreparedStatement pstmt = null;
		try {
			connection = getConnection();
			pstmt = connection.prepareStatement(Constantes.Autor.QUERY_DELETE_AUTOR);
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
