package controller;

import java.sql.SQLException;

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
		json.put("bairro_id", endereco.getBairro().getId());
		json.put("rua_id", endereco.getRua().getId());
		json.put("cep", endereco.getCep());
		
		return json;
	}
	
	private JSONObject Create(JSONObject json) throws SQLException, JSONException {
		Endereco endereco = jsonToEndereco(json);
		EnderecoDao dao = DaoFactory.createEnderecoDao();
		
		dao.insert(endereco);
		
		return enderecoToJson(endereco);
	}
	
	private JSONObject Show(int id) throws SQLException {
		Endereco endereco = new Endereco();
		
		try {
			
			EnderecoDao dao = DaoFactory.createEnderecoDao();
			dao.findById(id);
			return enderecoToJson(endereco);
			
		} catch (Exception e) {
			
			JSONObject json = new JSONObject();
			json.put("erro", e.getMessage());
			return json;
			
		}
	}
	
	private JSONObject Edit(JSONObject json) throws SQLException {
		Endereco endereco = jsonToEndereco(json);
		EnderecoDao dao = DaoFactory.createEnderecoDao();
		
		dao.update(endereco);
		
		return enderecoToJson(endereco);
	}
}
