package controller;

import java.util.HashMap;

import negocio.FachadaRegrasNegocio;
import negocio.NegocioException;

/*public class FrontController {
	private FachadaBaseDados db;
	private FachadaRegrasNegocio gerenciadorRegrasNegocio;
	private GerenciadorPreco gerenciadorPreco;
	private GerenciadorMastercard gerenciadorMastercard;
	private GerenciadorVisa gerenciadorVisa;
	private GerenciadorElo gerenciadorElo;
	private Usuario usuarioAtual;
	private Livro livroAtual;
	
	public FrontController() throws BaseDadosException {
		db = new FachadaBaseDadosDao();
		gerenciadorRegrasNegocio = new FachadaRegrasNegocio(db);
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

	public FachadaRegrasNegocio getGerenciadorRegrasNegocio() {
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
	
}*/

public class FrontController {
	private FachadaRegrasNegocio fachadaRegrasNegocio;
	public enum Request {
		ADMIN_ABRIR_PESQUISA, ADMIN_ABRIR_TELA_REGISTRAR, ADMIN_SAIR, ADMIN_DESATIVA_USUARIO,
		ADMIN_ATIVA_USUARIO, ADMIN_REMOVE_PRODUTO, ADMIN_PESQUISA_PRODUTO, ADMIN_PESQUISA_USUARIO,
		ADMIN_CADASTRA_PROMO, ADMIN_CADASTRA_LIVRO, ADMIN_EXIBE_TELA_CADASTRA_PROMOCAO, LOGIN_CADASTRA_USUARIO, LOGIN_REALIZA_LOGIN, LOGIN_EXIBE_CADASTRO, USUARIO_REALIZA_PESQUISA,
		USUARIO_EXIBE_TELA_PAGAMENTO_ALUGUEL, USUARIO_EXIBE_TELA_COMPRA_CARTAO_CLUBE, USUARIO_EXIBE_TELA_COMPRA_CARTAO_COMUM,
		USUARIO_EXIBE_TELA_ADICIONA_SALDO, USUARIO_REALIZA_COMPRA_CARTAO_CLUBE, USUARIO_REALIZA_ALUGUEL_CARTAO_CLUBE,
		USUARIO_REALIZA_COMPRA_CARTAO_NORMAL, USUARIO_INSERE_CREDITO, USUARIO_EXIBE_TELA_ALUGUEL,
		USUARIO_EXIBE_TELA_COMPRA, USUARIO_LER_LIVRO, USUARIO_DESCREVE_LIVRO, ADMIN_REMOVE_PROMO,
	}
	
	private HashMap<Request, AbstractController> microControladores = new HashMap<Request, AbstractController>();
	
	public void dispatchRequest(Request request) {
		dispatchRequest(request, new HashMap<String, Object>());
	}

	public void dispatchRequest(Request request, Pedido hashMap) {
		try {
			microControladores.get(request).dispatchRequest(request, hashMap);
		} catch (NegocioException e) {
			e.printStackTrace();
		}
	}
}
