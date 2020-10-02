package modelo.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import db.DB;
import db.DbException;
import db.DbIntegrityException;
import modelo.dao.ChatDao;
import modelo.entidades.Chat;

public class ChatDaoJDBC implements ChatDao {

	private Connection conn;

	public ChatDaoJDBC(Connection conn) {
		this.conn = conn;
	}

	@Override
	public void insert(Chat obj) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement(
					"INSERT INTO chat " + "(Remetente, Destinatario, Mensagem) " + "VALUES " + "(?, ?, ?)",
					Statement.RETURN_GENERATED_KEYS);

			st.setInt(1, obj.getRemetente().getId());
			st.setInt(2, obj.getDestinatario().getId());
			st.setString(3, obj.getMensagem());

			int rowsAffected = st.executeUpdate();

			if (rowsAffected > 0) {
				ResultSet rs = st.getGeneratedKeys();
				if (rs.next()) {
					int id = rs.getInt(1);
					obj.setId(id);
				}
			} else {
				throw new DbException("Erro inesperado! Nenhuma linha afetada!");
			}
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(st);
		}

	}

	@Override
	public void update(Chat obj) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement(
					"UPDATE chat " + "SET Remetente = ?, Destinatario = ?, Mensagem = ? " + "WHERE Id = ?");

			st.setInt(1, obj.getRemetente().getId());
			st.setInt(2, obj.getDestinatario().getId());
			st.setString(3, obj.getMensagem());

			st.executeUpdate();
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(st);
		}

	}

	@Override
	public void deleteById(Integer idChat) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement("DELETE FROM chat WHERE Id = ?");

			st.setInt(1, idChat);

			st.executeUpdate();
		} catch (SQLException e) {
			throw new DbIntegrityException(e.getMessage());
		} finally {
			DB.closeStatement(st);
		}

	}

	@Override
	public Chat findById(Integer id) {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement("SELECT * FROM chat WHERE Id = ?");
			st.setInt(1, id);
			rs = st.executeQuery();
			if (rs.next()) {
				Chat obj = new Chat();
				obj.setId(rs.getInt("Id"));
				return obj;
			}
			return null;
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
	}

	@Override
	public List<Chat> findAll() {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement("SELECT * FROM chat ORDER BY Mensagem");
			rs = st.executeQuery();

			List<Chat> list = new ArrayList<>();

			while (rs.next()) {
				Chat obj = new Chat();
				obj.setId(rs.getInt("Id"));
				obj.setMensagem(rs.getString("Mensagem"));
				list.add(obj);
			}
			return list;
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
	}

}