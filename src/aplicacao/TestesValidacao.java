package aplicacao;

import db.Validation;
import db.ValidationException;
import modelo.entidades.Profissao;

public class TestesValidacao {

  public static void main(String[] args) throws ValidationException {
    Profissao profissao = new Profissao(1, "123456789");
    Validation validation = new Validation(profissao);

    validation.validate();

  }

}
