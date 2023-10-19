package DAO.Implements;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.Scanner;

import DAO.ConexaoBanco.ConexaoDAO;
import DAO.Interfaces.IAdministradorDAO;
import DAO.Modelos.Administrador;


public class AdministradorDAOImple implements IAdministradorDAO {

	@Override
	public void cadastrarUsuario(Administrador admin) {
		ConexaoDAO conexao = new ConexaoDAO();
		conexao.abrirConexao();

		@SuppressWarnings("resource")
		Scanner entrada = new Scanner(System.in);

		boolean loginValido = false;

		while (!loginValido) {
			try {
				String sqlVerificarLogin = "SELECT COUNT(*) FROM Usuario WHERE Login = ?"; // verificar se login esta
																							// cadastrato no banco ja
				String sqlInserirUsuario = "INSERT INTO Usuario (Nome, Sobrenome, Login, Senha, Data_De_Nascimento) VALUES (?, ?, ?, ?, ?)";

				System.out.print("Informe o nome: ");
				String nome = entrada.nextLine();

				System.out.print("Informe o sobrenome: ");
				String sobrenome = entrada.nextLine();

				System.out.print("Informe o login: ");
				String login = entrada.nextLine();

				PreparedStatement stmtVerificarLogin = conexao.prepareStatement(sqlVerificarLogin);
				stmtVerificarLogin.setString(1, login);
				ResultSet resultSet = stmtVerificarLogin.executeQuery();

				if (resultSet.next() && resultSet.getInt(1) > 0) {
					System.out.println("Login ja esta em uso. Tente Novamente");
				} else {
					String senha;
					String confirmarSenha;

					do {
						System.out.print("Informe a senha: ");
						senha = entrada.nextLine();

						System.out.print("Confirme a senha: ");
						confirmarSenha = entrada.nextLine();

						if (!senha.equals(confirmarSenha)) {
							System.out.println("As senhas nao estao iguais. Tente novamente.");
						}
					} while (!senha.equals(confirmarSenha));

					System.out.print("Informe a data de nascimento (DD-MM-YYYY): ");
					String dataNascimento = entrada.nextLine();
					SimpleDateFormat FormatarData = new SimpleDateFormat("dd-MM-yyyy");// crie esse objeto aqui mas pode
																						// melhorar
					try {
						FormatarData.parse(dataNascimento);
					} catch (ParseException e) {
						System.out.println("Data inválida. Use o formato DD-MM-YYYY.");
						continue;
					}

					PreparedStatement stmtInserirUsuario = conexao.prepareStatement(sqlInserirUsuario);
					stmtInserirUsuario.setString(1, nome);
					stmtInserirUsuario.setString(2, sobrenome);
					stmtInserirUsuario.setString(3, login);
					stmtInserirUsuario.setString(4, senha);
					stmtInserirUsuario.setString(5, dataNascimento);

					stmtInserirUsuario.executeUpdate();
					System.out.println("Usuario cadastrado com sucesso!");
					loginValido = true;
					// sai do loop quando usuario do login for validado
				}
			} catch (SQLException e) {
				e.printStackTrace();
				System.out.println("Erro ao cadastrar usuário: " + e.getMessage());
			}
		}

		conexao.fecharConexao();
	}

	@Override
	public boolean efetuarlogin(Administrador admin) throws SQLException {
		ConexaoDAO conexao = new ConexaoDAO();
		conexao.abrirConexao();
		@SuppressWarnings("resource")
		Scanner entrada = new Scanner(System.in);

		System.out.print("Informe o login: ");
		String login = entrada.nextLine();

		System.out.print("Informe a senha: ");
		String senha = entrada.nextLine();

		try {
			String sql = "SELECT * FROM administrador WHERE login = ? AND senha = ?";

			PreparedStatement stmt =  conexao.prepareStatement(sql);
			stmt = conexao.prepareStatement(sql);
			stmt.setString(1, login);
			stmt.setString(2, senha);
			ResultSet rs = stmt.executeQuery();

			if (rs.next()) {

				// login feito com sucesso
				return true;
			} else {
				// login vazou
				return false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Erro ao efetuar o login: " + e.getMessage());
		} finally {

			conexao.fecharConexao();
		}
		return false;
		// ocorrendo uma exceção ou não for encontrado nenhum usuario vai retorna false
	}
}
