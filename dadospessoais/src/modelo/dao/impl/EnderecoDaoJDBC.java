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
import modelo.dao.EnderecoDao;
import modelo.entidades.Bairro;
import modelo.entidades.Cidade;
import modelo.entidades.Endereco;
import modelo.entidades.Rua;
import modelo.entidades.UniaoFederativa;

public class EnderecoDaoJDBC implements EnderecoDao{
	
	private Connection conn;
	
	public EnderecoDaoJDBC(Connection conn) {
		this.conn = conn;
	}

	@Override
	public void insert(Endereco obj) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement(
					"INSERT INTO endereco "
					+ "(cep, cidade_id, bairro_id, rua_id) "
					+ "VALUES "
					+ "(?,?,?,?)",
					Statement.RETURN_GENERATED_KEYS);
			
			st.setString(1, obj.getCep());
			st.setInt(2, obj.getCidade().getId());
			st.setInt(3, obj.getBairro().getId());
			st.setInt(4, obj.getRua().getId());
			
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
	public void update(Endereco obj) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement(
					"UPDATE endereco "
					+ "SET cep = ?, cidade_id = ?, bairro_id = ?, rua_id = ? "
					+ "WHERE id = ?");
			
			st.setString(1, obj.getCep());
			st.setInt(2, obj.getCidade().getId());
			st.setInt(3, obj.getBairro().getId());
			st.setInt(4, obj.getRua().getId());
			st.setInt(5, obj.getId());
			
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
			st = conn.prepareStatement("DELETE FROM endereco WHERE id = ?");
			
			st.setInt(1, id);
			
			st.executeUpdate();
		}
		catch (SQLException e) {
			throw new DbIntegrityException(e.getMessage());
		}
		finally {
			DB.closeStatement(st); 
		}		
	}

	@Override
	public Endereco findById(Integer id) {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement(
					"SELECT endereco.*,cidade.nome as CID, "
					+ "bairro.nome as BRO, rua.nome as RUA, "
					+ "uniaofederativa.nome as UF, uniaofederativa.id "
					+ "FROM endereco INNER JOIN cidade "
					+ "ON endereco.cidade_id = cidade.id "
					+ "INNER JOIN uniaofederativa "
					+ "ON cidade.uf_id = uniaofederativa.id "
					+ "INNER JOIN bairro "
					+ "ON endereco.bairro_id = bairro.id "
					+ "INNER JOIN rua "
					+ "ON endereco.rua_id = rua.id "
					+ "WHERE endereco.id = ?");		
			st.setInt(1, id);
			
			rs = st.executeQuery();	
			
			if (rs.next()) {
				UniaoFederativa uf = instantiateUf(rs);
				Cidade cid = instantiateCidade(rs, uf);				
				Bairro bro = instantiateBairro(rs);
				Rua rua = instantiateRua(rs);
				Endereco obj = instantiateEndereco(rs,cid, bro, rua);
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

	@Override
	public List<Endereco> findAll() {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement(
					"SELECT endereco.id,cidade.nome as CID, "
					+ "bairro.nome as BRO, rua.nome as RUA, "
					+ "uniaofederativa.nome as UF, uniaofederativa.id "
					+ "FROM endereco INNER JOIN cidade "
					+ "ON endereco.cidade_id = cidade.id "
					+ "INNER JOIN uniaofederativa "
					+ "ON cidade.uf_id = uniaofederativa.id "
					+ "INNER JOIN bairro "
					+ "ON endereco.bairro_id = bairro.id "
					+ "INNER JOIN rua "
					+ "ON endereco.rua_id = rua.id "
					+ "ORDER BY CID");		
									
			rs = st.executeQuery();	
			
			List<Endereco> list = new ArrayList<>();
			Map<Integer, Cidade> cidMap = new HashMap<>();
			Map<Integer, Bairro> broMap = new HashMap<>();
			Map<Integer, Rua> ruaMap = new HashMap<>();
			Map<Integer, UniaoFederativa> ufMap = new HashMap<>();
			
			while (rs.next()) {
				
				UniaoFederativa uf = ufMap.get(rs.getInt("id"));
				if (uf == null) {
					uf = instantiateUf(rs);
					ufMap.put(rs.getInt("id"), uf);
				}
				
				Cidade cid = cidMap.get(rs.getInt("cidade_id"));
				if (cid == null) {
					cid = instantiateCidade(rs, uf);
					cidMap.put(rs.getInt("cidade_id"), cid);
				}
				
				Bairro bro = broMap.get(rs.getInt("bairro_id"));
				if (bro == null) {
					bro = instantiateBairro(rs);
					broMap.put(rs.getInt("bairro_id"), bro);
				}
				
				Rua rua = ruaMap.get(rs.getInt("rua_id"));
				if (rua == null) {
					rua = instantiateRua(rs);
					ruaMap.put(rs.getInt("rua_id"), rua);
				}
				
				Endereco obj = instantiateEndereco(rs, cid, bro, rua);
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
	
	private UniaoFederativa instantiateUf(ResultSet rs) throws SQLException {
		UniaoFederativa uf = new UniaoFederativa();
		uf.setId(rs.getInt("id"));
		uf.setNome_uf(rs.getString("UF"));
		return uf;
	}

	private Cidade instantiateCidade(ResultSet rs, UniaoFederativa uf) throws SQLException {
		Cidade cid = new Cidade();
		cid.setId(rs.getInt("cidade_id"));
		cid.setNome_cidade(rs.getString("CID"));
		uf.setId(rs.getInt("id"));
		cid.setUf(uf);
		return cid;
	}
	
	private Bairro instantiateBairro(ResultSet rs) throws SQLException {
		Bairro bro = new Bairro();
		bro.setId(rs.getInt("bairro_id"));
		bro.setNome_bairro(rs.getString("BRO"));
		return bro;
	}
	
	private Rua instantiateRua(ResultSet rs) throws SQLException {
		Rua rua = new Rua();
		rua.setId(rs.getInt("rua_id"));
		rua.setNome_rua(rs.getString("RUA"));
		return rua;
	}
	
	private Endereco instantiateEndereco(ResultSet rs, Cidade cid, Bairro bro, Rua rua) throws SQLException {
		Endereco end = new Endereco();
		end.setId(rs.getInt("id"));
		end.setCep(rs.getString("cep"));
		end.setCidade(cid);
		end.setBairro(bro);
		end.setRua(rua);
		return end;
	}

}
