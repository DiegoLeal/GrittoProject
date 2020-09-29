package controller;

import java.sql.SQLException;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import modelo.dao.DaoFactory;
import modelo.dao.EnderecoDao;
import modelo.entidades.Bairro;
import modelo.entidades.Cidade;
import modelo.entidades.Endereco;
import modelo.entidades.Rua;

public class EnderecoController {
	
	private Endereco jsonToEndereco(JSONObject json) {
		Endereco endereco = new Endereco();
		Bairro bairro = new Bairro();
		Cidade cidade = new Cidade();
		Rua rua = new Rua();
		
		try {
			endereco.setId(json.getInt("id"));
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		cidade.setId(json.getInt("cidade_id"));
		endereco.setCidade(cidade);
		bairro.setId(json.getInt("bairro_id"));
		endereco.setBairro(bairro);
		rua.setId(json.getInt("rua_id"));
		endereco.setRua(rua);
		endereco.setCep(json.getString("cep"));
		return endereco;
	}
	
	private JSONObject enderecoToJson(Endereco endereco) {
		JSONObject json = new JSONObject();
		
		json.put("id", endereco.getId());
		json.put("cidade_id", endereco.getCidade().getId());
		json.put("cid_nome", endereco.getCidade().getNome_cidade());
		json.put("uf_id", endereco.getCidade().getUf().getId());
		json.put("uf_nome", endereco.getCidade().getUf().getNome_uf());
		json.put("bairro_id", endereco.getBairro().getId());
		json.put("bairro_nome", endereco.getBairro().getNome_bairro());
		json.put("rua_id", endereco.getRua().getId());
		json.put("rua_nome", endereco.getRua().getNome_rua());
		json.put("cep", endereco.getCep());
				
		return json;
	}
	
	public JSONObject Create(JSONObject json) throws SQLException, JSONException {
		Endereco endereco = jsonToEndereco(json);
		EnderecoDao dao = DaoFactory.createEnderecoDao();
		
		dao.insert(endereco);
		
		return enderecoToJson(endereco);
	}
	
	public JSONObject Show(Integer id) throws SQLException {
		Endereco endereco = new Endereco();
		
		try {
			
			EnderecoDao dao = DaoFactory.createEnderecoDao();
			endereco = dao.findById(id);
			return enderecoToJson(endereco);
			
		} catch (Exception e) {
			
			JSONObject json = new JSONObject();
			json.put("erro", e.getMessage());
			return json;
			
		}
	}
	
	public JSONObject Edit(JSONObject json) throws SQLException {
		Endereco endereco = jsonToEndereco(json);
		EnderecoDao dao = DaoFactory.createEnderecoDao();
		
		dao.update(endereco);
		
		return enderecoToJson(endereco);
	}
	
	public JSONArray Index() throws SQLException {
		Endereco endereco = new Endereco();
		EnderecoDao dao = DaoFactory.createEnderecoDao();
		
		List<Endereco> enderecos = dao.findAll();
		JSONArray json = new JSONArray();
		
		enderecos.forEach(endereco_atual -> {
			json.put(enderecoToJson(endereco_atual));
		});
		
		return json;
	}
	
	public JSONObject Delete(Integer id) {
		Endereco endereco = new Endereco();
		EnderecoDao dao = DaoFactory.createEnderecoDao();
		
		try {
			dao.deleteById(id);
			return enderecoToJson(endereco);
			
		} catch (Exception e) {
			JSONObject json = new JSONObject();
			json.put("Erro", e.getMessage());
			return json;
		}
	}
}
