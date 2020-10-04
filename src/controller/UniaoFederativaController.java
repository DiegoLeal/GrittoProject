package controller;

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
		JSONObject json = new JSONObject();
		
		try {
			
			json.put("id", uf.getId());
			json.put("nome", uf.getNome_uf());
			
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		return json;
	}
	
	public JSONObject Create(JSONObject json) {
		
		uf = jsonToUniaoFederativa(json);		
		uf_dao.insert(uf);
		
		return uniaofederativaToJson(uf);
	}
	
	public JSONObject Show(Integer id) {
		JSONObject json = new JSONObject();
		
		try {
			
			uf = uf_dao.findById(id);
			return uniaofederativaToJson(uf);
			
		} catch (Exception e) {
			json.put("Erro", e.getMessage());
			return json;
		}
		
	}
	
	public JSONObject Edit(JSONObject json) {
		
		uf = jsonToUniaoFederativa(json);
		uf_dao.update(uf);
		
		return uniaofederativaToJson(uf);
	
	}
	
	public JSONArray Index() {
		JSONArray json = new JSONArray();
		
		try {
			
			List<UniaoFederativa> ufs = uf_dao.findAll();
			
			ufs.forEach(uf_atual -> {
				json.put(uniaofederativaToJson(uf_atual));
			});
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return json;
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
