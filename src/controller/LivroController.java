package controller;

import java.util.HashMap;

import controller.FrontController.Request;
import negocio.FachadaRegrasNegocio;

public class LivroController extends AbstractController {
	public LivroController(FrontController frontController, Dispatcher dispatcher,
			FachadaRegrasNegocio fachadaRegrasNegocio) {
		super(frontController, dispatcher, fachadaRegrasNegocio);
		// TODO Auto-generated constructor stub
	}

	public void registerHandlers(HashMap<Request, AbstractController> microControladores) {
		Request[] requests = new Request[] { Request.LOGIN_CADASTRA_USUARIO, Request.LOGIN_REALIZA_LOGIN };
		for (Request r : requests)
			microControladores.put(r, this);
	}
	
	@Override
	public void dispatchRequest(Request request, HashMap<String, Object> hashMap) {
		switch (request) {
		case LOGIN_CADASTRA_USUARIO:
			cadastraUsuario();
			break;
		case LOGIN_REALIZA_LOGIN:
			realizaLogin();
			break;
		}
	}

	public void realizaLogin() {
		// TODO Auto-generated method stub
		
	}

	public void cadastraUsuario() {
		// TODO Auto-generated method stub
		
	}
}
