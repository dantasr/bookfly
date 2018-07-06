package main;

import basedados.BaseDadosException;
import basedados.FachadaBaseDados;
import basedados.FachadaBaseDadosDao;
import cartoes.GerenciadorElo;
import cartoes.GerenciadorMastercard;
import cartoes.GerenciadorVisa;
import dto.Livro;
import dto.Usuario;
import negocio.FachadaRegrasNegocio;

public class Contexto {
	private FachadaBaseDados db;
	private FachadaRegrasNegocio gerenciadorRegrasNegocio;
	private GerenciadorMastercard gerenciadorMastercard;
	private GerenciadorVisa gerenciadorVisa;
	private GerenciadorElo gerenciadorElo;
	private Usuario usuarioAtual;
	private Livro livroAtual;
	
	public Contexto() throws BaseDadosException {
		db = new FachadaBaseDadosDao();
		gerenciadorRegrasNegocio = new FachadaRegrasNegocio(db);
		gerenciadorMastercard = new GerenciadorMastercard();
		gerenciadorVisa = new GerenciadorVisa();
		gerenciadorElo = new GerenciadorElo();
		usuarioAtual = null;
	}
	
	public GerenciadorMastercard getGerenciadorMastercard() {
		return gerenciadorMastercard;
	}

	public GerenciadorVisa getGerenciadorVisa() {
		return gerenciadorVisa;
	}

	public GerenciadorElo getGerenciadorElo() {
		return gerenciadorElo;
	}

	public FachadaRegrasNegocio getFachadaRegrasNegocio() {
		return gerenciadorRegrasNegocio;
	}
	
	public String getLivroNome() {
		return livroAtual.getNome();
	}
	
	public Usuario getUsuarioAtual() {
		return usuarioAtual; 
	}
	
	public void setUsuarioAtual(Usuario usuario) {
		this.usuarioAtual = usuario;
	}
	
	public void setLivroAtual(String nome) {
		this.livroAtual.setNome(nome);
	}
	
}
