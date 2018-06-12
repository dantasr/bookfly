package controller;

import controller.Dispatcher.DispatchResponse;
import controller.FrontController.Request;
import negocio.FachadaRegrasNegocio;
import negocio.NegocioException;

public abstract class AbstractController {
	protected FrontController frontController;
	protected Dispatcher dispatcher;
	protected FachadaRegrasNegocio fachadaRegrasNegocio;

	public AbstractController(FrontController frontController, Dispatcher dispatcher,
			FachadaRegrasNegocio fachadaRegrasNegocio) {
		this.frontController = frontController;
		this.dispatcher = dispatcher;
		this.fachadaRegrasNegocio = fachadaRegrasNegocio;
	}
	
	protected void exibirErro(Pedido hashMap, String mensagem) {
		Pedido resposta = Pedido.criarNovoPedido(hashMap);
		resposta.put("mensagem", "Usuário não está ativado!");
		dispatcher.dispatch(DispatchResponse.MENSAGEM_FALHA, resposta);
	}
	
	protected void exibirSucesso(Pedido hashMap, String mensagem) {
		Pedido resposta = Pedido.criarNovoPedido(hashMap);
		resposta.put("mensagem", "Usuário não está ativado!");
		dispatcher.dispatch(DispatchResponse.MENSAGEM_FALHA, resposta);
	}
	
	public abstract void dispatchRequest(Request request, Pedido hashMap) throws NegocioException;
}
