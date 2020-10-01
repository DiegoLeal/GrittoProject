package aplicacao;

import db.Validation;
import db.ValidationException;
import modelo.entidades.CatServico;

public class TestesValidacao {

	public static void main(String[] args) throws ValidationException {
		CatServico catservico = new CatServico(1, "123456789");
		Validation validation = new Validation(catservico);

		validation.validate();

	}

}
