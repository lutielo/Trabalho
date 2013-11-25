package br.unisul.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.unisul.Constantes;
import br.unisul.dados.Autor;
import br.unisul.dados.Ingrediente;
import br.unisul.dados.Receita;
import br.unisul.dados.ReceitaIngrediente;
import br.unisul.dados.Unidade;

public class ReceitaIngredienteDAO extends GenericDAO {

	public void cadastrarIngredienteNaReceita(ReceitaIngrediente receitaIngrediente) throws DAOException {
		Connection connection = null;
		PreparedStatement pstmt = null;
		try {
			connection = getConnection();
			pstmt = connection.prepareStatement(Constantes.Receita_Ingrediente.QUERY_INSERT_RECEITA_INGREDIENTE);
			pstmt.setInt(1, receitaIngrediente.getIngrediente().getCodigo());
			pstmt.setInt(2, receitaIngrediente.getReceita().getCodigo());
			pstmt.setInt(3, receitaIngrediente.getUnidade().getCodigo());
			pstmt.setDouble(4, receitaIngrediente.getQuantidade());
			pstmt.executeUpdate();
		} catch (SQLException e) {
			throw new DAOException("Ocorreu um erro no banco de dados " + "ao cadastrar o ingrediente na receita", e);
		} finally {
			close(pstmt);
			close(connection);
		}
	}

	public void alterarIngredienteNaReceita(ReceitaIngrediente receitaIngredienteNEW, ReceitaIngrediente receitaIngredienteOLD) throws DAOException {
		Connection connection = null;
		PreparedStatement pstmt = null;
		try {
			connection = getConnection();
			pstmt = connection.prepareStatement(Constantes.Receita_Ingrediente.QUERY_ALTER_INGREDIENTE_DA_RECEITA);
			pstmt.setInt(1, receitaIngredienteNEW.getUnidade().getCodigo());
			pstmt.setDouble(2, receitaIngredienteNEW.getQuantidade());
			pstmt.setInt(3, receitaIngredienteOLD.getReceita().getCodigo());
			pstmt.setInt(4, receitaIngredienteOLD.getIngrediente().getCodigo());
			pstmt.setInt(5, receitaIngredienteOLD.getUnidade().getCodigo());
			pstmt.executeUpdate();
		} catch (SQLException e) {
			throw new DAOException("Ocorreu um erro ao precessar sua alteração", e);
		} finally {
			close(pstmt);
			close(connection);
		}
	}

	public List<ReceitaIngrediente> listarIngredientesDaReceita(ReceitaIngrediente receitaIngrediente) throws DAOException {
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			connection = getConnection();
			pstmt = connection.prepareStatement(Constantes.Receita_Ingrediente.QUERY_LIST_INGREDIENTES_DA_RECEITA);
			pstmt.setInt(1, receitaIngrediente.getReceita().getCodigo());
			rs = pstmt.executeQuery();
			List<ReceitaIngrediente> lista = new ArrayList<ReceitaIngrediente>();
			while (rs.next()) {
				Integer codigoReceita = rs.getInt("cd_receita");
				String nomeReceita = rs.getString("nm_receita");
				Receita receita = new Receita(codigoReceita, nomeReceita, null, null, null);

				Integer codigo_ingrediente = rs.getInt("cd_ingrediente");
				String nomeIngrediente = rs.getString("nm_ingrediente");
				Ingrediente ingrediente = new Ingrediente(codigo_ingrediente, nomeIngrediente);

				Integer codigoUnidade = rs.getInt("cd_unidade");
				String tipoUnidade = rs.getString("tp_unidade");
				Unidade unidade = new Unidade(codigoUnidade, tipoUnidade);

				double quantidade = rs.getDouble("quantidade");
				ReceitaIngrediente ri = new ReceitaIngrediente(receita, ingrediente, unidade, quantidade);
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

	public List<ReceitaIngrediente> listarTodosIngredientesDasReceitas() throws DAOException {
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			connection = getConnection();
			pstmt = connection.prepareStatement(Constantes.Receita_Ingrediente.QUERY_LIST_TODAS_RECEITAS_INGREDIENTES);
			rs = pstmt.executeQuery();
			List<ReceitaIngrediente> lista = new ArrayList<ReceitaIngrediente>();
			while (rs.next()) {
				Integer codigo_receita = rs.getInt("cd_receita");
				Receita receita = new Receita(codigo_receita, null, null, null, null);

				Integer codigo_ingrediente = rs.getInt("cd_ingrediente");
				Ingrediente ingrediente = new Ingrediente(codigo_ingrediente, null);

				Integer codigo_unidade = rs.getInt("cd_unidade");
				Unidade unidade = new Unidade(codigo_unidade, null);

				double quantidade = rs.getDouble("quantidade");
				ReceitaIngrediente ri = new ReceitaIngrediente(receita, ingrediente, unidade, quantidade);
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

	public List<ReceitaIngrediente> listarIngredientesMaisUtilizados() throws DAOException {
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			connection = getConnection();
			pstmt = connection.prepareStatement(Constantes.Receita_Ingrediente.QUERY_LIST_INGREDIENTES_MAIS_UTILIZADOS);
			rs = pstmt.executeQuery();
			List<ReceitaIngrediente> lista = new ArrayList<ReceitaIngrediente>();
			while (rs.next()) {
				Double vezes = rs.getDouble("vezes");

				Integer codIngrediente = rs.getInt("cd_ingrediente");
				String nomeIngrediente = rs.getString("nm_ingrediente");
				Ingrediente ingrediente = new Ingrediente(codIngrediente, nomeIngrediente);

				Integer codUnidade = rs.getInt("cd_unidade");
				String tpUunidade = rs.getString("tp_unidade");
				Unidade unidade = new Unidade(codUnidade, tpUunidade);

				ReceitaIngrediente ri = new ReceitaIngrediente(null, ingrediente, unidade, vezes);
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

	public List<ReceitaIngrediente> listarReceitaQueUsamIngrediente(Unidade unidade, Ingrediente ingrediente) throws DAOException {
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			connection = getConnection();
			pstmt = connection.prepareStatement(Constantes.Receita_Ingrediente.QUERY_LIST_RECEITAS_USAM_TAL_INGREDIENTE);
			pstmt.setInt(1, ingrediente.getCodigo());
			pstmt.setInt(2, unidade.getCodigo());
			rs = pstmt.executeQuery();
			List<ReceitaIngrediente> lista = new ArrayList<ReceitaIngrediente>();
			while (rs.next()) {
				String nomeAutor = rs.getString("nm_autor");
				Autor autor = new Autor(null, nomeAutor, null);

				String nomeReceita = rs.getString("nm_receita");
				Date data = rs.getDate("dt_criacao");
				Receita receita = new Receita(null, nomeReceita, data, null, autor);

				ReceitaIngrediente ri = new ReceitaIngrediente(receita, null, null, null);
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
	
	public void deletaReceitaIngrediente(ReceitaIngrediente receitaIngrediente) throws DAOException {
		Connection connection = null;
		PreparedStatement pstmt = null;

		try {
			connection = getConnection();
			pstmt = connection.prepareStatement(Constantes.Receita_Ingrediente.QUERY_DELETE_RECEITA_INGREDIENTE);
			pstmt.setInt(1, receitaIngrediente.getIngrediente().getCodigo());
			pstmt.setInt(2, receitaIngrediente.getReceita().getCodigo());
			pstmt.setInt(3, receitaIngrediente.getUnidade().getCodigo());
			pstmt.executeUpdate();
		} catch (Exception e) {
			throw new DAOException("Ocorreu um erro ao deletar o ingrediente", e);
		} finally {
			close(pstmt);
			close(connection);
		}
	}
}