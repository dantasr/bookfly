package beans;

import java.sql.Date;

public class Livro {
	private int codigo;
	private String nome;
	private String autor;
	private String editora;
	private int preco;
	private Date dataLancamento;
	private String sinopse;

	public Livro() {}

	public Livro(int codigo, String nome, String autor, String editora, int preco, Date dataLancamento) {
		super();
		this.codigo = codigo;
		this.nome = nome;
		this.autor = autor;
		this.editora = editora;
		this.preco = preco;
		this.dataLancamento = dataLancamento;
	}

	public int getCodigo() {
		return codigo;
	}

	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getAutor() {
		return autor;
	}

	public void setAutor(String autor) {
		this.autor = autor;
	}

	public String getEditora() {
		return editora;
	}

	public void setEditora(String editora) {
		this.editora = editora;
	}

	public int getPreco() {
		return preco;
	}

	public void setPreco(int preco) {
		this.preco = preco;
	}

	public Date getDataLancamento() {
		return dataLancamento;
	}

	public void setDataLancamento(Date dataLancamento) {
		this.dataLancamento = dataLancamento;
	}
	
}
