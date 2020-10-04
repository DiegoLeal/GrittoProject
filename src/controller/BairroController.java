package controller;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import modelo.dao.BairroDao;
import modelo.dao.DaoFactory;
import modelo.entidades.Bairro;

public class BairroController {
	
	Bairro bairro = new Bairro();
	BairroDao bairro_dao = DaoFactory.createBairroDao();
	
	private Bairro jsonToBairro(JSONObject json) {
		
		try {
			bairro.setId(json.getInt("id"));
			bairro.setNome_bairro(json.getString("nome"));
			
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		return bairro;
	}
	
	private JSONObject bairroToJson(Bairro bairro) {
		JSONObject json = new JSONObject();
		
		try {
			
			json.put("id", bairro.getId());
			json.put("nome", bairro.getNome_bairro());
			
			return json;
			
		} catch (JSONException e) {
			
			JSONObject json_err = new JSONObject();
			json_err.put("erro", e.getMessage());
			return json_err;
		}
	}
	
	public JSONObject Create(JSONObject json) {
		
		bairro = jsonToBairro(json);
		bairro_dao.insert(bairro);
		
		return bairroToJson(bairro);
	}
	
	public JSONObject Show(Integer id) {
		JSONObject json = new JSONObject();
		
		try {
			
			bairro = bairro_dao.findById(id);
			
			return bairroToJson(bairro);
			
		} catch (JSONException e) {
			
			json.put("erro", e.getMessage());
			return json;
		}
	}
	
	public JSONObject Edit(JSONObject json) {
		
		bairro = jsonToBairro(json);
		bairro_dao.update(bairro);
		
		return bairroToJson(bairro);
	}
	
	public JSONArray Index() {
		
		try {
			List<Bairro> bairros = bairro_dao.findAll();
			JSONArray json = new JSONArray();
			
			bairros.forEach(bairro_atual -> {
				json.put(bairroToJson(bairro_atual));
			});
			return json;
			
		} catch (JSONException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public JSONObject Delete(Integer id) {
		JSONObject json = new JSONObject();
		
		try {
			
			bairro_dao.deleteById(id);
			return bairroToJson(bairro);
			
		} catch (Exception e) {
			json.put("Erro", e.getMessage());
			return json;
		}
	}
}
