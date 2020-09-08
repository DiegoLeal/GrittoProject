package modelo.dao.impl;

import modelo.dao.AgendaDao;
import modelo.entidades.Agenda;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

public class AgendaDaoImpl implements AgendaDao {

    private final Connection connection;

    public AgendaDaoImpl(Connection connection) {
        this.connection = connection;
    }

    private static String INSERT = "INSERT INTO " +
            "Agenda(`idAgenda`, `Data`, `Mensagem`, `EnderecoId`, `Histórico`, `Hora`) " +
            "VALUES(?, ?, ?, ?, ?, ?) ";

    private static String UPDATE = "UPDATE Agenda " +
            "SET `idAgenda` = ?, `Data` = ?, `Mensagem` = ?, `EnderecoId` = ?, `Histórico` = ?, `Hora` = ? " +
            "WHERE `idAgenda` = ? ";

    private static String DELETE = "DELETE FROM Agenda WHERE `idAgenda` = ?";

    private static String SELECT_ONE = "SELECT " +
            "`idAgenda`, `Data`, `Mensagem`, `EnderecoId`, `Histórico`, `Hora` " +
            "FROM Agenda WHERE `idAgenda` = ?";

    private static String SELECT_ALL = "SELECT `idAgenda`, `Data`, `Mensagem`, `EnderecoId`, `Histórico`, `Hora` FROM Agenda";

    @Override
    public void delete(Agenda agenda) throws SQLException {
        if(agenda.getId() == null) {
            throw new RuntimeException("Invalid deletion without ID reference");
        }
        PreparedStatement statement = createStatement(DELETE);
        statement.setLong(1, agenda.getId());
        statement.execute();
    }

    @Override
    public Agenda findById(Agenda agenda) throws SQLException {
        if(agenda.getId() == null) {
            throw new RuntimeException("Invalid deletion without ID reference");
        }

        PreparedStatement statement = createStatement(SELECT_ONE);
        statement.setLong(1, agenda.getId());
        ResultSet queryResult = statement.executeQuery();
        Set<Agenda> agendaSet = buildFromResultSet(queryResult);
        if(agendaSet.isEmpty()) return null;

        return agendaSet.stream().findAny().get();
    }

    @Override
    public Agenda update(Agenda agenda) {

        return null;
    }

    @Override
    public Agenda save(Agenda agenda) {
        return null;
    }

    @Override
    public Set<Agenda> findAll() {
        return null;
    }


    private PreparedStatement createStatement(String sql) throws SQLException {
        return connection.prepareStatement(sql);
    }

    private Set<Agenda> buildFromResultSet(ResultSet resultSet) throws SQLException {
        List<Agenda> agendaList = new ArrayList<>();
        while(resultSet.next()) {
            Long id = resultSet.getLong(1);
            LocalDate date = LocalDate.ofInstant(resultSet.getDate(2).toInstant(), ZoneId.systemDefault());
            String mensagem = resultSet.getString(3);
            Integer enderecoId = resultSet.getInt(4);
            String historico = resultSet.getString(5);
            LocalDateTime time = LocalDateTime.ofInstant(resultSet.getTime(6).toInstant(), ZoneId.systemDefault());
            Agenda agenda = new Agenda(id, date, mensagem, enderecoId, historico, time);
            agendaList.add(agenda);
        }

        return Set.copyOf(agendaList);
    }
}
