package aplicacao;

import java.sql.Date;
import java.util.List;
import java.util.Scanner;

import modelo.dao.DaoFactory;
import modelo.dao.UsuarioDao;
import modelo.entidades.Profissao;
import modelo.entidades.Usuario;

public class Programa {

	public static void main(String[] args) {
		
		Scanner sc = new Scanner(System.in);
		
			
		UsuarioDao usuarioDao = DaoFactory.createUsuarioDao();
		
		System.out.println("=== TEST 1: usuario findById ===");
		Usuario usuario = usuarioDao.findById(3);		
		System.out.println(usuario);
		
		System.out.println("\n=== TEST 2: usuario findByProfissao ===");
		Profissao profissao = new Profissao(0, null);
		List<Usuario> list = usuarioDao.findByProfissao(profissao);
		for (Usuario obj : list) {
			System.out.println(obj);
		}
		
		System.out.println("\n=== TEST 3: usuario findAll ===");		
		list = usuarioDao.findAll();
		for (Usuario obj : list) {
			System.out.println(obj);
		}	
		
		System.out.println("\n=== TEST 4: usuario insert ===");
		Usuario newUsuario = new Usuario(null, "Levi Moura", "22.610.093-5", "699.620.573-53", new Date(1966-06-14), "(95) 99245-1738", "M" , profissao);
		usuarioDao.insert(newUsuario);
		System.out.println("Inserido com Sucesso! Novo id = " + newUsuario.getId());
		
		System.out.println("\n=== TEST 5: usuario update ===");
		usuario = usuarioDao.findById(1);
		usuario.setNome("Silvana Rocha");
		usuarioDao.update(usuario);
		System.out.println("Update efetuado com sucesso! ");
		
		System.out.println("\n=== TEST 6: usuario delete ===");	
	    System.out.println("Enter id for delete test");
	    int id = sc.nextInt();
	    usuarioDao.deleteById(id);
	    System.out.println("Deletado com sucesso! ");
	    
	    sc.close();
	}

}
