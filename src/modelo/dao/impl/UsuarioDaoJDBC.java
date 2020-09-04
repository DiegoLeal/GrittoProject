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
import modelo.dao.UsuarioDao;
import modelo.entidades.Profissao;
import modelo.entidades.Usuario;

public class UsuarioDaoJDBC implements UsuarioDao {
	
	private Connection conn;
	
	public UsuarioDaoJDBC(Connection conn) {
		this.conn = conn;
	}

	@Override
	public void insert(Usuario obj) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement(
					"INSERT INTO usuario "
					+ "(Nome, Rg, Cpf, DataNascimento, Telefone, Sexo, ProfissaoId) "
					+ "VALUES "
					+ "(?, ?, ?, ?, ?, ?, ?)",
					Statement.RETURN_GENERATED_KEYS);
			
			st.setString(1, obj.getNome());
			st.setString(2, obj.getRg());
			st.setString(3, obj.getCpf());
			st.setDate(4, new java.sql.Date(obj.getDataNascimento().getTime()));
			st.setString(5, obj.getTelefone());
			st.setString(6, obj.getSexo());
			st.setInt(7, obj.getProfissao().getId());
			
			int rowsAffected = st.executeUpdate();
			
			if (rowsAffected > 0) {
				ResultSet rs = st.getGeneratedKeys();
				if (rs.next()) {
					int id = rs.getInt(1);
					obj.setId(id);
				}
				DB.closeResultSet(rs);
			}
			else {
				throw new DbException("Erro inesperado! Nenhuma linha afetada");
			}
	    }
		catch (SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
		}
   }

	@Override
	public void update(Usuario obj) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement(
					"UPDATE usuario "
					+ "SET Nome = ?, Rg = ?, Cpf = ?, DataNascimento = ?, Telefone = ?, Sexo = ?, ProfissaoId = ? "
					+ "WHERE Id = ?");
					
			
			st.setString(1, obj.getNome());
			st.setString(2, obj.getRg());
			st.setString(3, obj.getCpf());
			st.setDate(4, new java.sql.Date(obj.getDataNascimento().getTime()));
			st.setString(5, obj.getTelefone());
			st.setString(6, obj.getSexo());
			st.setInt(7, obj.getProfissao().getId());
			st.setInt(8, obj.getId());
			
			st.executeUpdate();					
	    }
		catch (SQLException e) {
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
			st = conn.prepareStatement("DELETE FROM usuario WHERE Id = ?");
			
			st.setInt(1, id);
			
			st.executeUpdate();
		}
		catch (SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(st); 
		}
	} 
	
	@Override
	public List<Usuario> findAll() {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement(
					"SELECT usuario.*,profissao.Nome as ProfNome "  
					+ "FROM usuario INNER JOIN profissao "  
					+ "ON usuario.ProfissaoId = profissao.Id "					
					+ "ORDER BY Nome");		
									
			rs = st.executeQuery();	
			
			List<Usuario> list = new ArrayList<>();
			Map<Integer, Profissao> map = new HashMap<>();
			
			while (rs.next()) {
				
				Profissao prof = map.get(rs.getInt("ProfissaoId"));
				
				if (prof == null) {
					prof = instantiateProfissao(rs);
					map.put(rs.getInt("ProfissaoId"), prof);
				}				
				
				Usuario obj = instantiateUsuario(rs, prof);
				list.add(obj); 
			}
			return list;
		}
		catch (SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
	}

	@Override
	public Usuario findById(Integer id) {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement(
					"SELECT usuario.*,profissao.Nome as ProfNome "
					+ "FROM usuario INNER JOIN profissao "
					+ "ON usuario.ProfissaoId = profissao.Id "
					+ "WHERE usuario.Id = ?");		
			st.setInt(1, id);
			rs = st.executeQuery();	
			if (rs.next()) {
				Profissao prof = instantiateProfissao(rs);
				Usuario obj = instantiateUsuario(rs,prof);
				return obj;
			}
			return null;
		}
		catch (SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
	}

	private Usuario instantiateUsuario(ResultSet rs, Profissao prof) throws SQLException {
		Usuario obj = new Usuario();
		obj.setId(rs.getInt("Id"));
		obj.setNome(rs.getString("Nome"));
		obj.setRg(rs.getString("Rg"));
		obj.setCpf(rs.getString("Cpf"));
		obj.setDataNascimento(rs.getDate("DataNascimento"));
		obj.setTelefone(rs.getString("Telefone"));
		obj.setSexo(rs.getString("Sexo"));
		obj.setProfissao(prof);
		return obj;
	}	

	private Profissao instantiateProfissao(ResultSet rs) throws SQLException {
		Profissao prof = new Profissao();
		prof.setId(rs.getInt("ProfissaoId"));
		prof.setNome(rs.getString("ProfNome"));
		return prof;
	}

	@Override
	public List<Usuario> findByProfissao(Profissao profissao) {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement(
					"SELECT usuario.*,profissao.Nome as ProfNome "  
					+ "FROM usuario INNER JOIN profissao "  
					+ "ON usuario.ProfissaoId = profissao.Id "
					+ "WHERE ProfissaoId = ? "
					+ "ORDER BY Nome");		
			
			st.setInt(1, profissao.getId());
			
			rs = st.executeQuery();	
			
			List<Usuario> list = new ArrayList<>();
			Map<Integer, Profissao> map = new HashMap<>();
			
			while (rs.next()) {
				
				Profissao prof = map.get(rs.getInt("ProfissaoId"));
				
				if (prof == null) {
					prof = instantiateProfissao(rs);
					map.put(rs.getInt("ProfissaoId"), prof);
				}				
				
				Usuario obj = instantiateUsuario(rs, prof);
				list.add(obj); 
			}
			return list;
		}
		catch (SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
	}
}