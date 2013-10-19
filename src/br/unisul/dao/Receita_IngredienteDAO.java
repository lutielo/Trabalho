package br.unisul.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.unisul.dados.Ingrediente;
import br.unisul.dados.Receita;
import br.unisul.dados.Receita_Ingrediente;

public class Receita_IngredienteDAO extends GenericDAO {
	
	public void cadastreIngredienteNaReceita(Receita_Ingrediente receita_Ingrediente) throws DAOException{

		Connection connection = null;
		PreparedStatement pstmt = null;
		try {
			connection = getConnection();
			String sql = "INSERT INTO receita_ingrediente (cd_ingrediente, cd_receita, quantidade) " +
					" values (?, ?, ?)";
			pstmt = connection.prepareStatement(sql);

			pstmt.setInt(1, receita_Ingrediente.getIngredientes().getCodigo());
			pstmt.setInt(2, receita_Ingrediente.getReceita().getCodigo());
			pstmt.setDouble(3, receita_Ingrediente.getQuantidade());

			pstmt.executeUpdate();
		} catch (SQLException e) {
			throw new DAOException ("Ocorreu um erro no banco de dados " + "ao cadastrar o ingrediente na receita", e);
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
			String sql = "SELECT cd_receita_ingrediente, receita.cd_receita, ingrediente.cd_ingrediente, quantidade FROM receita_ingrediente " +
					"  ORDER BY cd_receita";
			pstmt = connection.prepareStatement(sql);
			
			rs = pstmt.executeQuery();
			
			List<Receita_Ingrediente> lista = new ArrayList<Receita_Ingrediente>();
			while(rs.next()){
				Integer codigo = rs.getInt("cd_receita_ingrediente");
				
				Integer codigo_receita = rs.getInt("cd_receita");
				Receita receita = new Receita(codigo_receita,null, null, null, null);
				
				Integer codigo_ingrediente = rs.getInt("cd_ingrediente");
				Ingrediente ingrediente = new Ingrediente(codigo_ingrediente, null, null);
				
				Double quantidade = rs.getDouble("quantidade");
				
				Receita_Ingrediente ri = new Receita_Ingrediente(codigo, receita, ingrediente, quantidade);
				lista.add(ri);
			}
			
			return lista;
		}catch(SQLException e){
			e.printStackTrace();
			return null;
		} finally {
			close(rs);
			close(pstmt);
			close(connection);
		}
	}
	
	public void deletaIngredienteDaReceita(Receita_Ingrediente receita_Ingrediente) throws DAOException {
		Connection connection = null;
		PreparedStatement pstmt = null;

		try {
			connection = getConnection();
			String sql = "DELETE FROM receita_ingrediente WHERE cd_receita_ingrediente = ?";
			pstmt = connection.prepareStatement(sql);

			pstmt.setInt(1, receita_Ingrediente.getCodigo());

			pstmt.executeUpdate();
		} catch (Exception e) {
			throw new DAOException("Ocorreu um erro ao deletar o ingrediente da receita",e);
		} finally {
			close(pstmt);
			close(connection);
		}
	}
}
