package controller;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import modelo.dao.DaoFactory;
import modelo.dao.UsuarioDao;
import modelo.entidades.CatServico;
import modelo.entidades.Usuario;

public class UsuarioController {
	
	Usuario usuario = new Usuario();
	UsuarioDao usuario_dao = DaoFactory.createUsuarioDao();
	
	private Usuario jsonToUsuario(JSONObject json) throws JSONException, ParseException {
		CatServico catservico = new CatServico();
		DateFormat df = DateFormat.getDateInstance(DateFormat.MEDIUM);
		
		try {
			
			usuario.setNome(json.getString("nome"));
			usuario.setRg(json.getString("rg"));
			usuario.setCpf(json.getString("cpf"));
			
			Date date = df.parse(json.getString("dataNascimento"));
			usuario.setDataNascimento(date);
			
			usuario.setTelefone(json.getString("telefone"));
			usuario.setSenha(json.getString("senha"));
			usuario.setEmail(json.getString("email"));
			usuario.setSexo(json.getString("sexo"));
			//catservico.setId(json.getInt("catServico"));
			//usuario.setCatServico(catservico);
			
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		return usuario;
	}
	
	private JSONObject usuarioToJson(Usuario usuario) {
		JSONObject json = new JSONObject();
		
		try {
			
			json.put("id", usuario.getId());
			json.put("nome", usuario.getNome());
			json.put("rg", usuario.getRg());
			json.put("cpf", usuario.getCpf());
			json.put("dataNascimeto", usuario.getDataNascimento());
			json.put("telefone", usuario.getTelefone());
			json.put("senha", usuario.getSenha());
			json.put("email", usuario.getEmail());
			json.put("sexo", usuario.getSexo());
			json.put("catServico", usuario.getCatServico().getId());
			return json;
			
		} catch (JSONException e) {
			
			JSONObject json_err = new JSONObject();
			json_err.put("erro", e.getMessage());
			return json_err;
		}
	}
	
	public JSONObject Create(JSONObject json) throws ParseException {
		
		usuario = jsonToUsuario(json);
		usuario_dao.insert(usuario);
		System.out.println("Cadastro Efetuado");
		
		return usuarioToJson(usuario);
	}
	
	public JSONObject Show(Integer id) {
		JSONObject json = new JSONObject();
		
		try {
			
			usuario = usuario_dao.findById(id);
			
			return usuarioToJson(usuario);
			
		} catch (JSONException e) {
			
			json.put("erro", e.getMessage());
			return json;
		}
	}
	
	public JSONObject Edit(JSONObject json) throws ParseException {
		
		usuario = jsonToUsuario(json);
		usuario_dao.update(usuario);
		
		return usuarioToJson(usuario);
	}
	
	public JSONArray Index() {
		
		try {
			List<Usuario> usuarios = usuario_dao.findAll();
			JSONArray json = new JSONArray();
			
			usuarios.forEach(usuario_atual -> {
				json.put(usuarioToJson(usuario_atual));
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
			
			usuario_dao.deleteById(id);
			return usuarioToJson(usuario);
			
		} catch (Exception e) {
			json.put("Erro", e.getMessage());
			return json;
		}
	}
}
