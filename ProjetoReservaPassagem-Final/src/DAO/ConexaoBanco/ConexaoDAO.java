package DAO.ConexaoBanco;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class ConexaoDAO implements IConexaoDAO {
    private Connection conexao;

    @Override
    public void abrirConexao() {
        try {
        	final String stringDeConexao = "jdbc:mysql://localhost/bancoprogramaçãoii?useSSL=false";
            final String usuario = "root";
            final String senha = "unifebe";

            conexao = DriverManager.getConnection(stringDeConexao, usuario, senha);
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao abrir a conexão com o banco de dados.", e);
        }
    }

    @Override
    public void fecharConexao() {
        try {
            if (conexao != null) {
                conexao.close();
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao fechar a conexão com o banco de dados.", e);
        }
    }

    @Override
    public PreparedStatement prepareStatement(String sql) throws SQLException {
        if (conexao == null) {
            throw new SQLException("Conexão não foi aberta");
        }
        return conexao.prepareStatement(sql);
    }

    @Override
    public Statement createStatement() throws SQLException {
        if (conexao == null) {
            throw new SQLException("Conexão não foi aberta");
        }
        return conexao.createStatement();
    }
}
