package modelo.dao;

import java.util.List;

import modelo.entidades.Bairro;

public interface BairroDao {
	void insert(Bairro obj);
	void update(Bairro obj);
	void deleteById(Integer id);
	Bairro findById(Integer id);	
	List<Bairro> findAll();
}
