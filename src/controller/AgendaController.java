package controller;

import modelo.dao.AgendaDao;
import modelo.dao.DaoFactory;
import modelo.entidades.Agenda;
import org.json.JSONObject;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;
import java.util.Optional;

public class AgendaController implements Controller<Agenda> {

  AgendaDao agendaDao = DaoFactory.createAgendaDao();

  @Override
  public JSONObject generateJsonObject(Agenda agenda) {
    LocalDate agendaData = agenda.getData();
    LocalTime agendaHora = agenda.getHora();
    JSONObject data = new JSONObject();
    data.append("id", agenda.getId());
    data.append("data", agendaData != null ? agendaData.toString() : null);
    data.append("descricao", agenda.getDescricao());
    data.append("historico", agenda.getHistorico());
    data.append("hora", agendaHora != null ? agendaHora.toString() : null);
    return data;
  }

  @Override
  public Agenda generateObjectFromJson(JSONObject json) {
    return new Agenda(
            json.has("id") ? json.getLong("id") : null,
            json.has("data") ? LocalDate.parse(json.getString("data")) : null,
            json.has("descricao") ? json.getString("descricao") : null,
            json.has("historico") ? json.getString("historico") : null,
            json.has("hora") ? LocalTime.parse(json.getString("hora")) : null
    );
  }

  @Override
  public JSONObject create(JSONObject json) {
    Agenda agenda = generateObjectFromJson(json);
    try {
      agenda = agendaDao.save(agenda);
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return generateJsonObject(agenda);
  }

  @Override
  public JSONObject show(Long id) {
    Agenda agenda = new Agenda();
    agenda.setId(id);
    try {
      agenda = agendaDao.findById(agenda);
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return generateJsonObject(agenda);
  }
}
