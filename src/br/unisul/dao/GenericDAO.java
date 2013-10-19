package br.unisul.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class GenericDAO {

	protected void registreDriver() throws DAOException{
		try {
			Class.forName("org.postgresql.Driver");
		} catch (Exception e) {
			throw new DAOException("Ocorreu um erro ao registrar o driver JDBC", e);
		}
	}

	protected Connection getConnection() throws DAOException{
		registreDriver();
		String urlJDBC = "jdbc:postgresql://localhost/prog2";
		try {
			return DriverManager.getConnection(urlJDBC, "postgres", "postgres");
		} catch (Exception e) {
			throw new DAOException("Ocorreu um erro ao realizar a conexão com o banco de dados", e);
		}
	}

	protected void close(ResultSet rs) {
		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	protected void close(PreparedStatement pstmt) {
		if (pstmt != null) {
			try {
				pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	protected void close(Connection connection) {
		if (connection != null) {
			try {
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	protected java.sql.Date toSQLDate(java.util.Date utilDate) {
		if (utilDate == null) {
			return null;
		} else {
			return new java.sql.Date(utilDate.getTime());			
		}
	}

	protected java.util.Date toUtilDate(java.sql.Date sqlDate) {
		if (sqlDate == null) {
			return null;
		} else {
			return new java.util.Date(sqlDate.getTime());			
		}
	}
}
