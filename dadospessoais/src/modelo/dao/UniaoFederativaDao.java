package modelo.dao;

import java.util.List;

import modelo.entidades.UniaoFederativa;

public interface UniaoFederativaDao {
	void insert(UniaoFederativa obj);
	void update(UniaoFederativa obj);
	void deleteById(Integer id);
	UniaoFederativa findById(Integer id);	
	List<UniaoFederativa> findAll();
}
