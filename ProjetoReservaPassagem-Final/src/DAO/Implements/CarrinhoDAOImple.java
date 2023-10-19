package DAO.Implements;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

import com.mysql.jdbc.PreparedStatement;

import DAO.ConexaoBanco.ConexaoDAO;
import DAO.Interfaces.ICarrinhoDAO;
import DAO.Modelos.Carrinho;

import DAO.Modelos.Usuario;

public class CarrinhoDAOImple implements ICarrinhoDAO {

	@Override
	public void removerDoCarrinho(Carrinho C, int idUsuario) throws SQLException {
	    ConexaoDAO conexao = new ConexaoDAO();
	    conexao.abrirConexao();

	    Scanner entrada = new Scanner(System.in);

	    try {
	        System.out.print("Informe o ID do produto a ser removido do carrinho: ");
	        int idProduto = entrada.nextInt();

	        String sql = "DELETE FROM carrinho WHERE idUsuario = ? AND idProduto = ?";
	        PreparedStatement stmt = (PreparedStatement) conexao.prepareStatement(sql);
	        stmt.setInt(1, idUsuario); // Estou usando Id do usuario obtido no login 
	        stmt.setInt(2, idProduto);

	        int linhasAfetadas = stmt.executeUpdate(); 
	        
	        /*
	         * Se mais que uma linha for afetada eu realizo o update no carrinho 
	         */
	        if (linhasAfetadas > 0) {
	            System.out.println("Produto removido com sucesso do carrinho.");
	        } else {
	            System.out.println("O produto não foi encontrado no carrinho do usuário.");
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	        System.out.println("Erro ao remover do Carrinho " + e.getMessage());
	    } finally {
	        conexao.fecharConexao();
	    }
	}


	public void adicionarNoCarinho(Carrinho C, int idUsuario, Usuario Usuario) {
	    ConexaoDAO conexao = new ConexaoDAO();
	    conexao.abrirConexao();

	    Scanner entrada = new Scanner(System.in);

	    try {
	        System.out.print("a - Adicionar Produto no Carrinho: ");
	        String resposta = entrada.nextLine();

	        if (resposta.equals("a")) {
	            String sql = "INSERT INTO carrinho (idUsuario, idProduto, QTD_Reservada) VALUES (?, ?, ?)";
	            System.out.print("Informe o ID do Produto: ");
	            int idProduto = entrada.nextInt();
	            System.out.print("Informe a Quantidade que deseja comprar: ");
	            int QTD_Comprada = entrada.nextInt();

	            if (verificarEstoque(C)) {
	                PreparedStatement stmt = (PreparedStatement) conexao.prepareStatement(sql);
	                stmt.setInt(1, idUsuario); // Usar o ID do usuário obtido no login
	                stmt.setInt(2, idProduto);
	                stmt.setInt(3, QTD_Comprada);

	                stmt.executeUpdate();
	                System.out.println("Produto adicionado no carrinho com sucesso");
	            } else {
	                System.out.println("Falha ao adicionar o produto no carrinho por que nao tem quantiadade");
	            }
	        } else {
	            System.out.println("Nao foi adicionado no carrinho");
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	        System.out.println("Erro ao adicionar no Carrinho: " + e.getMessage());
	    }
	}


	public void mostrarTodosProdutoDoCarrinho(Carrinho c, int idUsuario) {
		ConexaoDAO conexao = new ConexaoDAO();
		conexao.abrirConexao();

		try {

			String sql = "SELECT produto.Nome, carrinho.QTD_Reservada " 
			+ "FROM carrinho "
			+ "JOIN produto ON carrinho.idProduto = produto.idProduto " 
			+ "WHERE carrinho.idUsuario = ?";

			PreparedStatement stmt = (PreparedStatement) conexao.prepareStatement(sql);
			stmt.setInt(1, idUsuario);

			ResultSet resultSet = stmt.executeQuery();

			while (resultSet.next()) {
				String nomeProduto = resultSet.getString("Nome");
				int quantidadeReservada = resultSet.getInt("QTD_Reservada");

				System.out.println("Produto: " + nomeProduto + " - Quantidade Reservada: " + quantidadeReservada);
			}

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Erro ao listar produtos do carrinho: " + e.getMessage());
		} finally {
			conexao.fecharConexao();
		}
	}
	
	public boolean verificarEstoque(Carrinho C) {
		ConexaoDAO conexao = new ConexaoDAO();
		conexao.abrirConexao();

		Scanner entrada = new Scanner(System.in);

		System.out.println("Informe o ID do Produto para verificar se tem no estoque: ");
		int idProduto = entrada.nextInt();
		System.out.println("Informe a Quantidade desejada: ");
		int quantidadeDesejada = entrada.nextInt();

		
		String sql = "SELECT QTD_Em_Estoque FROM produto WHERE idProduto = ?";

		try {
			PreparedStatement stmt = (PreparedStatement) conexao.prepareStatement(sql);
			stmt.setInt(1, idProduto);

			ResultSet resultSet = stmt.executeQuery();

			if (resultSet.next()) {
				int quantidadeEmEstoque = resultSet.getInt("QTD_Em_Estoque");

				if (quantidadeEmEstoque >= quantidadeDesejada) {
					System.out.println("Produto disponível em estoque.");
					return true; // Retorna true se houver estoque suficiente
				} else {
					System.out.println("Quantidade insuficiente em estoque.");
					return false; // Retorna false se a quantidade em estoque for insuficiente
				}
			} else {
				System.out.println("Produto não encontrado.");
				return false; // Retorna false se o produto não for encontrado
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Erro ao verificar o estoque: " + e.getMessage());
			return false;
		} finally {
			conexao.fecharConexao();
		}
	}

	

	
}
