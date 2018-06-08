package negocio;

import java.sql.Date;
import java.util.List;

import utilidades.Log;
import dto.Livro;
import basedados.BaseDadosException;
import basedados.FachadaBaseDados;

public class RegrasNegocioLivro {
	private FachadaBaseDados fachadaBaseDados;
	
	public RegrasNegocioLivro(FachadaBaseDados fachadaBaseDados) {
		this.fachadaBaseDados = fachadaBaseDados;
	}
	
	public void cadastraLivro(int codigo, String nome, String autor, String editora, int preco, Date dataLancamento) throws NegocioException {
		Livro livro = new Livro(codigo, nome, autor, editora, preco, dataLancamento);
		try {
			fachadaBaseDados.insereLivro(livro);
		} catch (BaseDadosException e) {
			Log.gravaLog(e);
			throw new NegocioException("Problemas no acesso ao banco de dados.");
		}
	}
	
	public void cadastraLivro(int codigo, String nome, String autor, String editora, int preco, int dia, int mes, int ano) throws NegocioException {
		Date dataLancamento = new Date(ano, mes, dia);
		cadastraLivro(codigo, nome, autor, editora, preco, dataLancamento);
	}
	
	public List<Livro> listaLivros() throws NegocioException {
		try {
			return fachadaBaseDados.listaLivros();
		} catch (BaseDadosException e) {
			Log.gravaLog(e);
			throw new NegocioException("Problemas no acesso ao banco de dados.");
		}
	}
	
	public Livro buscaLivroPorNomeExato(String nome) throws NegocioException {
		try {
			return fachadaBaseDados.buscaLivroPorNomeExato(nome);
		} catch (BaseDadosException e) {
			Log.gravaLog(e);
			throw new NegocioException("Problemas no acesso ao banco de dados.");
		}
	}

	///////////////////////////////BUSCA LIVRO POR NOME///////////////////////////////////////////////////////////////////////////
	public List<Livro> buscaLivrosPorNome(String nome) throws NegocioException {
		try {
			return fachadaBaseDados.buscaLivrosPorNome(nome);
		} catch (BaseDadosException e) {
			Log.gravaLog(e);
			throw new NegocioException("Problemas no acesso ao banco de dados.");
		}
	}
	
	public Livro buscaLivro(int codigo) throws NegocioException {
		try {
			return fachadaBaseDados.buscaLivro(codigo);
		} catch (BaseDadosException e) {
			Log.gravaLog(e);
			throw new NegocioException("Problemas no acesso ao banco de dados.");
		}
	}
	
	public void removeLivro(int codigoLivro) throws NegocioException {
		try {
			fachadaBaseDados.removeLivro(codigoLivro);
		} catch (BaseDadosException e) {
			Log.gravaLog(e);
			throw new NegocioException("Problemas no acesso ao banco de dados.");
		}
	}

	public List<Livro> listaLivrosRecentes(int limite) throws NegocioException {
		try {
			return fachadaBaseDados.listaLivrosRecentes(limite);
		} catch (BaseDadosException e) {
			Log.gravaLog(e);
			throw new NegocioException("Problemas no acesso ao banco de dados.");
		}
	}
}
