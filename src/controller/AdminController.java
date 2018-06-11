package controller;

import java.util.HashMap;

import controller.FrontController.Request;
import negocio.FachadaRegrasNegocio;

public class AdminController implements IController {
	private FrontController frontController;
	private Dispatcher dispatcher;
	private FachadaRegrasNegocio fachadaRegrasNegocio;

	public AdminController(FrontController frontController, Dispatcher dispatcher,
			FachadaRegrasNegocio fachadaRegrasNegocio) {
		super();
		this.frontController = frontController;
		this.dispatcher = dispatcher;
		this.fachadaRegrasNegocio = fachadaRegrasNegocio;
	}

	public void registerHandlers(HashMap<Request, IController> microControladores) {
		Request[] requests = new Request[] { Request.ADMIN_ABRIR_PESQUISA, Request.ADMIN_ABRIR_TELA_REGISTRAR,
				Request.ADMIN_SAIR, Request.ADMIN_DESATIVA_USUARIO, Request.ADMIN_ATIVA_USUARIO,
				Request.ADMIN_REMOVE_PRODUTO, Request.ADMIN_PESQUISA_PRODUTO, Request.ADMIN_PESQUISA_USUARIO,
				Request.ADMIN_CADASTRA_PROMO, Request.ADMIN_CADASTRA_LIVRO };
		for (Request r : requests)
			microControladores.put(r, this);
	}

	@Override
	public void dispatchRequest(Request request, HashMap<String, Object> hashMap) {
		// TODO Auto-generated method stub

	}

}
