package controller;

import java.util.HashMap;
import java.util.List;

import controller.FrontController.Request;
import dto.Aluguel;
import dto.Venda;
import negocio.FachadaRegrasNegocio;
import negocio.NegocioException;

public class LivroController extends AbstractController {
	public LivroController(FrontController frontController, Dispatcher dispatcher,
			FachadaRegrasNegocio fachadaRegrasNegocio) {
		super(frontController, dispatcher, fachadaRegrasNegocio);
		// TODO Auto-generated constructor stub
	}

	public void registerHandlers(HashMap<Request, AbstractController> microControladores) {
		Request[] requests = new Request[] { Request.LER_LIVRO, Request.DESCREVE_LIVRO, Request.USUARIO_REALIZA_PESQUISA, Request.USUARIO_EXIBE_TELA_MEUS_LIVROS };
		for (Request r : requests)
			microControladores.put(r, this);
	} 
	
	@Override
	public void dispatchRequest(Request request, Pedido pedido) {
		switch (request) {
		case LER_LIVRO:
			lerLivro(pedido);
			break;
		case DESCREVE_LIVRO:
			descreveLivro(pedido);
			break;
		case USUARIO_REALIZA_PESQUISA:
			realizaPesquisa(pedido);
			break;
		case USUARIO_EXIBE_TELA_MEUS_LIVROS:
			exibeTelaMeusLivros(pedido);
			break;
		} 
	}

	public void exibeTelaMeusLivros(Pedido pedido) {
		Pedido resposta = Pedido.criarNovoPedido(pedido);
		
		List<Venda> vendas = fachadaRegrasNegocio.buscaVendasDoUsuario(pedido.getUsuarioLogado().getCodigo());
		List<Aluguel> alugueis = fachadaRegrasNegocio.buscaAlugueisDoUsuario(pedido.getUsuarioLogado().getCodigo());
		resposta.put("vendas", vendas);
		resposta.put("alugueis", alugueis);
		
		dispatcher.dispatch(DispatchResponse.USUARIO_MEUS_LIVROS, resposta);
	}

	private void realizaPesquisa(Pedido pedido) {
		
	}

	private void descreveLivro(Pedido pedido) {
		
	}

	private void lerLivro(Pedido pedido) {
		// TODO fazer???
	}

}
