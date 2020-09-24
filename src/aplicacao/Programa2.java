package aplicacao;

import java.util.List;

import java.util.Scanner;

import modelo.dao.DaoFactory;
import modelo.dao.CatServicoDao;
import modelo.entidades.CatServico;

public class Programa2 {

	public static void main(String[] args) {
		
         Scanner sc = new Scanner(System.in);
		
		CatServicoDao catServicoDao = DaoFactory.createCatServicoDao();

		System.out.println("=== TEST 1: findById =======");
		CatServico prof = catServicoDao.findById(7);
		System.out.println(prof);
		
		System.out.println("\n=== TEST 2: findAll =======");
		List<CatServico> list = catServicoDao.findAll();
		for (CatServico d : list) {
			System.out.println(d);
		}

		//System.out.println("\n=== TEST 3: insert =======");
		//Profissao newProfissao = new Profissao(null, "Eletricista");
		//profissaoDao.insert(newProfissao);
		//System.out.println("Inserido Nova profissao: " + newProfissao.getId());
		//System.out.println("Inserido com Sucesso");

		//System.out.println("\n=== TEST 4: update =======");
		//Profissao prof2 = profissaoDao.findById(4);
		//prof2.setNome("Personal trainer");
		//profissaoDao.update(prof2);
		//System.out.println("Update efetuado com sucesso! ");
		
		System.out.println("\n=== TEST 5: delete =======");
		System.out.print("Insira o id a ser deletado: ");
		int id = sc.nextInt();
		catServicoDao.deleteById(id);
		System.out.println("Deletado com sucesso! ");

		sc.close();

	}

}
