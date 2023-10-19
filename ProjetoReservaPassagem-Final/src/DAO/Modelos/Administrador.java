package DAO.Modelos;

public class Administrador {

	private int IdAdministrador;
	private String login;
	private String usuario;

	public int getIAdministrador() {
		return IdAdministrador;
	}

	public void setIdAdministrador(int idAdmistrador) {
		IdAdministrador = idAdmistrador;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

}
