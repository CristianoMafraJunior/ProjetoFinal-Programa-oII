package DAO.Modelos;

public class Produto {
	private long idProduto;
	private String nome;
	private double preco;
	private String descricao;
	private long qtdEmEstoque;

	public long getIdProduto() {
		return idProduto;

	}

	public void setIdProduto(long idProduto) {
		this.idProduto = idProduto;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public double getPreco() {
		return preco;
	}

	public void setPreco(double preco) {
		this.preco = preco;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public long getQtdEmEstoque() {
		return qtdEmEstoque;
	}

	public void setQtdEmEstoque(long qtdEmEstoque) {
		this.qtdEmEstoque = qtdEmEstoque;
	}
}
