package controller;

import java.util.HashMap;

import controller.FrontController.Request;
import negocio.FachadaRegrasNegocio;

public class LivroController implements IController {
	private FrontController frontController;
	private Dispatcher dispatcher;
	private FachadaRegrasNegocio fachadaRegrasNegocio;

	public LivroController(FrontController frontController, Dispatcher dispatcher,
			FachadaRegrasNegocio fachadaRegrasNegocio) {
		super();
		this.frontController = frontController;
		this.dispatcher = dispatcher;
		this.fachadaRegrasNegocio = fachadaRegrasNegocio;
	}	
	
	public void registerHandlers(HashMap<Request, IController> microControladores) {
		Request[] requests = new Request[] { Request.LOGIN_CADASTRA_USUARIO, Request.LOGIN_REALIZA_LOGIN };
		for (Request r : requests)
			microControladores.put(r, this);
	}
	
	@Override
	public void dispatchRequest(Request request, HashMap<String, Object> hashMap) {
		// TODO Auto-generated method stub

	}

}
