package modelo.dao;

import modelo.entidades.Publicacao;

import java.sql.SQLException;
import java.util.Set;

public interface PublicacaoDao {
	void delete(Publicacao publicacao) throws SQLException;

	Set<Publicacao> findAll();

	Publicacao findOne(Long id);

	Publicacao update(Publicacao publicacao);
}
