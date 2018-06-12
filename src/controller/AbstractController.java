package controller;

import java.util.HashMap;

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
	
	public abstract void dispatchRequest(Request request, HashMap<String, Object> hashMap) throws NegocioException;
}
