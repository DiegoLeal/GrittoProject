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
					+ "(CEP, CIDADE_ID, BAIRRO_ID, RUA_ID) "
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
					+ "SET CEP = ?, CIDADE_ID = ?, BAIRRO_ID = ?, RUA_ID = ? "
					+ "WHERE ID = ?");
			
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
			st = conn.prepareStatement("DELETE FROM endereco WHERE Id = ?");
			
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
					"SELECT enderco.*,cidade.NOME_CIDADE as CID, "
					+ "bairro.NOME_BAIRRO as BRO, rua.NOME_RUA as RUA "
					+ "FROM endereco INNER JOIN cidade "
					+ "ON endereco.CIDADE_ID = cidade.ID "
					+ "INNER JOIN bairro "
					+ "ON endereco.BAIRRO_ID = bairro.ID "
					+ "INNER JOIN rua "
					+ "ON endereco.RUA_ID = rua.ID"
					+ "WHERE endereco.ID = ?");		
			st.setInt(1, id);
			rs = st.executeQuery();	
			if (rs.next()) {
				Cidade cid = instantiateCidade(rs);
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
					"SELECT enderco.*,cidade.NOME_CIDADE as CID, "
					+ "bairro.NOME_BAIRRO as BRO, rua.NOME_RUA as RUA "
					+ "FROM endereco INNER JOIN cidade "
					+ "ON endereco.CIDADE_ID = cidade.ID "
					+ "INNER JOIN bairro "
					+ "ON endereco.BAIRRO_ID = bairro.ID "
					+ "INNER JOIN rua "
					+ "ON endereco.RUA_ID = rua.ID "
					+ "ORDER BY CID");		
									
			rs = st.executeQuery();	
			
			List<Endereco> list = new ArrayList<>();
			Map<Integer, Cidade> cidMap = new HashMap<>();
			Map<Integer, Bairro> broMap = new HashMap<>();
			Map<Integer, Rua> ruaMap = new HashMap<>();
			
			while (rs.next()) {
				
				Cidade cid = cidMap.get(rs.getInt("CIDADE_ID"));
				if (cid == null) {
					cid = instantiateCidade(rs);
					cidMap.put(rs.getInt("CIDADE_ID"), cid);
				}
				
				Bairro bro = broMap.get(rs.getInt("BAIRRO_ID"));
				if (bro == null) {
					bro = instantiateBairro(rs);
					broMap.put(rs.getInt("BAIRRO_ID"), bro);
				}
				
				Rua rua = ruaMap.get(rs.getInt("RUA_ID"));
				if (rua == null) {
					rua = instantiateRua(rs);
					ruaMap.put(rs.getInt("RUA_ID"), rua);
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
	
	private Cidade instantiateCidade(ResultSet rs) throws SQLException {
		Cidade cid = new Cidade();
		cid.setId(rs.getInt("CIDADE_ID"));
		cid.setNome_cidade(rs.getString("CID"));
		return cid;
	}
	
	private Bairro instantiateBairro(ResultSet rs) throws SQLException {
		Bairro bro = new Bairro();
		bro.setId(rs.getInt("BAIRRO_ID"));
		bro.setNome_bairro(rs.getString("BRO"));
		return bro;
	}
	
	private Rua instantiateRua(ResultSet rs) throws SQLException {
		Rua rua = new Rua();
		rua.setId(rs.getInt("RUA_ID"));
		rua.setNome_rua(rs.getString("RUA"));
		return rua;
	}
	
	private Endereco instantiateEndereco(ResultSet rs, Cidade cid, Bairro bro, Rua rua) throws SQLException {
		Endereco end = new Endereco();
		end.setCep(rs.getString("CEP"));
		end.setCidade(cid);
		end.setBairro(bro);
		end.setRua(rua);
		return end;
	}

}
