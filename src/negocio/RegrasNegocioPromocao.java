package negocio;

import utilidades.Log;
import dto.Promocao;

import java.util.List;

import basedados.BaseDadosException;
import basedados.FachadaBaseDados;
import controller.FrontController;

public class RegrasNegocioPromocao {
	private FachadaBaseDados fachadaBaseDados;
	
	public RegrasNegocioPromocao(FachadaBaseDados fachadaBaseDados) {
		this.fachadaBaseDados = fachadaBaseDados;
	}
	
	public void cadastraPromocao(int codigo,FrontController frontController, int novoPreco) throws NegocioException {
		try {
			Promocao promocao = new Promocao(codigo,frontController.getGerenciadorRegrasNegocio().buscaLivro(codigo),novoPreco);
			fachadaBaseDados.inserePromocao(promocao);
		} catch (BaseDadosException e) {
			Log.gravaLog(e);
			throw new NegocioException("Problemas no acesso ao banco de dados.");
		}
	}
	
	public Promocao buscaPromocao(int codigoLivro) throws NegocioException {
		try {
			return fachadaBaseDados.buscaPromocao(codigoLivro);
		} catch (BaseDadosException e) {
			Log.gravaLog(e);
			throw new NegocioException("Problemas no acesso ao banco de dados.");
		}
	}
	
	public List<Promocao> buscaPromocaoLista(int codigoPromocao) throws NegocioException {
		try {
			return fachadaBaseDados.buscaPromocaoLista(codigoPromocao);
		} catch (BaseDadosException e) {
			Log.gravaLog(e);
			throw new NegocioException("Problemas no acesso ao banco de dados.");
		}
	}
	
	public void removePromocao(int codigoLivro) throws NegocioException {
		try {
			fachadaBaseDados.removePromocao(codigoLivro);
		} catch (BaseDadosException e) {
			Log.gravaLog(e);
			throw new NegocioException("Problemas no acesso ao banco de dados.");
		}
	}
}
