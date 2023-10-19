package DAO.Modelos;

public class Carrinho {
	private int IdUsuario;
	private int IdProduto;
	private int IdCarrinho;
	private int QTD_Carrinho;

	public int getIdUsuario() {
		return IdUsuario;
	}

	public void setIdUsuario(int idUsuario) {
		IdUsuario = idUsuario;
	}

	public int getIdProduto() {
		return IdProduto;
	}

	public void setIdProduto(int idProduto) {
		IdProduto = idProduto;
	}

	public int getIdCarrinho() {
		return IdCarrinho;
	}

	public void setIdCarrinho(int idCarrinho) {
		IdCarrinho = idCarrinho;
	}

	public int getQTD_Carrinho() {
		return QTD_Carrinho;
	}

	public void setQTD_Carrinho(int qTD_Carrinho) {
		QTD_Carrinho = qTD_Carrinho;
	}

}
