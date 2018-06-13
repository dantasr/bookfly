package controller;

import java.util.HashMap;

import visualizacao.admin.TelaAdmin;
import visualizacao.admin.TelaAdminPesquisaRemoveClienteProduto;
import visualizacao.admin.TelaAdminPromocao;
import visualizacao.admin.TelaAdminRegistro;

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
		USUARIO_RESULTADO_PESQUISA,
		
		// Respostas meio que genericas?
		MENSAGEM_SUCESSO, MENSAGEM_FALHA
	}
	
	private HashMap<DispatchResponse, IAcceptRequests> dispatchMap = new HashMap<DispatchResponse, IAcceptRequests>();
	
	public Dispatcher(FrontController frontController) {
		this.frontController = frontController;
		dispatchMap.put(DispatchResponse.ADMIN_INICIO, new TelaAdmin(frontController));
		dispatchMap.put(DispatchResponse.ADMIN_PESQUISA, new TelaAdminPesquisaRemoveClienteProduto(frontController));
		dispatchMap.put(DispatchResponse.ADMIN_PROMOCAO, new TelaAdminPromocao(frontController));
		dispatchMap.put(DispatchResponse.ADMIN_REGISTRO, new TelaAdminRegistro(frontController));
	}
	
	public void dispatch(DispatchResponse request) {
		dispatch(request, new Pedido());
	}
	
	public void dispatch(DispatchResponse response, Pedido params) {
		dispatchMap.get(response).acceptRequest(params);
	}
}
