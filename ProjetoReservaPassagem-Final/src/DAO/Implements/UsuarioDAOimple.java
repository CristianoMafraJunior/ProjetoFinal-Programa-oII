package DAO.Implements;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

import com.mysql.jdbc.PreparedStatement;

import DAO.ConexaoBanco.ConexaoDAO;
import DAO.Interfaces.IUsuarioDAO;
import DAO.Modelos.Usuario;

public class UsuarioDAOimple implements IUsuarioDAO {

	public boolean efetuarlogin(Usuario U) {
	    ConexaoDAO conexao = new ConexaoDAO();
	    conexao.abrirConexao();

	    Scanner entrada = new Scanner(System.in);

	    System.out.print("Informe o login: ");
	    String login = entrada.nextLine();

	    System.out.print("Informe a senha: ");
	    String senha = entrada.nextLine();

	    try {
	        String sql = "SELECT IdUsuario FROM Usuario WHERE login = ? AND senha = ?";
	        PreparedStatement stmt = (PreparedStatement) conexao.prepareStatement(sql);
	        stmt.setString(1, login);
	        stmt.setString(2, senha);
	        ResultSet rs = stmt.executeQuery();

	        if (rs.next()) {
	            // Login bem-sucedido, atribua o ID do usuário à instância de Usuario
	            U.setIdUsuario(rs.getInt("IdUsuario"));
	            return true;
	        } else {
	            // Login falhou
	            return false;
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	        System.err.println("Erro ao efetuar o login: " + e.getMessage());
	        return false;
	    } finally {
	        conexao.fecharConexao();
	    }
	}


	@Override
	public void buscarProduto(Usuario U) {
	    ConexaoDAO conexao = new ConexaoDAO();
	    conexao.abrirConexao();

	    try {
	        @SuppressWarnings("resource")
			Scanner entrada = new Scanner(System.in);
	        System.out.print("Informe o nome ou ID do produto a ser pesquisado: ");
	        String pesquisaNoBanco = entrada.nextLine();

	        boolean pesquisarPorNome = true;
	        int idPesquisado = 0;

	        try { // aqui vai verificar se a entrada for um numero a pesquisa sai ser feita por id 
	        	// e pesquisaPorNome vai ser falsa
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

	        PreparedStatement stmt = (PreparedStatement) conexao.prepareStatement(sql);

	        
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
	    }finally {
	    conexao.fecharConexao();
	    }
	}

	

}
