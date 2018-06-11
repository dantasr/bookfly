package controller;

import java.util.HashMap;

import visualizacao.admin.TelaAdmin;

public class Dispatcher {
	private FrontController frontController;
	
	public enum DispatchResponse {
		ADMIN_INICIO, ADMIN_PESQUISA, ADMIN_PROMOCAO, ADMIN_REGISTRO,
		CADASTRO, LOGIN,
		USUARIO_INICIAL, USUARIO_ALUGA, USUARIO_COMPRA,
		USUARIO_COMPRA_CARTAO_CLUBE, USUARIO_ALUGA_CARTAO_CLUBE,
		USUARIO_COMPRA_CARTAO_NORMAL,
		USUARIO_INSERE_CREDITO,
		USUARIO_LIVRO,
		USUARIO_HISTORICO, USUARIO_MEUS_LIVROS, USUARIO_PROMOCAO,
		USUARIO_RESULTADO_PESQUISA
	}
	
	private HashMap<DispatchResponse, IAcceptRequests> dispatchMap = new HashMap<DispatchResponse, IAcceptRequests>();
	
	public Dispatcher(FrontController frontController) {
		this.frontController = frontController;
		dispatchMap.put(DispatchResponse.ADMIN_INICIO, new TelaAdmin(frontController));
	}
	
	public void dispatch(DispatchResponse request) {
		dispatch(request, new HashMap<String, Object>());
	}
	
	public void dispatch(DispatchResponse request, HashMap<String, Object> params) {
		
	}
}
