package beans;

public class Promocao {
	private int codigo;
	private Livro livro;
	private int preco;
	
	public Promocao() {}

	public Promocao(int codigo, Livro livro, int preco) {
		super();
		this.codigo = codigo;
		this.livro = livro;
		this.preco = preco;
	}

	public int getCodigo() {
		return codigo;
	}

	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}

	public Livro getLivro() {
		return livro;
	}

	public void setLivro(Livro livro) {
		this.livro = livro;
	}

	public int getPreco() {
		return preco;
	}

	public void setPreco(int preco) {
		this.preco = preco;
	}
	
	public String getLivroNome() {
		return livro.getNome();
	}
}
