package controller;

import java.util.HashMap;

import controller.FrontController.Request;
import negocio.FachadaRegrasNegocio;

public class LoginController implements IController {
	private FrontController frontController;
	private Dispatcher dispatcher;
	private FachadaRegrasNegocio fachadaRegrasNegocio;

	public LoginController(FrontController frontController, Dispatcher dispatcher,
			FachadaRegrasNegocio fachadaRegrasNegocio) {
		super();
		this.frontController = frontController;
		this.dispatcher = dispatcher;
		this.fachadaRegrasNegocio = fachadaRegrasNegocio;
	}

	public void registerHandlers(HashMap<Request, IController> microControladores) {
		Request[] requests = new Request[] { Request.USUARIO_LER_LIVRO, Request.USUARIO_DESCREVE_LIVRO };
		for (Request r : requests)
			microControladores.put(r, this);
	}
	
	@Override
	public void dispatchRequest(Request request, HashMap<String, Object> microControladores) {
		
	}
}
