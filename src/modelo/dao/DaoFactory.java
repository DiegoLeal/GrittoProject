package modelo.dao;

import db.DB;

import modelo.dao.impl.AgendaDaoImpl;
import modelo.dao.impl.ProfissaoDaoJDBC;
import modelo.dao.impl.PublicacaoDaoImpl;
import modelo.dao.impl.UsuarioDaoJDBC;

public class DaoFactory {
	
	public static UsuarioDao createUsuarioDao() {
		return new UsuarioDaoJDBC(DB.getConnection());
	}

	public static ProfissaoDao createProfissaoDao() {		
		return new ProfissaoDaoJDBC(DB.getConnection());
	}

	public static AgendaDao createAgendaDao() {
		return new AgendaDaoImpl(DB.getConnection());
	}

	public static PublicacaoDao createPublicacaoDao() {
		return new PublicacaoDaoImpl(DB.getConnection());
	}
}
