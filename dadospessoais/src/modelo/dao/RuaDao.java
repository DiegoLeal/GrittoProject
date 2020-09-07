package modelo.dao;

import java.util.List;

import modelo.entidades.Rua;

public interface RuaDao {
	void insert(Rua obj);
	void update(Rua obj);
	void deleteById(Integer id);
	Rua findById(Integer id);	
	List<Rua> findAll();
}
