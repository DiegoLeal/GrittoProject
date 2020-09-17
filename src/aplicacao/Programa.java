package aplicacao;

import java.sql.Date;
import java.util.List;
//import java.util.Scanner;

import modelo.dao.DaoFactory;
import modelo.dao.UsuarioDao;
import modelo.entidades.CatServico;
import modelo.entidades.Usuario;

public class Programa {

	public static void main(String[] args) {
		
		//Scanner sc = new Scanner(System.in);
		
			
		UsuarioDao usuarioDao = DaoFactory.createUsuarioDao();
		
		System.out.println("=== TEST 1: usuario findById ===");
		Usuario usuario = usuarioDao.findById(1);		
		System.out.println(usuario);
		
		System.out.println("\n=== TEST 2: usuario findByCatServico ===");
		CatServico catServico = new CatServico(0, null);
		List<Usuario> list = usuarioDao.findByCatServico(catServico);
		for (Usuario obj : list) {
			System.out.println(obj);
		}
		
		System.out.println("\n=== TEST 3: usuario findAll ===");		
		list = usuarioDao.findAll();
		for (Usuario obj : list) {
			System.out.println(obj);
		}	
		
		System.out.println("\n=== TEST 4: usuario insert ===");
		Usuario newUsuario = new Usuario(null, "Vitória Silveira", "22.610.093-5", "699.620.573-53", new Date(1966-06-14), "(95) 99245-1738", "3Z0oLGgMiH", "vitorialuziaemilysilveira@skapcom.com", "F", catServico);
		usuarioDao.insert(newUsuario);
		System.out.println("Inserido com Sucesso! Novo id = " + newUsuario.getId());
		
		//System.out.println("\n=== TEST 5: usuario update ===");
		//usuario = usuarioDao.findById(1);
		//usuario.setNome("Silvana Rocha");
		//usuarioDao.update(usuario);
		//System.out.println("Update efetuado com sucesso! ");
		
		//System.out.println("\n=== TEST 6: usuario delete ===");	
	    //System.out.println("Insira o id a ser deletado");
	    //int id = sc.nextInt();
	    //usuarioDao.deleteById(id);
	    //System.out.println("Deletado com sucesso! ");
	    
	    //sc.close();
	}

}
