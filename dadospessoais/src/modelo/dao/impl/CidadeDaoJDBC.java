package modelo.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import db.DB;
import db.DbException;
import db.DbIntegrityException;
import modelo.dao.CidadeDao;
import modelo.entidades.Cidade;
import modelo.entidades.UniaoFederativa;

public class CidadeDaoJDBC implements CidadeDao {
	
private Connection conn;
	
	public CidadeDaoJDBC(Connection conn) {
		this.conn = conn;
	}

	@Override
	public void insert(Cidade obj) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement(
					"INSERT INTO cidade "
					+ "(nome, uf_id) "
					+ "VALUES "
					+ "(?,?)",
					Statement.RETURN_GENERATED_KEYS);
			
			st.setString(1, obj.getNome_cidade());
			st.setInt(2, obj.getUf().getId());
			
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
	public void update(Cidade obj) {
		PreparedStatement st = null;
		
		try {
			st = conn.prepareStatement(
					"UPDATE cidade "
					+ "SET nome = ?, uf_id = ? "
					+ "WHERE id = ?");
			
			st.setString(1, obj.getNome_cidade());
			st.setInt(2, obj.getUf().getId());
			st.setInt(3, obj.getId());
			
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
					"DELETE FROM cidade "
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
	public Cidade findById(Integer id) {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement(
					"SELECT cidade.*, uniaofederativa.nome as UF "
					+ "FROM cidade INNER JOIN uniao_federativa "
					+ "ON cidade.uf_id = uniaofederativa.id "
					+ "WHERE cidade.id = ?");
			st.setInt(1, id);
			rs = st.executeQuery();
			
			if(rs.next()) {
				UniaoFederativa uf = instatiateUniaoFederativa(rs);
				Cidade obj = instantiateCidade(rs, uf);
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
	public List<Cidade> findAll() {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement(
					"SELECT cidade.*, uniaofederativa.nome as UF "
					+ "FROM cidade INNER JOIN uniaofederativa "
					+ "ON cidade.uf_id = uniaofederativa.id "
					+ "ORDER BY nome");
			
			rs = st.executeQuery();
			
			List<Cidade> list = new ArrayList<>();
			Map<Integer, UniaoFederativa> map = new HashMap<>();
			
			while(rs.next()) {
				UniaoFederativa uf = map.get(rs.getInt("uf_id"));
				
				if (uf == null) {
					uf = instatiateUniaoFederativa(rs);
					map.put(rs.getInt("uf_id"), uf);					
				}
				
				Cidade obj = instantiateCidade(rs, uf);
				list.add(obj);
			}
			return list;
		}
		catch(SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}		
	}
	
	public List<Cidade> findByUf(UniaoFederativa uf) {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement(
					"SELECT cidade.*, uniaofederativa.nome as UF "
					+ "FROM cidade INNER JOIN uniaofederativa "
					+ "ON cidade.uf_id = uniaofederativa.id "
					+ "WHERE uf_id = ?"
					+ "ORDER BY nome");
			st.setInt(1, uf.getId());
			
			rs = st.executeQuery();
			
			List<Cidade> list = new ArrayList<>();
			Map<Integer, UniaoFederativa> map = new HashMap<>();
			
			while(rs.next()) {
				UniaoFederativa uniao = map.get(rs.getInt("uf_id"));
				
				if (uniao == null) {
					uniao = instatiateUniaoFederativa(rs);
					map.put(rs.getInt("uf_id"), uniao);					
				}
				
				Cidade obj = instantiateCidade(rs, uniao);
				list.add(obj);
			}
			return list;
		}
		catch(SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}		
	}
	
	private UniaoFederativa instatiateUniaoFederativa(ResultSet rs) throws SQLException{
		UniaoFederativa uf = new UniaoFederativa();
		uf.setId(rs.getInt("uf_id"));
		uf.setNome_uf(rs.getString("UF"));
		return uf;
	}
	
	private Cidade instantiateCidade(ResultSet rs, UniaoFederativa uf) throws SQLException {
		Cidade obj = new Cidade();
		obj.setId(rs.getInt("id"));
		obj.setNome_cidade(rs.getString("nome"));
		obj.setUf(uf);
		return obj;
	}
}
