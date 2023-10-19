package DAO.Interfaces;

import java.sql.SQLException;

import DAO.Modelos.Administrador;

public interface IAdministradorDAO {

	public void cadastrarUsuario(Administrador admin);

	public boolean efetuarlogin(Administrador admin) throws SQLException;

}
