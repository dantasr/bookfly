package controller;

import java.util.HashMap;

import controller.FrontController.Request;
import negocio.FachadaRegrasNegocio;

public class CompraController extends AbstractController {
	public CompraController(FrontController frontController, Dispatcher dispatcher,
			FachadaRegrasNegocio fachadaRegrasNegocio) {
		super(frontController, dispatcher, fachadaRegrasNegocio);
		// TODO Auto-generated constructor stub
	}

	public void registerHandlers(HashMap<Request, AbstractController> microControladores) {
		Request[] requests = new Request[] { Request.USUARIO_EXIBE_TELA_PAGAMENTO_ALUGUEL, Request.USUARIO_EXIBE_TELA_COMPRA_CARTAO_CLUBE, Request.USUARIO_EXIBE_TELA_COMPRA_CARTAO_COMUM,
				Request.USUARIO_EXIBE_TELA_ADICIONA_SALDO, Request.USUARIO_REALIZA_COMPRA_CARTAO_CLUBE, Request.USUARIO_REALIZA_ALUGUEL_CARTAO_CLUBE,
				Request.USUARIO_REALIZA_COMPRA_CARTAO_NORMAL, Request.USUARIO_INSERE_CREDITO, Request.USUARIO_EXIBE_TELA_ALUGUEL,
				Request.USUARIO_EXIBE_TELA_COMPRA};
		for (Request r : requests)
			microControladores.put(r, this);
	}
	
	@Override
	public void dispatchRequest(Request request, HashMap<String, Object> hashMap) {
		switch (request) {
		case USUARIO_EXIBE_TELA_PAGAMENTO_ALUGUEL:
			exibeTelaPagamentoAluguel(hashMap);
			break;
		case USUARIO_EXIBE_TELA_COMPRA_CARTAO_CLUBE:
			exibeTelaCompraCartaoClube(hashMap);
			break;
		case USUARIO_EXIBE_TELA_ADICIONA_SALDO:
			exibeTelaAdicionaSaldo(hashMap);
			break;
		case USUARIO_REALIZA_COMPRA_CARTAO_CLUBE:
			realizaCompraCartaoClube(hashMap);
			break;
		case USUARIO_REALIZA_ALUGUEL_CARTAO_CLUBE:
			realizaAluguelCartaoClube(hashMap);
			break;
		case USUARIO_REALIZA_COMPRA_CARTAO_NORMAL:
			realizaCompraCartaoNormal(hashMap);
			break;
		case USUARIO_INSERE_CREDITO:
			insereCredito(hashMap);
			break;
		case USUARIO_EXIBE_TELA_ALUGUEL:
			exibeTelaAluguel(hashMap);
			break;
		case USUARIO_EXIBE_TELA_COMPRA:
			exibeTelaCompra(hashMap);
			break;
		}
	}

	private void exibeTelaPagamentoAluguel(HashMap<String, Object> hashMap) {
		// TODO Auto-generated method stub
		
	}

	private void exibeTelaCompraCartaoClube(HashMap<String, Object> hashMap) {
		// TODO Auto-generated method stub
		
	}

	private void exibeTelaAdicionaSaldo(HashMap<String, Object> hashMap) {
		// TODO Auto-generated method stub
		
	}

	private void realizaCompraCartaoClube(HashMap<String, Object> hashMap) {
		// TODO Auto-generated method stub
		
	}

	private void realizaAluguelCartaoClube(HashMap<String, Object> hashMap) {
		// TODO Auto-generated method stub
		
	}

	private void realizaCompraCartaoNormal(HashMap<String, Object> hashMap) {
		// TODO Auto-generated method stub
		
	}

	private void insereCredito(HashMap<String, Object> hashMap) {
		// TODO Auto-generated method stub
		
	}

	private void exibeTelaAluguel(HashMap<String, Object> hashMap) {
		// TODO Auto-generated method stub
		
	}

	private void exibeTelaCompra(HashMap<String, Object> hashMap) {
		// TODO Auto-generated method stub
		
	}

}
