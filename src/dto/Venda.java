package dto;

public class Venda {
	private int codigo;
	private Livro livro;
	private Usuario usuario;
	
	public Venda() {}

	public Venda(int codigo, Livro livro, Usuario usuario) {
		super();
		this.codigo = codigo;
		this.livro = livro;
		this.usuario = usuario;
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

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
}
