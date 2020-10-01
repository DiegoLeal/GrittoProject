package controller;

import java.sql.SQLException;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import modelo.dao.DaoFactory;
import modelo.dao.UniaoFederativaDao;
import modelo.entidades.UniaoFederativa;

public class UniaoFederativaController {
	
	UniaoFederativa uf = new UniaoFederativa();
	UniaoFederativaDao uf_dao = DaoFactory.createUniaoFederativaDao();
	
	JSONObject json = new JSONObject();
	
	private UniaoFederativa jsonToUniaoFederativa(JSONObject json) {
				
		try {
			
			uf.setId(json.getInt("id"));
			uf.setNome_uf(json.getString("nome"));
			
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		return uf;
	}
	
	private JSONObject uniaofederativaToJson(UniaoFederativa uf) {
		
		try {
			
			json.put("id", uf.getId());
			json.put("nome", uf.getNome_uf());
			
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		return json;
	}
	
	public JSONObject Create(JSONObject json) throws SQLException, JSONException {
		
		uf = jsonToUniaoFederativa(json);		
		uf_dao.insert(uf);
		
		return uniaofederativaToJson(uf);
	}
	
	public JSONObject Show(Integer id) throws SQLException, JSONException {
		
		try {
			
			uf = uf_dao.findById(id);
			return uniaofederativaToJson(uf);
			
		} catch (Exception e) {
			json.put("Erro", e.getMessage());
			return json;
		}
		
	}
	
	public JSONObject Edit(JSONObject json) throws SQLException, JSONException {
		
		uf = jsonToUniaoFederativa(json);
		uf_dao.update(uf);
		
		return uniaofederativaToJson(uf);
	
	}
	
	public JSONArray Index() throws SQLException, JSONException {
		
		try {
			
			List<UniaoFederativa> ufs = uf_dao.findAll();
			JSONArray json = new JSONArray();
			
			ufs.forEach(uf_atual -> {
				json.put(uniaofederativaToJson(uf_atual));
			});
			
			return json;
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public JSONObject Delete(Integer id) {
		
		try {
			
			uf_dao.deleteById(id);
			return uniaofederativaToJson(uf);
			
		} catch (Exception e) {
			JSONObject json = new JSONObject();
			json.put("Erro", e.getMessage());
			return json;
		}
	}
}
