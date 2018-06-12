package controller;

import java.sql.Date;
import java.util.HashMap;

import controller.Dispatcher.DispatchResponse;
import controller.FrontController.Request;
import dto.Usuario;
import negocio.FachadaRegrasNegocio;
import negocio.NegocioException;

public class LoginController extends AbstractController {
	public LoginController(FrontController frontController, Dispatcher dispatcher,
			FachadaRegrasNegocio fachadaRegrasNegocio) {
		super(frontController, dispatcher, fachadaRegrasNegocio);
		// TODO Auto-generated constructor stub
	}

	public void registerHandlers(HashMap<Request, AbstractController> microControladores) {
		Request[] requests = new Request[] { Request.LOGIN_CADASTRA_USUARIO, Request.LOGIN_REALIZA_LOGIN, Request.LOGIN_EXIBE_CADASTRO };
		for (Request r : requests)
			microControladores.put(r, this);
	}
	
	@Override
	public void dispatchRequest(Request request, Pedido hashMap) throws NegocioException {
		switch (request) {
		case LOGIN_CADASTRA_USUARIO:
			cadastraUsuario(hashMap);
			break;
		case LOGIN_EXIBE_CADASTRO:
			exibeCadastro(hashMap);
			break;
		case LOGIN_REALIZA_LOGIN:
			realizaLogin(hashMap);
			break;
		}
	}

	public void exibeCadastro(Pedido hashMap) {
		dispatcher.dispatch(DispatchResponse.CADASTRO);
	}

	public void realizaLogin(Pedido hashMap) throws NegocioException {
		String usuario = (String) hashMap.get("usuario");
		String senha = (String) hashMap.get("senha");
		
		Usuario atual = fachadaRegrasNegocio.buscaUsuarioPorNomeExato(usuario);
		
		if (atual == null) {
			exibirErro(hashMap, "Usuario não existe!");
			return;
		}
		
		if (!atual.isAtivado()) {
			exibirErro(hashMap, "Usuário não está ativado!");
			return;
		}
		
		if (atual.getSenha() != senha) {
			exibirErro(hashMap, "Senha está incorreta!");
			return;
		}
		
		Pedido novaSessao = Pedido.criarNovoPedido(hashMap);
		novaSessao.setUsuarioLogado(atual);
		
		if (atual.isAdministrador()) {
			dispatcher.dispatch(DispatchResponse.ADMIN_INICIO, novaSessao);
		} else {
			dispatcher.dispatch(DispatchResponse.USUARIO_INICIAL, novaSessao);
		}
	}

	public void cadastraUsuario(Pedido hashMap) throws NegocioException {
		String nome = (String) hashMap.get("nome");
		String senha = (String) hashMap.get("senha");
		Date dataNascimento = (Date) hashMap.get("dataNascimento");
		String cpf = (String) hashMap.get("cpf");
		String telefone = (String) hashMap.get("telefone");
		
		fachadaRegrasNegocio.cadastraUsuario(0, nome, dataNascimento, telefone, cpf, senha);
		dispatcher.dispatch(DispatchResponse.LOGIN, Pedido.criarNovoPedido(hashMap));
	}
}
