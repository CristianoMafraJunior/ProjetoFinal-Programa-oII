package DAO.View;
import java.sql.SQLException;
import java.util.Scanner;
import DAO.Implements.AdministradorDAOImple;
import DAO.Implements.CarrinhoDAOImple;
import DAO.Implements.ProdutoDAOImple;
import DAO.Implements.UsuarioDAOimple;
import DAO.Modelos.Administrador;
import DAO.Modelos.Carrinho;
import DAO.Modelos.Produto;
import DAO.Modelos.Usuario;


public class Main {

	public static void main(String[] args) throws SQLException {
		@SuppressWarnings("resource") // desativa aviso de recursos ativos
		Scanner entrada = new Scanner(System.in);
		AdministradorDAOImple adminDAO = new AdministradorDAOImple();
		UsuarioDAOimple usuarioDAO = new UsuarioDAOimple();
		CarrinhoDAOImple CarrinhoDAO = new CarrinhoDAOImple();
		ProdutoDAOImple produtoDAO = new ProdutoDAOImple();
		
		Produto produto = new Produto();
		Administrador adm = new Administrador();
		Usuario usuario = new Usuario();
		Carrinho carrinho = new Carrinho();
		
		
		boolean AdminLogado = false;
		boolean UsuarioLogado = false;

		do {
			System.out.println("-----Menu-----");
			System.out.println("1 - Efetuar login Administrativo");
			System.out.println("2 - Efetuar login de Usuario");
			System.out.println("3 - Sair");

			int opcao = Integer.parseInt(entrada.nextLine());

			switch (opcao) {
			case 1:
				if (adminDAO.efetuarlogin(adm)) {
					AdminLogado = true;
					System.out.println("Login Efetuado com sucesso");
				} else {
					System.out.println("Login falhou ");
				}
				break;
			case 2:
				if (usuarioDAO.efetuarlogin(usuario)) {
					UsuarioLogado = true;
					System.out.println("Login efetuado ");
				} else {
					System.out.println("Login falhou ");
				}
				break;
			case 3:
				System.out.println("Saindo do sistema.");
				System.exit(0);
				break;
			default:
				System.out.println("Opção inválida.");
			}
			while (AdminLogado) {
			    System.out.println("-----Menu de Administrativo-----");
			    System.out.println("1 - Cadastrar Usuario");
			    System.out.println("2 - Cadastrar Produto");
			    System.out.println("3 - Remover Produto");
			    System.out.println("4 - Buscar Produto");
			    System.out.println("5 - Atualizar Produto");
			    System.out.println("6 - Sair do Menu de Administrativo");

			    char adminOpcao = entrada.nextLine().charAt(0);
			    switch (adminOpcao) {
			    
			        case '1':
			            adminDAO.cadastrarUsuario(adm);
			            break;
			        case '2':
			            produtoDAO.cadastrarProduto(produto);
			            break;
			        case '3':
			            produtoDAO.removerProduto(produto);
			            break;
			        case '4':
			            produtoDAO.buscarProduto(produto);
			            break;
			        case '5':
			           produtoDAO.atualizarProduto(produto);
			            break;
			        case '6':
						System.out.println("Saindo do sistema.");
						System.exit(0);
						break;
					default:
						System.out.println("Opção inválida.");
			    }
			}

			while (UsuarioLogado) {
				System.out.println("-----Menu de Cliente-----");
				System.out.println("a - Buscar Produto");
				System.out.println("b - Adicionar Produto(Carrinho)");
				System.out.println("c - Remover Produto(Carrinho)");
				System.out.println("d - Confirmar Compra");
				System.out.println("e - Mostrar Todo o Carrinho");
				System.out.println("f - Sair do Menu de Usuario");

				char userOpcao = entrada.nextLine().charAt(0);

				System.out.println();

				switch (userOpcao) {
				case 'a':
					usuarioDAO.buscarProduto(usuario);
					break;
				case 'b':
					CarrinhoDAO.adicionarNoCarinho(carrinho, usuario.getIdUsuario(), usuario);
					break;
				case 'c':
					CarrinhoDAO.removerDoCarrinho(carrinho,usuario.getIdUsuario());
					break;

				case 'd':
					System.out.println("Compra confirmada com Suceso");
					break;

				case 'e':
					CarrinhoDAO.mostrarTodosProdutoDoCarrinho(carrinho,usuario.getIdUsuario());

					break;
				case 'f':
					System.out.println("Saindo do Menu de Usuario.");
					UsuarioLogado = false;
				default:
					System.out.println("Opção inválida.");
				}
			}
		} while (true);
	}
}
