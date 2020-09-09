package modelo.dao.impl;

import modelo.dao.PublicacaoDao;
import modelo.entidades.Publicacao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Set;

public class PublicacaoDaoImpl implements PublicacaoDao {

    private final Connection connection;

    private static String DELETE = "DELETE FROM publicacao WHERE id = ?";

    public PublicacaoDaoImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void delete(Publicacao publicacao) throws SQLException {
        if(publicacao.getId() == null) {
            throw new RuntimeException("Invalid deletion without ID reference");
        }

        PreparedStatement statement = createStatement(DELETE);
        statement.setLong(1, publicacao.getId());
        statement.execute();
    }

    @Override
    public Set<Publicacao> findAll() {

        return null;
    }

    @Override
    public Publicacao findOne(Long id) {
        return null;
    }

    @Override
    public Publicacao update(Publicacao publicacao) {
        return null;
    }

    private PreparedStatement createStatement(String sql) throws SQLException {
        return connection.prepareStatement(sql);
    }

}
