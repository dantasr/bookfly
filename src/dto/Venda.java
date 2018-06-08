package dto;

import java.sql.Timestamp;

public class Venda {
	private int codigo;
	private Livro livro;
	private Usuario usuario;
	private Timestamp dataDaVenda;
	
	public Venda() {}

	public Venda(int codigo, Livro livro, Usuario usuario, Timestamp dataDaVenda) {
		super();
		this.codigo = codigo;
		this.livro = livro;
		this.usuario = usuario;
		this.dataDaVenda = dataDaVenda;
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
	
	public Timestamp getDataDaVenda() {
		return dataDaVenda;
	}
	
	public void setDataDaVenda(Timestamp dataDaVenda) {
		this.dataDaVenda = dataDaVenda;
	}
}
