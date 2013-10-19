package br.unisul.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.unisul.dados.Autor;
import br.unisul.dados.Receita;

public class ReceitaDAO extends GenericDAO {

	private final String QUERY_INSERT_RECEITA = "INSERT INTO receita (nm_receita, cd_autor, dt_criacao, tx_modo_preparo) " + "values (?, ?, ?, ?)";
	private final String QUERY_LIST_TODAS_RECEITAS = "SELECT cd_receita, nm_receita, dt_criacao, tx_modo_preparo, autor.cd_autor" + " FROM receita " + " JOIN autor ON receita.cd_autor = autor.cd_autor " + " ORDER BY nm_receita ";
	private final String QUERY_LIST_RECEITAS_NOME = "SELECT cd_receita, nm_receita, dt_criacao, tx_modo_preparo, autor.cd_autor, autor.nm_autor" + " FROM receita " + " JOIN autor ON receita.cd_autor = autor.cd_autor " + " WHERE upper(nm_receita) LIKE  upper(?) ORDER BY nm_receita ";
	private final String QUERY_LIST_RECEITA_COD = "SELECT cd_receita, nm_receita, dt_criacao, tx_modo_preparo, autor.cd_autor, autor.nm_autor" + " FROM receita " + " JOIN autor ON receita.cd_autor = autor.cd_autor " + " WHERE cd_receita = ? ";
	private final String QUUERY_DELETE_RECEITA = "DELETE FROM receita WHERE cd_receita = ?";

	public void cadastreReceita(Receita receita) throws DAOException {
		Connection connection = null;
		PreparedStatement pstmt = null;
		try {
			connection = getConnection();
			pstmt = connection.prepareStatement(QUERY_INSERT_RECEITA);
			pstmt.setString(1, receita.getNome());
			pstmt.setInt(2, receita.getAutor().getCodigo());
			pstmt.setDate(3, toSQLDate(receita.getDt_criacao()));
			pstmt.setString(4, receita.getModo_preparo());
			pstmt.executeUpdate();
		} catch (SQLException e) {
			throw new DAOException("Ocorreu um erro no banco de dados " + "ao cadastrar a receita", e);
		} finally {
			close(pstmt);
			close(connection);
		}
	}

	public List<Receita> listeTodasReceitas() throws DAOException {
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			connection = getConnection();
			pstmt = connection.prepareStatement(QUERY_LIST_TODAS_RECEITAS);
			rs = pstmt.executeQuery();
			List<Receita> lista = new ArrayList<Receita>();
			while (rs.next()) {
				Integer codigo = rs.getInt("cd_receita");
				String nome = rs.getString("nm_receita");
				Date data = (rs.getDate("dt_criacao"));
				String modo_preparo = rs.getString("tx_modo_preparo");
				Integer codigo_autor = rs.getInt("cd_autor");
				Autor autor = new Autor(codigo_autor, null);
				Receita r = new Receita(codigo, nome, data, modo_preparo, autor);
				lista.add(r);
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

	public List<Receita> listeTodasReceitasPeloNome(String nomeRecebido) throws DAOException {
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			connection = getConnection();
			pstmt = connection.prepareStatement(QUERY_LIST_RECEITAS_NOME);
			pstmt.setString(1, "%" + nomeRecebido + "%");

			rs = pstmt.executeQuery();

			List<Receita> lista = new ArrayList<Receita>();
			while (rs.next()) {
				Integer codigo = rs.getInt("cd_receita");
				String nome = rs.getString("nm_receita");
				Date data = (rs.getDate("dt_criacao"));
				String modo_preparo = rs.getString("tx_modo_preparo");
				// Código modelo para instanciar referência dentro de objeto
				Integer codigo_autor = rs.getInt("cd_autor");
				String nomeAutor = rs.getString("nm_autor");
				Autor autor = new Autor(codigo_autor, nomeAutor);
				Receita r = new Receita(codigo, nome, data, modo_preparo, autor);
				lista.add(r);
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

	public Receita listeDadosDaReceitaPeloCodigo(int codigoRecebido) throws DAOException {
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			connection = getConnection();
			pstmt = connection.prepareStatement(QUERY_LIST_RECEITA_COD);
			pstmt.setInt(1, codigoRecebido);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				Integer codigo = rs.getInt("cd_receita");
				String nome = rs.getString("nm_receita");
				Date data = (rs.getDate("dt_criacao"));
				String modo_preparo = rs.getString("tx_modo_preparo");
				Integer codigo_autor = rs.getInt("cd_autor");
				String nomeAutor = rs.getString("nm_autor");
				Autor autor = new Autor(codigo_autor, nomeAutor);
				return new Receita(codigo, nome, data, modo_preparo, autor);
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

	public void deletaReceita(Receita receita) throws DAOException {
		Connection connection = null;
		PreparedStatement pstmt = null;
		try {
			connection = getConnection();
			pstmt = connection.prepareStatement(QUUERY_DELETE_RECEITA);
			pstmt.setInt(1, receita.getCodigo());
			pstmt.executeUpdate();
		} catch (Exception e) {
			throw new DAOException("Ocorreu um erro ao deletar a receita", e);
		} finally {
			close(pstmt);
			close(connection);
		}
	}
}