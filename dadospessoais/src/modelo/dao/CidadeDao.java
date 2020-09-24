package modelo.dao;

import java.util.List;

import modelo.entidades.Cidade;
import modelo.entidades.UniaoFederativa;

public interface CidadeDao {
	void insert(Cidade obj);
	void update(Cidade obj);
	void deleteById(Integer id);
	Cidade findById(Integer id);	
	List<Cidade> findAll();
	List<Cidade> findByUf(UniaoFederativa uf);
}
