package main;

import java.util.Date;

import basedados.BaseDadosException;
import basedados.GerenciadorBaseDados;
import basedados.GerenciadorBaseDadosJDBC;
import cartoes.GerenciadorElo;
import cartoes.GerenciadorMastercard;
import cartoes.GerenciadorVisa;
import dto.Livro;
import dto.Usuario;
import negocio.GerenciadorPreco;
import negocio.GerenciadorRegrasNegocio;
import negocio.NegocioException;

public class Contexto {
	private GerenciadorBaseDados db;
	private GerenciadorRegrasNegocio gerenciadorRegrasNegocio;
	private GerenciadorPreco gerenciadorPreco;
	private GerenciadorMastercard gerenciadorMastercard;
	private GerenciadorVisa gerenciadorVisa;
	private GerenciadorElo gerenciadorElo;
	private Usuario usuarioAtual;
	private Livro livroAtual;
	
	public Contexto() throws BaseDadosException {
		db = new GerenciadorBaseDadosJDBC();
		gerenciadorRegrasNegocio = new GerenciadorRegrasNegocio(db);
		gerenciadorPreco = new GerenciadorPreco(db);
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

	public GerenciadorRegrasNegocio getGerenciadorRegrasNegocio() {
		return gerenciadorRegrasNegocio;
	}
	
	public GerenciadorPreco getGerenciadorPreco() {
		return gerenciadorPreco;
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
