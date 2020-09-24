package modelo.dao;

import java.util.List;

import modelo.entidades.Profissao;

public interface ProfissaoDao {
	
	void insert(Profissao obj);
	void update(Profissao obj);
	void deleteById(Integer id);
	Profissao findById(Integer id);	
	List<Profissao> findAll();
}
