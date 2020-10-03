package controller;

import java.sql.SQLException;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import modelo.dao.DaoFactory;
import modelo.dao.RuaDao;
import modelo.entidades.Rua;

public class RuaController {
	
	Rua rua = new Rua();
	RuaDao rua_dao = DaoFactory.createRuaDao();
	
	JSONObject json = new JSONObject();
	
	private Rua jsonToRua(JSONObject json) {
		
		try {
			
			rua.setId(json.getInt("id"));
			rua.setNome_rua(json.getString("nome"));
			
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		return rua;
	}
	
	private JSONObject ruaToJson(Rua rua) {
		
		try {
			
			json.put("id", rua.getId());
			json.put("nome", rua.getNome_rua());
			
			return json;
			
		} catch (JSONException e) {
			json.put("erro", e.getMessage());
			return json;
		}	
	}
	
	public JSONObject Create(JSONObject json) throws SQLException {
		
		rua = jsonToRua(json);
		rua_dao.insert(rua);
		
		return ruaToJson(rua);
	}
	
	public JSONObject Show(Integer id) {
		
		try {
			rua = rua_dao.findById(id);
			return ruaToJson(rua);
			
		} catch (JSONException e) {
			json.put("erro", e.getMessage());
			return json;
		}
	}
	
	public JSONObject Edit(JSONObject json) throws SQLException {
		
		rua = jsonToRua(json);
		rua_dao.update(rua);
		
		return ruaToJson(rua);
	}
	
	public JSONArray Index() {
		
		try {
			
			List<Rua> ruas = rua_dao.findAll();
			JSONArray json = new JSONArray();
			
			ruas.forEach(rua_atual -> {
				json.put(ruaToJson(rua_atual));
			});
			
			return json;
			
		} catch (JSONException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public JSONObject Delete(Integer id) {
		
		try {
			
			rua_dao.deleteById(id);
			return ruaToJson(rua);
			
		} catch (Exception e) {
			json.put("Erro", e.getMessage());
			return json;
		}
	}
}
