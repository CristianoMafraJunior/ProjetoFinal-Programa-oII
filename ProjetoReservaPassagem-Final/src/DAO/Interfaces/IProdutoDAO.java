package DAO.Interfaces;

import DAO.Modelos.Produto;

public interface IProdutoDAO {

	public void cadastrarProduto(Produto p);

	public void buscarProduto(Produto p);

	public void removerProduto(Produto p);

	public void atualizarProduto(Produto p);

}
