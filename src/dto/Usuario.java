package dto;

import java.sql.Date;

public class Usuario {
	private int codigo;
	private String nome;
	private Date dataNascimento;
	private String telefone;
	private String cpf;
	private String senha;
	private int saldoCartaoClube;
	private boolean administrador;
	private boolean ativado;

	public Usuario() {
	}

	public Usuario(int codigo, String nome, Date dataNascimento, String telefone, String cpf, String senha,
			int saldoCartaoClube, boolean administrador, boolean ativado) {
		super();
		this.codigo = codigo;
		this.nome = nome;
		this.dataNascimento = dataNascimento;
		this.telefone = telefone;
		this.cpf = cpf;
		this.senha = senha;
		this.saldoCartaoClube = saldoCartaoClube;
		this.administrador = administrador;
		this.ativado = ativado;
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

	public Date getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(Date dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}
	
	public int getSaldoCartaoClube() {
		return saldoCartaoClube;
	}
	
	public void setSaldoCartaoClube(int saldoCartaoClube) {
		this.saldoCartaoClube = saldoCartaoClube;
	}

	public boolean isAdministrador() {
		return administrador;
	}

	public void setAdministrador(boolean administrador) {
		this.administrador = administrador;
	}

	public boolean isAtivado() {
		return ativado;
	}

	public void setAtivado(boolean ativado) {
		this.ativado = ativado;
	}
}
