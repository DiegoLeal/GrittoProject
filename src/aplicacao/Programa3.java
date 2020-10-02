package aplicacao;

import modelo.dao.CatServicoDao;
import modelo.dao.ChatDao;
import modelo.dao.DaoFactory;
import modelo.dao.UsuarioDao;
import modelo.entidades.CatServico;
import modelo.entidades.Chat;

import java.util.List;
import java.util.Scanner;

public class Programa3 {

	public static void main(String[] args) {

		Scanner sc = new Scanner(System.in);

		ChatDao chatDao = DaoFactory.createChatDao();
		CatServicoDao profissaoDao = DaoFactory.createCatServicoDao();
		UsuarioDao usuarioDao = DaoFactory.createUsuarioDao();

		System.out.println("=== TEST 1: findById =======");
		Chat chat = chatDao.findById(1);
		System.out.println(chat);

		System.out.println("\n=== TEST 2: findAll =======");
		List<Chat> list = chatDao.findAll();
		for (Chat d : list) {
			System.out.println(d);
		}

		System.out.println("\n=== TEST 3: Profissão =======");
		CatServico newProfissao = new CatServico(null, "Geral");
		profissaoDao.insert(newProfissao);
		System.out.println("Inserido com Sucesso! Novo ID = " + newProfissao.getId());

		// System.out.println("\n=== TEST 4: Usuario ===");
		// Usuario newUsuario = new Usuario(null, "Lucas", "22.111.111-2",
		// "522.457.987-57", new Date(1952-10-22), "(34) 99226-9815", "M" ,
		// newProfissao);
		// usuarioDao.insert(newUsuario);
		// System.out.println("Inserido com Sucesso! Novo ID = " + newUsuario.getId());

		// System.out.println("\n=== TEST 5: Chat =======");
		// Chat newChat = new Chat(null, newUsuario, newUsuario, "Eai td bem ?");
		// chatDao.insert(newChat);
		// System.out.println("Inserido com Sucesso! Novo ID = " + newChat.getId());

		// System.out.println("\n=== TEST 5: usuario update ===");
		// usuario = usuarioDao.findById(1);
		// usuario.setNome("Silvana Rocha");
		// usuarioDao.update(usuario);
		// System.out.println("Update efetuado com sucesso! ");

		System.out.println("\n=== TEST 6: usuario delete ===");
		System.out.println("Enter id for delete test");
		int idChat = sc.nextInt();
		chatDao.deleteById(idChat);
		System.out.println("Deletado com sucesso! ");

		sc.close();
	}

}
