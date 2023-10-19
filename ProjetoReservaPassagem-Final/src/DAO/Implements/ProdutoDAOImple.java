package DAO.Implements;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

import DAO.ConexaoBanco.ConexaoDAO;
import DAO.Interfaces.IProdutoDAO;

import DAO.Modelos.Produto;

public class ProdutoDAOImple implements IProdutoDAO {
	@Override
	public void cadastrarProduto(Produto p) {
	    ConexaoDAO conexao = new ConexaoDAO();
	    conexao.abrirConexao();

	    try {
	        String sql = "INSERT INTO produto ( Nome, Descricao, Preco, QTD_Em_Estoque) VALUES ( ?, ?, ?, ?)";
	        @SuppressWarnings("resource")
			Scanner entrada = new Scanner(System.in);
	        
	        

	        System.out.print("Informe o nome do produto: ");
	        String nome = entrada.nextLine();

	        System.out.print("Informe a descricao do produto: ");
	        String descricao = entrada.nextLine();

	        System.out.print("Informe o preco do produto: ");
	        double preco = entrada.nextDouble();
	        
	        System.out.print("Informe a Quantidade em estoque: ");
	        int QTD_Em_Estoque = entrada.nextInt();
	        
	        

	        PreparedStatement stmt = conexao.prepareStatement(sql);
	        stmt.setString(1, nome);
	        stmt.setString(2, descricao);
	        stmt.setDouble(3, preco);
	        stmt.setInt(4, QTD_Em_Estoque);

	        stmt.executeUpdate();
	        System.out.println("Produto cadastrado com sucesso");
	        
	    } catch (SQLException e) {
	        e.printStackTrace();
	        System.err.println("Erro ao cadastrar produto: " + e.getMessage());
	    } finally {
	        conexao.fecharConexao();
	    }
	}

	@Override
	public void buscarProduto(Produto p) {
	    ConexaoDAO conexao = new ConexaoDAO();
	    conexao.abrirConexao();

	    try {
	        @SuppressWarnings("resource")
			Scanner entrada = new Scanner(System.in);
	        System.out.print("Informe o nome ou ID do produto a ser pesquisado: ");
	        String pesquisaNoBanco = entrada.nextLine();

	        boolean pesquisarPorNome = true;
	        int idPesquisado = 0;

	        try {
	            idPesquisado = Integer.parseInt(pesquisaNoBanco);
	            pesquisarPorNome = false;
	        } catch (NumberFormatException e) {
	            
	        }

	        String sql;
	        if (pesquisarPorNome) {
	            sql = "SELECT * FROM Produto WHERE Nome LIKE ?";
	        } else {
	            sql = "SELECT * FROM Produto WHERE IdProduto = ?";
	        }

	        PreparedStatement stmt = conexao.prepareStatement(sql);

	        
	        if (pesquisarPorNome) {
	            stmt.setString(1, pesquisaNoBanco );
	        } else {
	            stmt.setInt(1, idPesquisado);
	        }

	        ResultSet rs = stmt.executeQuery();
	        boolean produtoEncontrado = false; 

	        while (rs.next()) {
	            int id = rs.getInt("IdProduto"); 
	            String nome = rs.getString("Nome");
	            String descricao = rs.getString("Descricao");
	            double preco = rs.getDouble("Preco");
	            int Qtd = rs.getInt("QTD_Em_Estoque");
	            System.out.println("ID: " + id + ", Nome: " + nome + ", Descricao: " + descricao + ", Preco: R$ " + preco + ", Quantidade: " + Qtd);
	            produtoEncontrado = true; 
	        }

	        if (!produtoEncontrado) {
	            System.out.println("Produto não encontrado.");
	        }

	        rs.close();
	        stmt.close();
	        
	    } catch (SQLException e) {
	        e.printStackTrace();
	        System.err.println("Erro ao buscar produto: " + e.getMessage());
	    } finally {
	        conexao.fecharConexao();
	    }
	}


	@Override
	public void removerProduto(Produto p) {
		 ConexaoDAO conexao = new ConexaoDAO();
	          conexao.abrirConexao();

	        try {
	            @SuppressWarnings("resource")
				Scanner entrada = new Scanner(System.in);
	            System.out.print("Informe o ID do produto a ser removido: ");
	            int idProduto = Integer.parseInt(entrada.nextLine());

	            String sql = "DELETE FROM Produto WHERE IdProduto = ?";
	            PreparedStatement stmt = conexao.prepareStatement(sql);
	            stmt.setInt(1, idProduto);
	            int linhasAfetadas = stmt.executeUpdate();

	            if (linhasAfetadas > 0) {
	                System.out.println("Produto removido");
	            } else {
	                System.out.println("Produto com ID " + idProduto + " nao encontrado.");
	            }

	            stmt.close();
	          
	        } catch (SQLException e) {
	            e.printStackTrace();
	            System.err.println("Erro ao remover produto: " + e.getMessage());
	        } finally {
	            conexao.fecharConexao();
	        }
	    }

	@Override
	public void atualizarProduto(Produto p) {
		
	        ConexaoDAO conexao = new ConexaoDAO();
	         conexao.abrirConexao();

	        try {
	            @SuppressWarnings("resource")
				Scanner entrada = new Scanner(System.in);
	            System.out.print("Informe o ID do produto a ser atualizado: ");
	            int idProduto = Integer.parseInt(entrada.nextLine());

	            String sql = "SELECT * FROM Produto WHERE IdProduto = ?";
	            PreparedStatement stmt = conexao.prepareStatement(sql);
	            stmt.setInt(1, idProduto);
	            ResultSet rs = stmt.executeQuery();

	            if (rs.next()) {
	                String nomeAtual = rs.getString("Nome");
	                String descricaoAtual = rs.getString("Descricao");
	                double precoAtual = rs.getDouble("Preco");
	                int QTD_Atual = rs.getInt("QTD_Em_Estoque");

	                System.out.println("Nome atual: " + nomeAtual);
	                System.out.println("Descricao atual: " + descricaoAtual);
	                System.out.println("Preco atual: " + precoAtual);
	                System.out.println("Quantidade atual: " + QTD_Atual);

	                System.out.print("Informe o novo nome do produto (ou deixe em branco para manter o atual): ");
	                String novoNome = entrada.nextLine();
	                if (novoNome.isEmpty()) {
	                    novoNome = nomeAtual;
	                }

	                System.out.print("Informe a nova descricao do produto (ou deixe em branco para manter a atual): ");
	                String novaDescricao = entrada.nextLine();
	                if (novaDescricao.isEmpty()) {
	                    novaDescricao = descricaoAtual;
	                }

	                System.out.print("Informe o novo preco do produto (ou 0 para manter o atual): ");
	                double novoPreco = entrada.nextDouble();
	                entrada.nextLine();
	                if (novoPreco == 0) {
	                    novoPreco = precoAtual;
	                }
	                System.out.print("Informe nova quantidade (ou 0 para manter a atual):");
	                int nova_QTD = Integer.parseInt(entrada.nextLine());
	                if(nova_QTD == 0) {
	                	nova_QTD = QTD_Atual;
	                }
	                
	                sql = "UPDATE Produto SET Nome = ?, Descricao = ?, Preco = ?, QTD_Em_Estoque = ? WHERE IdProduto = ?";
	                stmt = conexao.prepareStatement(sql);
	               
	                stmt.setString(1, novoNome);
	                stmt.setString(2, novaDescricao);
	                stmt.setDouble(3, novoPreco);
	                stmt.setInt(4, nova_QTD);
	                stmt.setInt(5, idProduto);

	                int linhasAfetadas = stmt.executeUpdate();
	                if (linhasAfetadas > 0) {
	                    System.out.println("Produto atualizado");
	                } else {
	                    System.out.println("Nenhum produto atualizado");
	                }
	            } else {
	                System.out.println("Produto com ID " + idProduto + " nao encontrado.");
	            }

	            rs.close();
	            stmt.close();
	           
	        } catch (SQLException e) {
	            e.printStackTrace();
	            System.err.println("Erro ao atualizar produto: " + e.getMessage());
	        } finally {
	            conexao.fecharConexao();
	        }
	    }


	
}
