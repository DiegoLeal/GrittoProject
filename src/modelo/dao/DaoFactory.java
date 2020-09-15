package modelo.dao;

import db.DB;
import modelo.dao.impl.ChatDaoJDBC;
import modelo.dao.impl.ProfissaoDaoJDBC;
import modelo.dao.impl.UsuarioDaoJDBC;

public class DaoFactory {
	
	public static UsuarioDao createUsuarioDao() {
		return new UsuarioDaoJDBC(DB.getConnection());
	}

	public static ProfissaoDao createProfissaoDao() {		
		return new ProfissaoDaoJDBC(DB.getConnection());
	}
	
	public static ChatDao createChatDao() {		
		return new ChatDaoJDBC(DB.getConnection());
	}
}
