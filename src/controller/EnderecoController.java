package controller;


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
	
	Endereco endereco = new Endereco();
	EnderecoDao dao = DaoFactory.createEnderecoDao();
	

	private Endereco jsonToEndereco(JSONObject json) {
		
		Bairro bairro = new Bairro();
		Cidade cidade = new Cidade();
		Rua rua = new Rua();

		try {
			
			//endereco.setId(json.getInt("id"));
			
			cidade.setId(json.getInt("cidade_id"));
			System.out.println(cidade.getId());
			endereco.setCidade(cidade);
			
			bairro.setId(json.getInt("bairro_id"));
			System.out.println(bairro.getId());
			endereco.setBairro(bairro);
			
			rua.setId(json.getInt("rua_id"));
			System.out.println(rua.getId());
			endereco.setRua(rua);
			
			endereco.setCep(json.getString("cep"));
			
		} catch (JSONException e) {
			e.printStackTrace();
		}

		return endereco;
	}

	private JSONObject enderecoToJson(Endereco endereco) {
		JSONObject json = new JSONObject();
		
		try {
			
			json.put("id", endereco.getId());
			json.put("cep", endereco.getCep());
			
			json.put("cid_nome", endereco.getCidade().getNome_cidade());
			json.put("cidade_id", endereco.getCidade().getId());
			
			json.put("uf_nome", endereco.getCidade().getUf().getNome_uf());
			json.put("uf_id", endereco.getCidade().getUf().getId());
			
			json.put("bairro_nome", endereco.getBairro().getNome_bairro());
			json.put("bairro_id", endereco.getBairro().getId());
			
			json.put("rua_nome", endereco.getRua().getNome_rua());
			json.put("rua_id", endereco.getRua().getId());
			
			return json;
			
		} catch (JSONException e) {

			json.put("erro", e.getMessage());
			return json;
		}
	}

	public JSONObject Create(JSONObject json) {
		
		endereco = jsonToEndereco(json);
		dao.insert(endereco);

		return enderecoToJson(endereco);
	}

	public JSONObject Show(Integer id) {
		JSONObject json = new JSONObject();
		try {

			endereco = dao.findById(id);
			return enderecoToJson(endereco);

		} catch (JSONException e) {

			json.put("erro", e.getMessage());
			return json;

		}
	}

	public JSONObject Edit(JSONObject json) {
		
		endereco = jsonToEndereco(json);
		dao.update(endereco);

		return enderecoToJson(endereco);
	}

	public JSONArray Index() {
		
		try {
			
			List<Endereco> enderecos = dao.findAll();
			JSONArray json = new JSONArray();
			
			enderecos = dao.findAll();
			enderecos.forEach(endereco_atual -> {
				json.put(enderecoToJson(endereco_atual));
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
			
			dao.deleteById(id);
			return enderecoToJson(endereco);

		} catch (Exception e) {
			json.put("Erro", e.getMessage());
			return json;
		}
	}
}
