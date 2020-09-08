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
import modelo.dao.BairroDao;
import modelo.entidades.Bairro;

public class BairroDaoJDBC implements BairroDao{
	
	private Connection conn;
	
	public BairroDaoJDBC(Connection conn) {
		this.conn = conn;
	}

	@Override
	public void insert(Bairro obj) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement(
					"INSERT INTO bairro "
					+ "(nome "
					+ "VALUES "
					+ "(?)",
					Statement.RETURN_GENERATED_KEYS);
			
			st.setString(1, obj.getNome_bairro());
			
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
	public void update(Bairro obj) {
		PreparedStatement st = null;
		
		try {
			st = conn.prepareStatement(
					"UPDATE bairro "
					+ "SET nome = ? "
					+ "WHERE id = ?");
			
			st.setString(1, obj.getNome_bairro());
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
					"DELETE FROM bairro "
					+ "WHERE id = ?");
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
	public Bairro findById(Integer id) {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement(
					"SELECT * FROM bairro WHERE id = ?");
			st.setInt(1, id);
			rs = st.executeQuery();
			
			if(rs.next()) {
				Bairro obj = new Bairro();
				obj.setId(rs.getInt("id"));
				obj.setNome_bairro(rs.getString("nome"));
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
	public List<Bairro> findAll() {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement(
					"SELECT FROM bairro ORDER BY nome");
			rs = st.executeQuery();
			
			List<Bairro> list = new ArrayList<>();
			
			while (rs.next()) {
				Bairro obj = new Bairro();
				obj.setId(rs.getInt("id"));
				obj.setNome_bairro(rs.getString("nome"));
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
