package controller;

import java.util.HashMap;
import java.util.List;

import controller.FrontController.Request;
import dto.Aluguel;
import dto.Venda;
import negocio.FachadaRegrasNegocio;
import negocio.NegocioException;

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
				Request.USUARIO_EXIBE_TELA_COMPRA, Request.USUARIO_EXIBE_TELA_MEU_HISTORICO};
		for (Request r : requests)
			microControladores.put(r, this);
	}
	
	@Override
	public void dispatchRequest(Request request, Pedido hashMap) {
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
		case USUARIO_EXIBE_TELA_MEU_HISTORICO:
			exibeTelaMeuHistorico(hashMap);
			break;
		}
	}

	public void exibeTelaMeuHistorico(Pedido hashMap) throws NegocioException {
		Pedido resposta = Pedido.criarNovoPedido(hashMap);
		
		List<Venda> vendas = fachadaRegrasNegocio.buscaVendasDoUsuario(hashMap.getUsuarioLogado().getCodigo());
		List<Aluguel> alugueis = fachadaRegrasNegocio.buscaAlugueisDoUsuario(hashMap.getUsuarioLogado().getCodigo());
		resposta.put("vendas", vendas);
		resposta.put("alugueis", alugueis);
		
		dispatcher.dispatch(DispatchResponse.USUARIO_MEU_HISTORICO, resposta);
	}

	private void exibeTelaPagamentoAluguel(Pedido hashMap) {
		// TODO Auto-generated method stub
		
	}

	private void exibeTelaCompraCartaoClube(Pedido hashMap) {
		// TODO Auto-generated method stub
		
	}

	private void exibeTelaAdicionaSaldo(Pedido hashMap) {
		// TODO Auto-generated method stub
		
	}

	private void realizaCompraCartaoClube(Pedido hashMap) {
		// TODO Auto-generated method stub
		
	}

	private void realizaAluguelCartaoClube(Pedido hashMap) {
		// TODO Auto-generated method stub
		
	}

	private void realizaCompraCartaoNormal(Pedido hashMap) {
		// TODO Auto-generated method stub
		
	}

	private void insereCredito(Pedido hashMap) {
		// TODO Auto-generated method stub
		
	}

	private void exibeTelaAluguel(Pedido hashMap) {
		// TODO Auto-generated method stub
		
	}

	private void exibeTelaCompra(Pedido hashMap) {
		// TODO Auto-generated method stub
		
	}

}
