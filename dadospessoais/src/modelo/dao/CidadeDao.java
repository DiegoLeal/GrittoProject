package modelo.dao;

import java.util.List;

import modelo.entidades.Cidade;
import modelo.entidades.Uniao_federativa;

public interface CidadeDao {
	void insert(Cidade obj);
	void update(Cidade obj);
	void deleteById(Integer id);
	Cidade findById(Integer id);	
	List<Cidade> findAll();
	List<Cidade> findByUf(Uniao_federativa uf);
}
