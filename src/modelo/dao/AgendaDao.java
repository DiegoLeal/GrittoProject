package modelo.dao;

import modelo.entidades.Agenda;

import java.sql.SQLException;
import java.util.Set;

public interface AgendaDao {

    void delete(Agenda agenda) throws SQLException;

    Agenda findById(Agenda agenda) throws SQLException;

    Agenda update(Agenda agenda);

    Agenda save(Agenda agenda);

    Set<Agenda> findAll();
}
