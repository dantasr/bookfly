package dto;

public class Promocao {
	private int codigo;
	private int preco;
	
	public Promocao() {}

	public Promocao(int codigo, int preco) {
		super();
		this.codigo = codigo;
		this.preco = preco;
	}

	public int getCodigo() {
		return codigo;
	}

	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}

	public int getPreco() {
		return preco;
	}

	public void setPreco(int preco) {
		this.preco = preco;
	}
}
