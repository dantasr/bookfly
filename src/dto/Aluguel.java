package dto;

import java.sql.Timestamp;

public class Aluguel {
	private int codigo;
	private Livro livro;
	private Usuario usuario;
	private Timestamp expiracao;
	
	public Aluguel() {}

	public Aluguel(int codigo, Livro livro, Usuario usuario, Timestamp expiracao) {
		super();
		this.codigo = codigo;
		this.livro = livro;
		this.usuario = usuario;
		this.expiracao = expiracao;
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

	public Timestamp getExpiracao() {
		return expiracao;
	}

	public void setExpiracao(Timestamp expiracao) {
		this.expiracao = expiracao;
	}
}
