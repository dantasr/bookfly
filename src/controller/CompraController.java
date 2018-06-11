package controller;

import java.util.HashMap;

import controller.FrontController.Request;
import negocio.FachadaRegrasNegocio;

public class CompraController implements IController {
	private FrontController frontController;
	private Dispatcher dispatcher;
	private FachadaRegrasNegocio fachadaRegrasNegocio;

	public CompraController(FrontController frontController, Dispatcher dispatcher,
			FachadaRegrasNegocio fachadaRegrasNegocio) {
		super();
		this.frontController = frontController;
		this.dispatcher = dispatcher;
		this.fachadaRegrasNegocio = fachadaRegrasNegocio;
	}

	public void registerHandlers(HashMap<Request, IController> microControladores) {
		Request[] requests = new Request[] { Request.USUARIO_EXIBE_TELA_PAGAMENTO_ALUGUEL, Request.USUARIO_EXIBE_TELA_COMPRA_CARTAO_CLUBE, Request.USUARIO_EXIBE_TELA_COMPRA_CARTAO_COMUM,
				Request.USUARIO_EXIBE_TELA_ADICIONA_SALDO, Request.USUARIO_REALIZA_COMPRA_CARTAO_CLUBE, Request.USUARIO_REALIZA_ALUGUEL_CARTAO_CLUBE,
				Request.USUARIO_REALIZA_COMPRA_CARTAO_NORMAL, Request.USUARIO_INSERE_CREDITO, Request.USUARIO_EXIBE_TELA_ALUGUEL,
				Request.USUARIO_EXIBE_TELA_COMPRA};
		for (Request r : requests)
			microControladores.put(r, this);
	}
	
	@Override
	public void dispatchRequest(Request request, HashMap<String, Object> hashMap) {
		// TODO Auto-generated method stub

	}

}
