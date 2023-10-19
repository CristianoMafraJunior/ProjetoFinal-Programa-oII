package DAO.ConexaoBanco;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public interface IConexaoDAO {

	public void abrirConexao();

	public void fecharConexao();

	public Statement createStatement() throws SQLException;

	PreparedStatement prepareStatement(String sql) throws SQLException;
	
	

}
