package modelo.dao;

import java.util.List;

import modelo.entidades.CatServico;

public interface CatServicoDao {

	void insert(CatServico obj);

	void update(CatServico obj);

	void deleteById(Integer id);

	CatServico findById(Integer id);

	List<CatServico> findAll();
}
