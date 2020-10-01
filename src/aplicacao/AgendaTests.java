package aplicacao;

import modelo.dao.AgendaDao;
import modelo.dao.DaoFactory;
import modelo.dao.impl.AgendaDaoImpl;
import modelo.entidades.Agenda;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;

public class AgendaTests {

	public static void main(String[] args) throws SQLException {
		Agenda agenda = new Agenda(null, LocalDate.now(), "mensagem", "historico", LocalTime.now());

		DaoFactory.createAgendaDao().save(agenda);
	}

}
