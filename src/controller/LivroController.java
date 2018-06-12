package controller;

import java.util.HashMap;

import controller.FrontController.Request;
import negocio.FachadaRegrasNegocio;
import negocio.NegocioException;

public class LivroController extends AbstractController {
	public LivroController(FrontController frontController, Dispatcher dispatcher,
			FachadaRegrasNegocio fachadaRegrasNegocio) {
		super(frontController, dispatcher, fachadaRegrasNegocio);
		// TODO Auto-generated constructor stub
	}

	public void registerHandlers(HashMap<Request, AbstractController> microControladores) {
		Request[] requests = new Request[] { Request.USUARIO_LER_LIVRO, Request.USUARIO_DESCREVE_LIVRO,Request.USUARIO_REALIZA_PESQUISA };
		for (Request r : requests)
			microControladores.put(r, this);
	} 
	
	@Override
	public void dispatchRequest(Request request, Pedido pedido) {
		switch (request) {
		case USUARIO_LER_LIVRO:
			lerLivro(pedido);
			break;
		case USUARIO_DESCREVE_LIVRO:
			descreveLivro(pedido);
			break;
		case USUARIO_REALIZA_PESQUISA:
			realizaPesquisa(pedido);
			break;
		} 
	}

	private void realizaPesquisa(Pedido pedido) {
		// TODO Auto-generated method stub
		
	}

	private void descreveLivro(Pedido pedido) {
		// TODO Auto-generated method stub
		
	}

	private void lerLivro(Pedido pedido) {
		// TODO Auto-generated method stub
		
	}

}
