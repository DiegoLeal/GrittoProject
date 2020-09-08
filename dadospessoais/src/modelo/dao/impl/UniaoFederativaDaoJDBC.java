package modelo.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import db.DB;
import db.DbException;
import db.DbIntegrityException;
import modelo.dao.UniaoFederativaDao;
import modelo.entidades.UniaoFederativa;

public class UniaoFederativaDaoJDBC implements UniaoFederativaDao{
	private Connection conn;
	
	public UniaoFederativaDaoJDBC(Connection conn) {
		this.conn = conn;
	}
	
	@Override
	public void insert(UniaoFederativa obj) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement(
					"INSERT INTO uniao_federativa "
					+ "(NOME_UF "
					+ "VALUES "
					+ "(?)",
					Statement.RETURN_GENERATED_KEYS);
			
			st.setString(1, obj.getNome_uf());
			
			int rowsAffected = st.executeUpdate();
			
			if (rowsAffected > 0) {
				ResultSet rs = st.getGeneratedKeys();
				if (rs.next()) {
					int id = rs.getInt(1);
					obj.setId(id);
				}
			}
			else {
				throw new DbException("Erro inesperado! Nenhuma linha afetada!");
			}
		}
		catch(SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
		}
	}

	@Override
	public void update(UniaoFederativa obj) {
		PreparedStatement st = null;
		
		try {
			st = conn.prepareStatement(
					"UPDATE uniao_federativa "
					+ "SET NOME_UF = ? "
					+ "WHERE ID = ?");
			
			st.setString(1, obj.getNome_uf());
			st.setInt(2, obj.getId());
			
			st.executeUpdate();
		}
		catch(SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
		}
	}

	@Override
	public void deleteById(Integer id) {
		PreparedStatement st = null;
		
		try {
			st = conn.prepareStatement(
					"DELETE FROM uniao_federativa "
					+ "WHERE ID = ?");
			st.setInt(1, id);
			st.executeUpdate();
		}
		catch(SQLException e) {
			throw new DbIntegrityException(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
		}
	}

	@Override
	public UniaoFederativa findById(Integer id) {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement(
					"SELECT * FROM uniao_federativa WHERE ID = ?");
			st.setInt(1, id);
			rs = st.executeQuery();
			
			if(rs.next()) {
				UniaoFederativa obj = new UniaoFederativa();
				obj.setId(rs.getInt("ID"));
				obj.setNome_uf(rs.getString("NOME_UF"));
				return obj;
			}
			return null;
		}
		catch(SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
	}

	@Override
	public List<UniaoFederativa> findAll() {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement(
					"SELECT FROM uniao_federativa ORDER BY NOME_UF");
			rs = st.executeQuery();
			
			List<UniaoFederativa> list = new ArrayList<>();
			
			while (rs.next()) {
				UniaoFederativa obj = new UniaoFederativa();
				obj.setId(rs.getInt("ID"));
				obj.setNome_uf(rs.getString("NOME_UF"));
				list.add(obj);
			}
			return list;
		}
		catch (SQLException e ) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
	}
}

