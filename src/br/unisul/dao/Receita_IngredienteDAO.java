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
import br.unisul.dados.Receita_Ingrediente;
import br.unisul.dados.Unidade;

public class Receita_IngredienteDAO extends GenericDAO {

	public void cadastreIngredienteNaReceita(Receita_Ingrediente receita_Ingrediente) throws DAOException {
		Connection connection = null;
		PreparedStatement pstmt = null;
		try {
			connection = getConnection();
			pstmt = connection.prepareStatement(Constantes.Receita_Ingrediente.QUERY_INSERT_RECEITA_INGREDIENTE);
			pstmt.setInt(1, receita_Ingrediente.getIngrediente().getCodigo());
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

	public void alterarIngredienteLiNaReceita(Receita_Ingrediente receitaIngredienteNEW, Receita_Ingrediente receitaIngredienteOLD) throws DAOException {
		Connection connection = null;
		PreparedStatement pstmt = null;
		try {
			connection = getConnection();
			pstmt = connection.prepareStatement(Constantes.Receita_Ingrediente.QUERY_ALTER_INGREDIENTE_DA_RECEITA);
			pstmt.setInt(1, receitaIngredienteNEW.getIngrediente().getCodigo());
			pstmt.setInt(2, receitaIngredienteNEW.getUnidade().getCodigo());
			pstmt.setDouble(3, receitaIngredienteNEW.getQuantidade());
			pstmt.setInt(4, receitaIngredienteOLD.getReceita().getCodigo());
			pstmt.setInt(5, receitaIngredienteOLD.getIngrediente().getCodigo());
			pstmt.setInt(6, receitaIngredienteOLD.getUnidade().getCodigo());
			pstmt.executeUpdate();
		} catch (SQLException e) {
			throw new DAOException("Ocorreu um erro ao precessar sua alteração", e);
		} finally {
			close(pstmt);
			close(connection);
		}
	}

	public List<Receita_Ingrediente> listarIngredientesDaReceita(Receita_Ingrediente receitaIngrediente) throws DAOException {
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			connection = getConnection();
			pstmt = connection.prepareStatement(Constantes.Receita_Ingrediente.QUERY_LIST_INGREDIENTES_DA_RECEITA);
			pstmt.setInt(1, receitaIngrediente.getReceita().getCodigo());
			rs = pstmt.executeQuery();
			List<Receita_Ingrediente> lista = new ArrayList<Receita_Ingrediente>();
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
				Double vezes = rs.getDouble("vezes");

				Integer codIngrediente = rs.getInt("cd_ingrediente");
				String nomeIngrediente = rs.getString("nm_ingrediente");
				Ingrediente ingrediente = new Ingrediente(codIngrediente, nomeIngrediente);

				Integer codUnidade = rs.getInt("cd_unidade");
				String tpUunidade = rs.getString("tp_unidade");
				Unidade unidade = new Unidade(codUnidade, tpUunidade);

				Receita_Ingrediente ri = new Receita_Ingrediente(null, ingrediente, unidade, vezes);
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

	public List<Receita_Ingrediente> listeReceitaQueUsamIngrediente(Unidade unidade, Ingrediente ingrediente) throws DAOException {
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			connection = getConnection();
			pstmt = connection.prepareStatement(Constantes.Receita_Ingrediente.QUERY_LIST_RECEITAS_USAM_TAL_INGREDIENTE);
			pstmt.setInt(1, ingrediente.getCodigo());
			pstmt.setInt(2, unidade.getCodigo());
			rs = pstmt.executeQuery();
			List<Receita_Ingrediente> lista = new ArrayList<Receita_Ingrediente>();
			while (rs.next()) {
				String nomeAutor = rs.getString("nm_autor");
				Autor autor = new Autor(null, nomeAutor, null);

				String nomeReceita = rs.getString("nm_receita");
				Date data = rs.getDate("dt_criacao");
				Receita receita = new Receita(null, nomeReceita, data, null, autor);

				Receita_Ingrediente ri = new Receita_Ingrediente(receita, null, null, null);
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
