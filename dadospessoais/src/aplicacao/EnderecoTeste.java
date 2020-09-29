package aplicacao;

import java.util.List;
import java.util.Scanner;

import org.json.JSONArray;
import org.json.JSONObject;

import controller.EnderecoController;
import modelo.dao.BairroDao;
import modelo.dao.CidadeDao;
import modelo.dao.DaoFactory;
import modelo.dao.EnderecoDao;
import modelo.dao.RuaDao;
import modelo.dao.UniaoFederativaDao;
import modelo.entidades.Bairro;
import modelo.entidades.Cidade;
import modelo.entidades.Endereco;
import modelo.entidades.Rua;
import modelo.entidades.UniaoFederativa;

public class EnderecoTeste {
	static Scanner scan = new Scanner(System.in).useDelimiter("\r");
	static Scanner scanInt = new Scanner(System.in);
	
	static UniaoFederativaDao uf_dao = DaoFactory.createUniaoFederativaDao();
	static UniaoFederativa uf = new UniaoFederativa();
	
	static CidadeDao cidade_dao = DaoFactory.createCidadeDao();
	static Cidade cidade = new Cidade();
	
	static BairroDao bairro_dao = DaoFactory.createBairroDao();
	static Bairro bairro = new Bairro();
	
	static RuaDao rua_dao = DaoFactory.createRuaDao();
	static Rua rua = new Rua();
	
	static EnderecoDao endereco_dao = DaoFactory.createEnderecoDao();
	static Endereco endereco = new Endereco();
	
	static EnderecoController controller = new EnderecoController();
	
	static JSONObject json = new JSONObject();
	
	public static void main(String[] args) {
		
		System.out.println("[Operações...]");
		System.out.println("[1 = Insert..]");
		System.out.println("[2 = Delete..]");
		System.out.println("[3 = FindById]");
		System.out.println("[4 = FindAll.]");
		System.out.println("[5 = Update..]");
		System.out.print("Insira o opção desejada: ");
		int e = scanInt.nextInt();
		
		Escolha(e);
		
	}
	
	private static void Insert(int i) {
		
		switch (i) {
		
			case 1:
				//UniaoFederativa
				try {
					
					System.out.print("Digite a UF: ");
					String newUF = scan.next();
					
					uf.setNome_uf(newUF);
					uf_dao.insert(uf);
					
					System.out.println("Cadastro efetuado");
					break;
					
				} catch (Exception e) {
					e.printStackTrace();
					break;
				}
				
			case 2:
				//Cidade
				try {
					
					System.out.println("Digite a Cidade: ");
					String newCidade = scan.next();
					
					System.out.println("Digite o id da UF: ");
					int cidade_uf = scanInt.nextInt();
					uf.setId(cidade_uf);
					
					cidade.setNome_cidade(newCidade);
					cidade.setUf(uf);
					
					cidade_dao.insert(cidade);
					
					System.out.println("Cadastro efetuado");
					break;
					
				} catch (Exception e) {
					e.printStackTrace();
					
				}
				
			case 3:
				//Bairro
				try {
					
					System.out.print("Digite o Bairro: ");
					String newBairro = scan.next();
					
					bairro.setNome_bairro(newBairro);
					bairro_dao.insert(bairro);
					
					System.out.println("Cadastro efetuado");
					break;
					
				} catch (Exception e) {
					e.printStackTrace();
					break;
				}
				
			case 4:
				//Rua
				try {
					
					System.out.println("Digite a Rua: ");
					String newRua = scan.next();
					
					rua.setNome_rua(newRua);
					rua_dao.insert(rua);
					
					System.out.println("Cadastro efetuado");
					break;
					
				} catch (Exception e) {
					e.printStackTrace();
					break;
				}
				
			case 5:
				//Endereço
				try {
					
					System.out.println("Digite o id da cidade: ");
					int endereco_cidade = scanInt.nextInt();
					cidade.setId(endereco_cidade);
					
					System.out.println("Digite o id da rua: ");
					int endereco_rua = scanInt.nextInt();
					rua.setId(endereco_rua);
					
					System.out.println("Digite o id do bairro: ");
					int endereco_bairro = scanInt.nextInt();
					bairro.setId(endereco_bairro);
					
					System.out.println("Digite o CEP: ");
					String newCep = scan.next();
					
					endereco.setCidade(cidade);
					endereco.setRua(rua);
					endereco.setBairro(bairro);					
					endereco.setCep(newCep);
					
					endereco_dao.insert(endereco);
					
					System.out.println("Cadastro efetuado");
					break;
					
				} catch (Exception e) {
					e.printStackTrace();
					break;
				}
				
			default: break;				
		}
		
	}
	
	private static void FindById(int i) {
		switch (i) {
			case 1:
				//UniaoFederativa
				try {
					
					System.out.println("Digite o id da uf: ");
					int id = scanInt.nextInt();
					
					uf = uf_dao.findById(id);
					System.out.println(uf);
					break;
					
				} catch (Exception e) {
					e.printStackTrace();
					break;
				}
			case 2:
				//Cidade
				try {
					System.out.println("Digite o id da cidade: ");
					int id = scanInt.nextInt();
					
					cidade = cidade_dao.findById(id);
					System.out.println(cidade);
					
					break;
				} catch (Exception e) {
					e.printStackTrace();
					break;
				}
			case 3:
				//Bairro
				try {
					System.out.println("Digite o id do bairro: ");
					int id = scanInt.nextInt();
					
					bairro = bairro_dao.findById(id);
					System.out.println(bairro);
					break;
					
				} catch (Exception e) {
					e.printStackTrace();
					break;
				}
			case 4:
				//Rua
				try {
					System.out.println("Digite o id da rua: ");
					int id = scanInt.nextInt();
					
					rua = rua_dao.findById(id);
					System.out.println(rua);
					break;
					
				} catch (Exception e) {
					e.printStackTrace();
					break;
				}
			case 5:
				//Endereco
				try {
					
					System.out.println("Digite o id do endreço: ");
					int id = scanInt.nextInt();			
					
					EnderecoController controller = new EnderecoController();
					
					json = controller.Show(id);;					
					System.out.println(json);
					break;
					
				} catch (Exception e) {
					e.printStackTrace();
					break;
				}
			default: break;
		}
			
	}
	
	private static void Delete(int i) {
		
		switch (i) {
			case 1:
				//Uniao Federativa
				try {
					System.out.println("Digite o id da uf: ");
					int id = scanInt.nextInt();
					
					uf_dao.deleteById(id);
					
					System.out.println("Deletado com sucesso!");
					break;
					
				} catch (Exception e) {
					e.printStackTrace();
					break;
				}
			case 2:
				//Cidade
				try {
					System.out.println("Digite o id da cidade: ");
					int id = scanInt.nextInt();
					
					cidade_dao.deleteById(id);
					
					System.out.println("Deletado com sucesso!");
					break;
					
				} catch (Exception e) {
					e.printStackTrace();
					break;
				}
			case 3:
				//Bairro
				try {
					System.out.println("Digite o id da bairro: ");
					int id = scanInt.nextInt();
					
					bairro_dao.deleteById(id);
					
					System.out.println("Deletado com sucesso!");
					break;
					
				} catch (Exception e) {
					e.printStackTrace();
					break;
				}
			case 4:
				//Rua
				try {
					System.out.println("Digite o id da rua: ");
					int id = scanInt.nextInt();
					
					rua_dao.deleteById(id);
					
					System.out.println("Deletado com sucesso!");
					break;
					
				} catch (Exception e) {
					e.printStackTrace();
					break;
				}
			case 5:
				//Cidade
				try {
					System.out.println("Digite o id do endereço: ");
					int id = scanInt.nextInt();
					
					endereco_dao.deleteById(id);
					
					System.out.println("Deletado com sucesso!");
					break;
					
				} catch (Exception e) {
					e.printStackTrace();
					break;
				}
	
			default:
				break;
		}
	}
	
	private static void FindAll(int i) {
		
		switch (i) {
			case 1:
				//Uniao Federativa
				try {
					
					List<UniaoFederativa> list = uf_dao.findAll();
					System.out.println(list);
					break;
					
				} catch (Exception e) {
					e.printStackTrace();
					break;
				}
				
			case 2:
				//Cidade
				try {
					
					List<Cidade> list = cidade_dao.findAll();
					System.out.println(list);
					break;
					
				} catch (Exception e) {
					e.printStackTrace();
					break;
				}
			case 3:
				//Bairro
				try {
					
					List<Bairro> list = bairro_dao.findAll();
					System.out.println(list);
					break;
					
				} catch (Exception e) {
					e.printStackTrace();
					break;
				}
			case 4:
				//Rua
				try {
					
					List<Rua> list = rua_dao.findAll();
					System.out.println(list);
					break;
					
				} catch (Exception e) {
					e.printStackTrace();
					break;
				}
			case 5:
				//Endereço
				try {
					JSONArray json_array = new JSONArray();
					json_array = controller.Index();
					System.out.println(json_array);
					break;
					
				} catch (Exception e) {
					e.printStackTrace();
					break;
				}
		}
	}
	
	private static void Update(int i) {
		
		switch (i) {
			case 1:
				//Uniao Federativa
				try {
					
					System.out.println("Digite o id da uf: ");
					int id = scanInt.nextInt();
					
					System.out.println("Digite a uf atualizada: ");
					String newUf = scan.next();
					
					uf.setId(id);
					uf.setNome_uf(newUf);
					uf_dao.update(uf);
					
					System.out.println("Uf atualizada com sucesso!");
					break;
					
				} catch (Exception e) {
					e.printStackTrace();
					break;
				}
			case 2:
				try {
					
					System.out.println("Digite o id da cidade: ");
					int id = scanInt.nextInt();
					
					cidade = cidade_dao.findById(id);
					
					System.out.println("Qual informação quer atualizar?");
					System.out.println("[1 UF.......]");
					System.out.println("[2 Nome.....]");
					System.out.println("[3 Nome e Uf]");
					int e = scanInt.nextInt();
					
						if(e == 1) {
							
							System.out.println("Digita o id da nova uf: ");
							int id_uf = scanInt.nextInt();
							uf.setId(id_uf);
							cidade.setUf(uf);
							System.out.println("Cidade atualizada com sucesso!");
							
						} else if(e == 2) {
							
							System.out.println("Digite o nome da nova cidade: ");
							String newCidade = scan.next();
							cidade.setNome_cidade(newCidade);
							System.out.println("Cidade atualizada com sucesso!");
							
						} else if(e ==3) {
							
							System.out.println("Digita o id da nova uf: ");
							int id_uf = scanInt.nextInt();
							System.out.println("Digite o nome da nova cidade: ");
							String newCidade = scan.next();
							uf.setId(id_uf);
							cidade.setUf(uf);
							cidade.setNome_cidade(newCidade);
							System.out.println("Cidade atualizada com sucesso!");
							
						} else {
							
							System.out.println("Nenhuma selecionada\n\n");
							System.out.println("Aguarde para tentar novamente...");
							Thread.sleep(1000);
							Update(2);
						}	
						
						cidade_dao.update(cidade);
						
					break;
					
				} catch (Exception e) {
					e.printStackTrace();
					break;
				}
				
			case 3:
				//Bairro
				try {
					
					System.out.println("Digite o id do bairro: ");
					int id = scanInt.nextInt();
					
					System.out.println("Digite o novo nome: ");
					String newBairro = scan.next();
					
					bairro.setId(id);
					bairro.setNome_bairro(newBairro);
					bairro_dao.update(bairro);
					
					System.out.println("Bairro atualizado com sucesso!");
					break;
					
				} catch (Exception e) {
					e.printStackTrace();
					break;
				}
			case 4:
				//Rua
				try {
					
					System.out.println("Digite o id da rua: ");
					int id = scanInt.nextInt();
					
					System.out.println("Digite o novo nome: ");
					String newRua = scan.next();
					
					rua.setId(id);
					rua.setNome_rua(newRua);
					rua_dao.update(rua);
					
					System.out.println("Rua atualizada com sucesso!");
					break;
					
				} catch (Exception e) {
					e.printStackTrace();
					break;
				}
			case 5:
				//Endereço
				try {
					System.out.println("Digite o id do endereco: ");
					int id = scanInt.nextInt();
					
					endereco = endereco_dao.findById(id);
					
					System.out.println("Qual informação quer atualizar?");
					System.out.println("[1 Bairro]");
					System.out.println("[2 Rua...]");
					System.out.println("[3 Cidade]");
					System.out.println("[4 CEP...]");
					System.out.println("[5 Todas.]");
					int e = scanInt.nextInt();
					
						if (e == 1) {
							
							System.out.println("Digite o id do novo bairro: ");
							int id_bairro = scanInt.nextInt();
							bairro.setId(id_bairro);
							endereco.setBairro(bairro);
							System.out.println("Endereco atualizado com sucesso!");
							
						} else if (e == 2) {
							
							System.out.println("Digite o id da nova rua: ");
							int id_rua = scanInt.nextInt();
							rua.setId(id_rua);
							endereco.setRua(rua);
							System.out.println("Endereco atualizado com sucesso!");
							
						} else if (e == 3) {
							
							System.out.println("Digite o id da nova cidade: ");
							int id_cid = scanInt.nextInt();
							cidade.setId(id_cid);
							endereco.setCidade(cidade);
							System.out.println("Endereco atualizado com sucesso!");
							
						} else if (e == 4) {
							
							System.out.println("Digite o novo cep: ");
							String newCep = scan.next();
							endereco.setCep(newCep);
							System.out.println("Endereco atualizado com sucesso!");
							
						} else if (e == 5) {
							
							System.out.println("Digite o id do novo bairro: ");
							int id_bairro = scanInt.nextInt();
							bairro.setId(id_bairro);
							endereco.setBairro(bairro);
							
							System.out.println("Digite o id da nova rua: ");
							int id_rua = scanInt.nextInt();
							rua.setId(id_rua);
							endereco.setRua(rua);
							
							System.out.println("Digite o id da nova cidade: ");
							int id_cid = scanInt.nextInt();
							cidade.setId(id_cid);
							endereco.setCidade(cidade);
							
							System.out.println("Digite o novo cep: ");
							String newCep = scan.next();
							endereco.setCep(newCep);
							System.out.println("Endereco atualizado com sucesso!");
							
						} else {
							
							System.out.println("Nenhuma selecionada\n\n");
							System.out.println("Aguarde para tentar novamente...");
							Thread.sleep(1000);
							Update(5);
							
						}
						
						endereco_dao.update(endereco);
						break;
						
				} catch (Exception e) {
					e.printStackTrace();
					break;
				}
				
			default: break;
		}
	}
	
	private static void Escolha(int e) {
		
		switch (e) {
		
			case 1:
				System.out.println("[Inserir...]");
				System.out.println("[1 UF......]");
				System.out.println("[2 Cidade..]");
				System.out.println("[3 Bairro..]");
				System.out.println("[4 Rua.....]");
				System.out.println("[5 Endereço]");
				int i = scanInt.nextInt();
				
					if (i == 1) {
						Insert(1);
						break;
					} else if (i == 2) {
						Insert(2);
						break;
					} else if (i == 3) {
						Insert(3);
						break;
					}else if (i == 4) {
						Insert(4);
						break;
					} else if (i == 5) {
						Insert(5);
						break;
					} else {
						System.out.println("Nenhuma opção selecionada!");
					}
					break;
			case 2:
				System.out.println("[Deletar...]");
				System.out.println("[1 UF......]");
				System.out.println("[2 Cidade..]");
				System.out.println("[3 Bairro..]");
				System.out.println("[4 Rua.....]");
				System.out.println("[5 Endereço]");
				int i1 = scanInt.nextInt();
				
					if (i1 == 1) {
						Delete(1);
						break;
					} else if (i1 == 2) {
						Delete(2);
						break;
					} else if (i1 == 3) {
						Delete(3);
						break;
					}else if (i1 == 4) {
						Delete(4);
						break;
					} else if (i1 == 5) {
						Delete(5);
						break;
					} else {
						System.out.println("Nenhuma opção selecionada!");
					}
					break;
			case 3: 
				System.out.println("[FindById...]");
				System.out.println("[1 UF.......]");
				System.out.println("[2 Cidade...]");
				System.out.println("[3 Bairro...]");
				System.out.println("[4 Rua......]");
				System.out.println("[5 Endereço.]");
				int i2 = scanInt.nextInt();
				
					if (i2 == 1) {
						FindById(1);
						break;
					} else if (i2 == 2) {
						FindById(2);
						break;
					} else if (i2 == 3) {
						FindById(3);
						break;
					}else if (i2 == 4) {
						FindById(4);
						break;
					} else if (i2 == 5) {
						FindById(5);
						break;
					} else {
						System.out.println("Nenhuma opção selecionada!");
					}
					break;
			case 4:
				System.out.println("[FindAll...]");
				System.out.println("[1 UF......]");
				System.out.println("[2 Cidade..]");
				System.out.println("[3 Bairro..]");
				System.out.println("[4 Rua.....]");
				System.out.println("[5 Endereço]");
				int i3 = scanInt.nextInt();
				
					if (i3 == 1) {
						FindAll(1);
						break;
					} else if (i3 == 2) {
						FindAll(2);
						break;
					} else if (i3 == 3) {
						FindAll(3);
						break;
					}else if (i3 == 4) {
						FindAll(4);
						break;
					} else if (i3 == 5) {
						FindAll(5);
						break;
					} else {
						System.out.println("Nenhuma opção selecionada!");
					}
					break;
			case 5:
				System.out.println("[Update....]");
				System.out.println("[1 UF......]");
				System.out.println("[2 Cidade..]");
				System.out.println("[3 Bairro..]");
				System.out.println("[4 Rua.....]");
				System.out.println("[5 Endereço]");
				int i4 = scanInt.nextInt();
				
					if (i4 == 1) {
						Update(1);
						break;
					} else if (i4 == 2) {
						Update(2);
						break;
					} else if (i4 == 3) {
						Update(3);
						break;
					}else if (i4 == 4) {
						Update(4);
						break;
					} else if (i4 == 5) {
						Update(5);
						break;
					} else {
						System.out.println("Nenhuma opção selecionada!");
					}
					break;
			default: break;
		}	
	}
}

