package controller;

import java.io.File;
import java.io.IOException;
import java.sql.Date;
import java.util.HashMap;
import java.util.List;

import javax.swing.JOptionPane;

import arquivos.GerenciadorArquivos;
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
				Request.ADMIN_CADASTRA_PROMO, Request.ADMIN_EXIBE_TELA_CADASTRA_PROMOCAO, Request.ADMIN_CADASTRA_LIVRO, Request.ADMIN_REMOVE_PROMO };
		for (Request r : requests)
			microControladores.put(r, this);
	}

	@Override
	public void dispatchRequest(Request request, Pedido hashMap) throws NegocioException {
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
		case ADMIN_EXIBE_TELA_CADASTRA_PROMOCAO:
			abreCadastraPromocao(hashMap);
			break;
		case ADMIN_CADASTRA_LIVRO:
			cadastraLivro(hashMap);
			break;
		case ADMIN_REMOVE_PROMO:
			removePromo(hashMap);
			break;
		}
	}

	private void removePromo(Pedido hashMap) throws NegocioException {
		int codigo = (int) hashMap.get("codigoLivro");
		fachadaRegrasNegocio.removePromocao(codigo);
		exibirSucesso(hashMap, "Deletado com sucesso!");
		// TODO Auto-generated catch block
	}

	public void abreCadastraPromocao(Pedido hashMap) {
		int codigo = (int) hashMap.get("codigoLivro");
		Pedido novo = Pedido.criarNovoPedido(hashMap);
		novo.put("codigoLivro", codigo);
		dispatcher.dispatch(DispatchResponse.ADMIN_PROMOCAO, novo);
	}

	public void cadastraLivro(Pedido hashMap) throws NegocioException {	
		int codigoLivro = (int)hashMap.get("codigoLivro");
		String titulo = (String)hashMap.get("titulo");
		String autor = (String)hashMap.get("autor");
		String editora = (String)hashMap.get("editora");
		int preco = (int)hashMap.get("preco");
		Date data = (Date)hashMap.get("data");
		File campoPdf = (File)hashMap.get("campoPdf");
		File campoImagem = (File)hashMap.get("campoImagem");
		fachadaRegrasNegocio.cadastraLivro(codigoLivro, titulo, autor, editora, preco, data);
		GerenciadorArquivos gerenciadorArquivos = new GerenciadorArquivos();
		Livro livro = new Livro(codigoLivro, titulo, autor, editora, preco, data);
		try {
			gerenciadorArquivos.salvarImagemDoLivro(livro, campoImagem);
			gerenciadorArquivos.salvarArquivoPdf(livro, campoPdf);
			exibirSucesso(hashMap, "Livro cadastrado com sucesso!");
		} catch (IOException e) {
			exibirErro(hashMap, "Erro no cadastro do .pdf e/ou Imagem!");
		}
	}

	public void cadastraPromocao(Pedido hashMap) throws NegocioException {
		int codigoLivro = (int) hashMap.get("codigoLivro");
		int valorPromo = (int) hashMap.get("valorPromo");
		
		fachadaRegrasNegocio.cadastraPromocao(codigoLivro, valorPromo);
		exibirSucesso(hashMap, "Promoção cadastrada com sucesso!");
	}

	public void pesquisaUsuario(Pedido hashMap) throws NegocioException {
		String termoPesquisa = (String) hashMap.get("termoPesquisa");
		
		List<Usuario> resultados = (List<Usuario>) fachadaRegrasNegocio.buscaUsuariosPorNome(termoPesquisa);
		
		Pedido params = Pedido.criarNovoPedido(hashMap);
		params.put("livros", resultados);
		dispatcher.dispatch(DispatchResponse.ADMIN_PESQUISA, params);
	}

	public void pesquisaProduto(Pedido hashMap) throws NegocioException {
		String termoPesquisa = (String) hashMap.get("termoPesquisa");
		
		List<Livro> resultados = (List<Livro>) fachadaRegrasNegocio.buscaLivrosPorNome(termoPesquisa);
		
		Pedido params = Pedido.criarNovoPedido(hashMap);
		params.put("livros", resultados);
		dispatcher.dispatch(DispatchResponse.ADMIN_PESQUISA, params);
	}

	public void removeProduto(Pedido hashMap) throws NegocioException {
		int codigoProduto = (Integer) hashMap.get("codigoProduto");
		fachadaRegrasNegocio.removeLivro(codigoProduto);
		
		Pedido params = Pedido.criarNovoPedido(hashMap);
		params.put("mensagem", "Usuario removido com sucesso!");
		dispatcher.dispatch(DispatchResponse.MENSAGEM_SUCESSO, params);
	}

	public void ativaUsuario(Pedido hashMap) throws NegocioException {
		int codigoUsuario = (Integer) hashMap.get("codigoUsuario");
		fachadaRegrasNegocio.ativaUsuario(codigoUsuario);
		
		Pedido params = Pedido.criarNovoPedido(hashMap);
		params.put("mensagem", "Usuario ativado com sucesso!");
		dispatcher.dispatch(DispatchResponse.MENSAGEM_SUCESSO, params);
	}

	public void desativaUsuario(Pedido hashMap) throws NegocioException {
		int codigoUsuario = (Integer) hashMap.get("codigoUsuario");
		fachadaRegrasNegocio.desativaUsuario(codigoUsuario);
		
		Pedido params = Pedido.criarNovoPedido(hashMap);
		params.put("mensagem", "Usuario desativado com sucesso!");
		dispatcher.dispatch(DispatchResponse.MENSAGEM_SUCESSO, params);
	}

	public void sair(Pedido hashMap) {
		dispatcher.dispatch(DispatchResponse.LOGIN);
	}

	public void abreRegistrar(Pedido hashMap) {
		dispatcher.dispatch(DispatchResponse.ADMIN_REGISTRO, Pedido.criarNovoPedido(hashMap));
	}

	public void abrePesquisa(Pedido hashMap) {
		dispatcher.dispatch(DispatchResponse.ADMIN_PESQUISA, Pedido.criarNovoPedido(hashMap));
	}
}
