package modelo.dao;

import java.util.List;

import modelo.entidades.Uniao_federativa;

public interface Uniao_federativaDao {
	void insert(Uniao_federativa obj);
	void update(Uniao_federativa obj);
	void deleteById(Integer id);
	Uniao_federativa findById(Integer id);	
	List<Uniao_federativa> findAll();
}
