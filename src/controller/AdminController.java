package controller;

import java.util.HashMap;
import java.util.List;

import controller.Dispatcher.DispatchResponse;
import controller.FrontController.Request;
import dto.Livro;
import dto.Usuario;
import negocio.FachadaRegrasNegocio;
import negocio.NegocioException;

public class AdminController extends AbstractController {
	public AdminController(FrontController frontController, Dispatcher dispatcher,
			FachadaRegrasNegocio fachadaRegrasNegocio) {
		super(frontController, dispatcher, fachadaRegrasNegocio);
		// TODO Auto-generated constructor stub
	}

	public void registerHandlers(HashMap<Request, AbstractController> microControladores) {
		Request[] requests = new Request[] { Request.ADMIN_ABRIR_PESQUISA, Request.ADMIN_ABRIR_TELA_REGISTRAR,
				Request.ADMIN_SAIR, Request.ADMIN_DESATIVA_USUARIO, Request.ADMIN_ATIVA_USUARIO,
				Request.ADMIN_REMOVE_PRODUTO, Request.ADMIN_PESQUISA_PRODUTO, Request.ADMIN_PESQUISA_USUARIO,
				Request.ADMIN_CADASTRA_PROMO, Request.ADMIN_CADASTRA_LIVRO };
		for (Request r : requests)
			microControladores.put(r, this);
	}

	@Override
	public void dispatchRequest(Request request, HashMap<String, Object> hashMap) throws NegocioException {
		switch (request) {
		case ADMIN_ABRIR_PESQUISA:
			abrePesquisa(hashMap);
			break;
		case ADMIN_ABRIR_TELA_REGISTRAR:
			abreRegistrar(hashMap);
			break;
		case ADMIN_SAIR:
			sair(hashMap);
			break;
		case ADMIN_DESATIVA_USUARIO:
			desativaUsuario(hashMap);
			break;
		case ADMIN_ATIVA_USUARIO:
			ativaUsuario(hashMap);
			break;
		case ADMIN_REMOVE_PRODUTO:
			removeProduto(hashMap);
			break;
		case ADMIN_PESQUISA_PRODUTO:
			pesquisaProduto(hashMap);
			break;
		case ADMIN_PESQUISA_USUARIO:
			pesquisaUsuario(hashMap);
			break;
		case ADMIN_CADASTRA_PROMO:
			cadastraPromocao(hashMap);
			break;
		case ADMIN_CADASTRA_LIVRO:
			cadastraLivro(hashMap);
		}
	}

	public void cadastraLivro(HashMap<String, Object> hashMap) {
		// TODO Auto-generated method stub
		
	}

	public void cadastraPromocao(HashMap<String, Object> hashMap) {
		
	}

	public void pesquisaUsuario(HashMap<String, Object> hashMap) throws NegocioException {
		String termoPesquisa = (String) hashMap.get("termoPesquisa");
		
		List<Usuario> resultados = (List<Usuario>) fachadaRegrasNegocio.buscaUsuariosPorNome(termoPesquisa);
		
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("livros", resultados);
		dispatcher.dispatch(DispatchResponse.ADMIN_PESQUISA, params);
	}

	public void pesquisaProduto(HashMap<String, Object> hashMap) throws NegocioException {
		String termoPesquisa = (String) hashMap.get("termoPesquisa");
		
		List<Livro> resultados = (List<Livro>) fachadaRegrasNegocio.buscaLivrosPorNome(termoPesquisa);
		
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("livros", resultados);
		dispatcher.dispatch(DispatchResponse.ADMIN_PESQUISA, params);
	}

	public void removeProduto(HashMap<String, Object> hashMap) throws NegocioException {
		int codigoProduto = (Integer) hashMap.get("codigoProduto");
		fachadaRegrasNegocio.removeLivro(codigoProduto);
		
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("mensagem", "Usuario removido com sucesso!");
		dispatcher.dispatch(DispatchResponse.MENSAGEM_SUCESSO, params);
	}

	public void ativaUsuario(HashMap<String, Object> hashMap) throws NegocioException {
		int codigoUsuario = (Integer) hashMap.get("codigoUsuario");
		fachadaRegrasNegocio.ativaUsuario(codigoUsuario);
		
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("mensagem", "Usuario ativado com sucesso!");
		dispatcher.dispatch(DispatchResponse.MENSAGEM_SUCESSO, params);
	}

	public void desativaUsuario(HashMap<String, Object> hashMap) throws NegocioException {
		int codigoUsuario = (Integer) hashMap.get("codigoUsuario");
		fachadaRegrasNegocio.desativaUsuario(codigoUsuario);
		
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("mensagem", "Usuario desativado com sucesso!");
		dispatcher.dispatch(DispatchResponse.MENSAGEM_SUCESSO, params);
	}

	public void sair(HashMap<String, Object> hashMap) {
		dispatcher.dispatch(DispatchResponse.LOGIN);
	}

	public void abreRegistrar(HashMap<String, Object> hashMap) {
		dispatcher.dispatch(DispatchResponse.ADMIN_REGISTRO, hashMap);
	}

	public void abrePesquisa(HashMap<String, Object> hashMap) {
		dispatcher.dispatch(DispatchResponse.ADMIN_PESQUISA, hashMap);
	}
}
