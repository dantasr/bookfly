package negocio;

import java.sql.Date;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JOptionPane;

import utilidades.Log;
import basedados.BaseDadosException;
import basedados.FachadaBaseDados;
import basedados.FachadaBaseDadosDao;
import cartoes.Cartao;
import cartoes.CartaoSemSaldoException;
import cartoes.GerenciadorCartoes;
import dto.Aluguel;
import dto.Livro;
import dto.Promocao;
import dto.Usuario;
import dto.Venda;
import main.Contexto;

public class GerenciadorRegrasNegocio {
	private FachadaBaseDados fachadaBaseDados;
	private RegrasNegocioUsuario regrasNegocioUsuario;
	private RegrasNegocioLivro regrasNegocioLivro;
	private RegrasNegocioPromocao regrasNegocioPromocao;

	public GerenciadorRegrasNegocio(FachadaBaseDados db) {
		this.fachadaBaseDados = db;
		
		regrasNegocioUsuario = new RegrasNegocioUsuario(fachadaBaseDados);
		regrasNegocioLivro = new RegrasNegocioLivro(fachadaBaseDados);
		regrasNegocioPromocao = new RegrasNegocioPromocao(fachadaBaseDados);
	}

	public void cadastraUsuario(int codigo, String nome, Date dataNascimento, String telefone, String cpf,
			String senha) throws NegocioException {
		regrasNegocioUsuario.cadastraUsuario(codigo, nome, dataNascimento, telefone, cpf, senha);
	}
	
	public void cadastraUsuario(int codigo, String nome, int dia, int mes, int ano, String telefone, String cpf,
			String senha) throws NegocioException {
		regrasNegocioUsuario.cadastraUsuario(codigo, nome, dia, mes, ano, telefone, cpf, senha);
	}

	public void cadastraLivro(int codigo, String nome, String autor, String editora, int preco, Date dataLancamento) throws NegocioException {
		regrasNegocioLivro.cadastraLivro(codigo, nome, autor, editora, preco, dataLancamento);
	}
	
	public void cadastraLivro(int codigo, String nome, String autor, String editora, int preco, int dia, int mes, int ano) throws NegocioException {
		regrasNegocioLivro.cadastraLivro(codigo, nome, autor, editora, preco, dia, mes, ano);
	}

	public void cadastraPromocao(int codigo,Contexto contexto, int novoPreco) throws NegocioException {
		try {
			Promocao promocao = new Promocao(codigo,contexto.getGerenciadorRegrasNegocio().buscaLivro(codigo),novoPreco);
			fachadaBaseDados.inserePromocao(promocao);
		} catch (BaseDadosException e) {
			Log.gravaLog(e);
			throw new NegocioException("Problemas no acesso ao banco de dados.");
		}
	}

	///////////////////////////////LISTA USUARIOS///////////////////////////////////////////////////////////////////////////
	public List<Usuario> listaUsuarios() throws NegocioException {
		return regrasNegocioUsuario.listaUsuarios();
	}

	///////////////////////////////LISTA LIVROS///////////////////////////////////////////////////////////////////////////
	public List<Livro> listaLivros() throws NegocioException {
		return regrasNegocioLivro.listaLivros();
	}

	///////////////////////////////LISTA VENDAS///////////////////////////////////////////////////////////////////////////
	public List<Venda> listaVendas() throws NegocioException {
		try {
			return fachadaBaseDados.listaVendas();
		} catch (BaseDadosException e) {
			Log.gravaLog(e);
			throw new NegocioException("Problemas no acesso ao banco de dados.");
		}
	}

	///////////////////////////////LISTA ALUGUEIS///////////////////////////////////////////////////////////////////////////
	public List<Aluguel> listaAlugueis() throws NegocioException {
		try {
			return fachadaBaseDados.listaAlugueis();
		} catch (BaseDadosException e) {
			Log.gravaLog(e);
			throw new NegocioException("Problemas no acesso ao banco de dados.");
		}
	}

	///////////////////////////////BUSCA USUARIO POR NOME EXATO///////////////////////////////////////////////////////////////////////////
	public Usuario buscaUsuarioPorNomeExato(String nome) throws NegocioException {
		return regrasNegocioUsuario.buscaUsuarioPorNomeExato(nome);
	}
	///////////////////////////////BUSCA PROMOCAO///////////////////////////////////////////////////////////////////////////
	public Promocao buscaPromocao(int codigoLivro) throws NegocioException {
		try {
			return fachadaBaseDados.buscaPromocao(codigoLivro);
		} catch (BaseDadosException e) {
			Log.gravaLog(e);
			throw new NegocioException("Problemas no acesso ao banco de dados.");
		}
	}

	///////////////////////////////BUSCA LIVRO POR NOME EXATO///////////////////////////////////////////////////////////////////////////
	public Livro buscaLivroPorNomeExato(String nome) throws NegocioException {
		return regrasNegocioLivro.buscaLivroPorNomeExato(nome);
	}

	///////////////////////////////BUSCA LIVRO POR NOME///////////////////////////////////////////////////////////////////////////
	public List<Livro> buscaLivrosPorNome(String nome) throws NegocioException {
		return regrasNegocioLivro.buscaLivrosPorNome(nome);
	}

	///////////////////////////////BUSCA USUARIO POR NOME///////////////////////////////////////////////////////////////////////////
	public List<Usuario> buscaUsuariosPorNome(String nome) throws NegocioException {
		return regrasNegocioUsuario.buscaUsuariosPorNome(nome);
	}

	////////////// BUSCA USUARIO ////////////////////
	public Usuario buscaUsuario(int codigo) throws NegocioException {
		return regrasNegocioUsuario.buscaUsuario(codigo);
	}

	////////////// BUSCA LIVRO ////////////////////
	public Livro buscaLivro(int codigo) throws NegocioException {
		return regrasNegocioLivro.buscaLivro(codigo);
	}

	////////////// BUSCA VENDA ////////////////////
	public Venda buscaVenda(int codigo) throws NegocioException {
		try {
			return fachadaBaseDados.buscaVenda(codigo);
		} catch (BaseDadosException e) {
			Log.gravaLog(e);
			throw new NegocioException("Problemas no acesso ao banco de dados.");
		}
	}

	////////////// BUSCA ALUGUEL ////////////////////
	public Aluguel buscaAluguel(int codigo) throws NegocioException {
		try {
			return fachadaBaseDados.buscaAluguel(codigo);
		} catch (BaseDadosException e) {
			Log.gravaLog(e);
			throw new NegocioException("Problemas no acesso ao banco de dados.");
		}
	}

	public void vendeLivroCartaoClube(Usuario usuario, Livro livro, int preco) throws NegocioException {
		if (preco > usuario.getSaldoCartaoClube()) {
			throw new NegocioException("O usuario não possui saldo suficiente.");
		}

		usuario.setSaldoCartaoClube(usuario.getSaldoCartaoClube() - preco);
		try {
			fachadaBaseDados.alteraUsuario(usuario);
			Venda venda = new Venda(0, livro, usuario);
			fachadaBaseDados.insereVenda(venda);
		} catch (BaseDadosException e) {
			Log.gravaLog(e);
			throw new NegocioException("Problemas no acesso ao banco de dados.");
		}
	}

	public void vendeLivroCartaoNormal(GerenciadorCartoes gerenciadorCartoes, Cartao cartao, Usuario usuario, Livro livro, int preco) throws NegocioException {
		try {
			gerenciadorCartoes.realizarDebito(cartao, preco);
		} catch (CartaoSemSaldoException e) {
			Log.gravaLog(e);
			throw new NegocioException("Cartão não possui saldo!");
		} catch (BaseDadosException e) {
			Log.gravaLog(e);
			throw new NegocioException("Problemas na conexão com o banco de dados.");
		}

		try {
			Venda venda = new Venda(0, livro, usuario);
			fachadaBaseDados.insereVenda(venda);
		} catch (BaseDadosException e) {
			Log.gravaLog(e);
			throw new NegocioException("Problemas no acesso ao banco de dados!");
		}
	}

	public void insereCreditoCartaoClube(GerenciadorCartoes gerenciadorCartoes, Cartao cartao, Usuario usuario, int valor) throws NegocioException {
		if (valor < 0)
			throw new NegocioException("Valor inválido!");

		try {
			gerenciadorCartoes.realizarDebito(cartao, valor);
		} catch (CartaoSemSaldoException e) {
			Log.gravaLog(e);
			throw new NegocioException("Cartão não possui saldo!");
		} catch (BaseDadosException e) {
			Log.gravaLog(e);
			throw new NegocioException("Problemas na conexão com o banco de dados.");
		}

		usuario.setSaldoCartaoClube(usuario.getSaldoCartaoClube() + valor);
		try {
			fachadaBaseDados.alteraUsuario(usuario);
		} catch (BaseDadosException e) {
			Log.gravaLog(e);
			throw new NegocioException("Problemas no acesso ao banco de dados!");
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

	public List<Venda> buscaVendasDoUsuario(int codigoUsuario) throws NegocioException {
		try {
			return fachadaBaseDados.buscaVendasDoUsuario(codigoUsuario);
		} catch (BaseDadosException e) {
			Log.gravaLog(e);
			throw new NegocioException("Problemas no acesso ao banco de dados.");
		}
	}

	public List<Aluguel> buscaAlugueisDoUsuario(int codigoUsuario) throws NegocioException {
		try {
			return fachadaBaseDados.buscaAlugueisDoUsuario(codigoUsuario);
		} catch (BaseDadosException e) {
			Log.gravaLog(e);
			throw new NegocioException("Problemas no acesso ao banco de dados.");
		}
	}

	public void desativaUsuario(int codigoUsuario) throws NegocioException {
		regrasNegocioUsuario.desativaUsuario(codigoUsuario);
	}


	public void ativaUsuario(int codigoUsuario) throws NegocioException {
		regrasNegocioUsuario.ativaUsuario(codigoUsuario);
	}

	public void removePromocao(int codigoLivro) throws NegocioException {
		try {
			fachadaBaseDados.removePromocao(codigoLivro);
		} catch (BaseDadosException e) {
			Log.gravaLog(e);
			throw new NegocioException("Problemas no acesso ao banco de dados.");
		}
	}

	public void removeLivro(int codigoLivro) throws NegocioException {
		regrasNegocioLivro.removeLivro(codigoLivro);
	}

	public List<Livro> listaLivrosRecentes(int limite) throws NegocioException {
		return regrasNegocioLivro.listaLivrosRecentes(limite);
	}
}
