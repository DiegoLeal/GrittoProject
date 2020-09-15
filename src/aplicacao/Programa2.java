package aplicacao;

import java.util.List;
import java.util.Scanner;

import modelo.dao.DaoFactory;
import modelo.dao.ProfissaoDao;
import modelo.entidades.Profissao;

public class Programa2 {

	public static void main(String[] args) {
         Scanner sc = new Scanner(System.in);
		
		ProfissaoDao profissaoDao = DaoFactory.createProfissaoDao();

		System.out.println("=== TEST 1: findById =======");
		Profissao dep = profissaoDao.findById(1);
		System.out.println(dep);
		
		System.out.println("\n=== TEST 2: findAll =======");
		List<Profissao> list = profissaoDao.findAll();
		for (Profissao d : list) {
			System.out.println(d);
		}

		System.out.println("\n=== TEST 3: insert =======");
		Profissao newProfissao = new Profissao(null, "Cardíaco");
		profissaoDao.insert(newProfissao);
		System.out.println("Inserted! New id: " + newProfissao.getId());

		//System.out.println("\n=== TEST 4: update =======");
		//Profissao prof2 = profissaoDao.findById(5);
		//prof2.setNome("Personal trainer");
		//profissaoDao.update(prof2);
		//System.out.println("Update efetuado com sucesso! ");
		
		//System.out.println("\n=== TEST 5: delete =======");
		//System.out.print("Enter id for delete test: ");
		//int id = sc.nextInt();
		//profissaoDao.deleteById(id);
		//System.out.println("Deletado com sucesso! ");

		sc.close();

	}

}
