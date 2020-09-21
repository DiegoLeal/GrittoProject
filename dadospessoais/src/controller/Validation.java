package controller;

import org.json.JSONException;
import org.json.JSONObject;

import modelo.entidades.Endereco;

public class Validation {

	public JSONObject EnderecoValidation(JSONObject json) {
		
		try {
	
			json.getString("cep");
			if(json.getString("cep") == null) {
				throw new JSONException("O cep não pode ser vazio!"); 
			} else if(json.getString("cep").length() < 8) {
				throw new JSONException("O cep é inválido");
			}
			
			json.getString("cidade");
			if(json.getString("cidade") == null) {
				throw new JSONException("A cidade não pode estar vazia");
			}
			
			json.getString("bairro");
			if(json.getString("bairro") == null) {
				throw new JSONException("O bairro não pode estar vazio");
			}
			
			json.getString("rua");
			if(json.getString("rua") == null) {
				throw new JSONException("O bairro não pode estar vazio");
			}
			
		}catch(JSONException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
}
