package modelo.dao;

import java.util.List;

import modelo.entidades.Chat;

public interface ChatDao {
	
		void insert(Chat obj);
		void update(Chat obj);
		void deleteById(Integer id);
		Chat findById(Integer id);	
		List<Chat> findAll();
	}


