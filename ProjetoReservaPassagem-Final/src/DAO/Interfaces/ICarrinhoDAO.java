package DAO.Interfaces;

import java.sql.SQLException;

import DAO.Modelos.Carrinho;
import DAO.Modelos.Usuario;

public interface ICarrinhoDAO {

	public void removerDoCarrinho(Carrinho C,int idUsuario) throws SQLException;

	public void adicionarNoCarinho(Carrinho C, int idUsuario , Usuario Usuario);

	public void mostrarTodosProdutoDoCarrinho(Carrinho c, int idUsuario);

	public boolean verificarEstoque(Carrinho C);

}
