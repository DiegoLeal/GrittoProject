package modelo.dao.impl;

import modelo.dao.AgendaDao;
import modelo.entidades.Agenda;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

public class AgendaDaoImpl implements AgendaDao {

    private final Connection connection;

    public AgendaDaoImpl(Connection connection) {
        this.connection = connection;
    }

    private static String INSERT = "INSERT INTO " +
            "Agenda(`id`, `data`, `descricao`, `historico`, `hora`) " +
            "VALUES(?, ?, ?, ?, ?) ";

    private static String UPDATE = "UPDATE Agenda " +
            "SET `data` = ?, `descricao` = ?, `historico` = ?, `hora` = ? " +
            "WHERE `id` = ? ";

    private static String DELETE = "DELETE FROM Agenda WHERE `id` = ?";

    private static String SELECT_ONE = "SELECT " +
            "`id`, `data`, `decricao`, `historico`, `hora` " +
            "FROM Agenda WHERE `id` = ?";

    private static String SELECT_ALL = "SELECT " +
            "`id`, `data`, `mensagem, `historico`, `hora` FROM Agenda ORDER BY id ASC";

    @Override
    public void delete(Agenda agenda) throws SQLException {
        if (agenda.getId() == null) {
            throw new RuntimeException("Invalid deletion without ID reference");
        }
        PreparedStatement statement = createStatement(DELETE);
        statement.setLong(1, agenda.getId());
        statement.execute();
    }

    @Override
    public Agenda findById(Agenda agenda) throws SQLException {
        if (agenda.getId() == null) {
            throw new RuntimeException("Invalid deletion without ID reference");
        }

        PreparedStatement statement = createStatement(SELECT_ONE);
        statement.setLong(1, agenda.getId());
        ResultSet queryResult = statement.executeQuery();
        Set<Agenda> agendaSet = buildFromResultSet(queryResult);
        if (agendaSet.isEmpty()) return null;

        return agendaSet.stream().findAny().get();
    }

    @Override
    public Agenda update(Agenda agenda) throws SQLException {
        if (agenda.getId() == null) {
            return save(agenda);
        }
        PreparedStatement statement = createStatement(UPDATE);

        if (agenda.getData() != null) {
            statement.setDate(1, Date.valueOf(agenda.getData()));
        }

        if (!agenda.getDescricao().isEmpty() || agenda.getDescricao() != null) {
            statement.setString(2, agenda.getDescricao());
        }

        if (!agenda.getHistorico().isEmpty() || agenda.getHistorico() != null) {
            statement.setString(3, agenda.getDescricao());
        }

        if (agenda.getHora() != null) {
            statement.setTime(4, Time.valueOf(agenda.getHora()));
        }

        statement.setLong(5, agenda.getId());

        statement.executeUpdate();

        return agenda;
    }


    @Override
    public Agenda save(Agenda agenda) throws SQLException {
        if(agenda.getId() != null) {
            return update(agenda);
        }

        PreparedStatement statement = createStatement(INSERT);

        statement.setLong(1, ThreadLocalRandom.current().nextLong());

        if (agenda.getData() != null) {
            statement.setDate(2, Date.valueOf(agenda.getData()));
        }

        if (!agenda.getDescricao().isEmpty() || agenda.getDescricao() != null) {
            statement.setString(3, agenda.getDescricao());
        }

        if (!agenda.getHistorico().isEmpty() || agenda.getHistorico() != null) {
            statement.setString(4, agenda.getDescricao());
        }

        if (agenda.getHora() != null) {
            statement.setTime(5, Time.valueOf(agenda.getHora()));
        }

        statement.execute();

        return agenda;
    }

    @Override
    public Set<Agenda> findAll() throws SQLException {
        PreparedStatement statement = createStatement(SELECT_ALL);
        return buildFromResultSet(statement.executeQuery());
    }


    private PreparedStatement createStatement(String sql) throws SQLException {
        return connection.prepareStatement(sql);
    }

    private Set<Agenda> buildFromResultSet(ResultSet resultSet) throws SQLException {
        List<Agenda> agendaList = new ArrayList<>();
        while (resultSet.next()) {
            Long id = resultSet.getLong(1);
            LocalDate date = LocalDate.ofInstant(resultSet.getDate(2).toInstant(), ZoneId.systemDefault());
            String mensagem = resultSet.getString(3);
            String historico = resultSet.getString(4);
            LocalTime time = LocalTime.ofInstant(resultSet.getTime(5).toInstant(), ZoneId.systemDefault());
            Agenda agenda = new Agenda(id, date, mensagem, historico, time);
            agendaList.add(agenda);
        }

        return Set.copyOf(agendaList);
    }

}
