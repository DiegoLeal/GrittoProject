package controller;

import java.sql.SQLException;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import modelo.dao.CidadeDao;
import modelo.dao.DaoFactory;
import modelo.entidades.Cidade;
import modelo.entidades.UniaoFederativa;

public class CidadeController {
	
	Cidade cidade = new Cidade();
	CidadeDao cidade_dao = DaoFactory.createCidadeDao();
	
	JSONObject json = new JSONObject();
	
	private Cidade jsonToCidade(JSONObject json) {
		
		UniaoFederativa uf = new UniaoFederativa();
		
		try {
			cidade.setId(json.getInt("id"));
			cidade.setNome_cidade(json.getString("nome"));
			uf.setId(json.getInt("uf_id"));
			cidade.setUf(uf);
						
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		return cidade;
	}
	
	private JSONObject cidadeToJson(Cidade cidade) {
		
		try {
			
			json.put("id", cidade.getId());
			json.put("nome", cidade.getNome_cidade());
			json.put("uf_id", cidade.getUf().getId());
			
			return json;
			
		} catch (JSONException e) {
			
			JSONObject json_err = new JSONObject();
			json_err.put("erro", e.getMessage());
			return json_err;
			
		}
	}
	
	public JSONObject Create(JSONObject json) throws SQLException {
		
		cidade_dao.insert(cidade);
		
		return cidadeToJson(cidade);
	}
	
	public JSONObject Show(Integer id) {
		
		try {
			
			cidade = cidade_dao.findById(id);
			return cidadeToJson(cidade);
					
		} catch (JSONException e) {
			
			json.put("erro", e.getMessage());
			return json;
			
		}
	}
	
	public JSONObject Edit(JSONObject json) throws SQLException {
		
		cidade = jsonToCidade(json);
		cidade_dao.update(cidade);
		
		return cidadeToJson(cidade);
	}
	
	public JSONArray Index() {
		
		try {
			
			List<Cidade> cidades = cidade_dao.findAll();
			JSONArray json = new JSONArray();
			
			cidades.forEach(cidade_atual -> {
				json.put(cidadeToJson(cidade_atual));
			});
			return json;
			
		} catch (JSONException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public JSONObject Delete(Integer id) {
		
		try {
			cidade_dao.deleteById(id);
			return cidadeToJson(cidade);
			
		} catch (Exception e) {
			
			json.put("Erro", e.getMessage());
			return json;
		}
	}
}
