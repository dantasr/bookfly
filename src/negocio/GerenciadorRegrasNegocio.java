package negocio;

import java.sql.Date;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JOptionPane;

import utilidades.Log;
import basedados.BaseDadosException;
import basedados.GerenciadorBaseDados;
import basedados.GerenciadorBaseDadosJDBC;
import beans.Aluguel;
import beans.Livro;
import beans.Promocao;
import beans.Usuario;
import beans.Venda;
import cartoes.Cartao;
import cartoes.CartaoSemSaldoException;
import cartoes.GerenciadorCartoes;
import main.Contexto;

public class GerenciadorRegrasNegocio {

	private GerenciadorBaseDados gerenciadorBaseDados;

	public GerenciadorRegrasNegocio(GerenciadorBaseDados db) {
		this.gerenciadorBaseDados = db;
	}

	///////////////////////////////CADASTRA USUARIO///////////////////////////////////////////////////////////////////////////
	public void cadastraUsuario(int codigo, String nome, Date dataNascimento, String telefone, String cpf,
			String senha) throws NegocioException {
		Usuario usuario = new Usuario(codigo, nome, dataNascimento, telefone, cpf, senha, 0, false, true);
		try {
			gerenciadorBaseDados.insereUsuario(usuario);
		} catch (BaseDadosException e) {
			Log.gravaLog(e);
			throw new NegocioException("Problemas no acesso ao banco de dados.");
		}
	}
	public void cadastraUsuario(int codigo, String nome, int dia, int mes, int ano, String telefone, String cpf,
			String senha) throws NegocioException {
		cadastraUsuario(codigo, nome, new Date(ano, mes, dia), telefone, cpf, senha);
	}

	///////////////////////////////CADASTRA LIVRO////////////////////////////////////////////////////////////////////////////
	public void cadastraLivro(int codigo, String nome, String autor, String editora, int preco, Date dataLancamento) throws NegocioException {
		Livro livro = new Livro(codigo, nome, autor, editora, preco, dataLancamento);
		try {
			gerenciadorBaseDados.insereLivro(livro);
		} catch (BaseDadosException e) {
			Log.gravaLog(e);
			throw new NegocioException("Problemas no acesso ao banco de dados.");
		}
	}
	public void cadastraLivro(int codigo, String nome, String autor, String editora, int preco, int dia, int mes, int ano) throws NegocioException {
		Date dataLancamento = new Date(ano, mes, dia);
		cadastraLivro(codigo, nome, autor, editora, preco, dataLancamento);
	}

	///////////////////////////////CADASTRA PROMOCAO////////////////////////////////////////////////////////////////////////////
	public void cadastraPromocao(int codigo,Contexto contexto, int novoPreco) throws NegocioException {
		try {
			Promocao promocao = new Promocao(codigo,contexto.getGerenciadorRegrasNegocio().buscaLivro(codigo),novoPreco);
			gerenciadorBaseDados.inserePromocao(promocao);
		} catch (BaseDadosException e) {
			Log.gravaLog(e);
			throw new NegocioException("Problemas no acesso ao banco de dados.");
		}
	}

	///////////////////////////////LISTA USUARIOS///////////////////////////////////////////////////////////////////////////
	public List<Usuario> listaUsuarios() throws NegocioException {
		try {
			return gerenciadorBaseDados.listaUsuarios();
		} catch (BaseDadosException e) {
			Log.gravaLog(e);
			throw new NegocioException("Problemas no acesso ao banco de dados.");
		}
	}

	///////////////////////////////LISTA LIVROS///////////////////////////////////////////////////////////////////////////
	public List<Livro> listaLivros() throws NegocioException {
		try {
			return gerenciadorBaseDados.listaLivros();
		} catch (BaseDadosException e) {
			Log.gravaLog(e);
			throw new NegocioException("Problemas no acesso ao banco de dados.");
		}
	}

	///////////////////////////////LISTA VENDAS///////////////////////////////////////////////////////////////////////////
	public List<Venda> listaVendas() throws NegocioException {
		try {
			return gerenciadorBaseDados.listaVendas();
		} catch (BaseDadosException e) {
			Log.gravaLog(e);
			throw new NegocioException("Problemas no acesso ao banco de dados.");
		}
	}

	///////////////////////////////LISTA ALUGUEIS///////////////////////////////////////////////////////////////////////////
	public List<Aluguel> listaAlugueis() throws NegocioException {
		try {
			return gerenciadorBaseDados.listaAlugueis();
		} catch (BaseDadosException e) {
			Log.gravaLog(e);
			throw new NegocioException("Problemas no acesso ao banco de dados.");
		}
	}

	///////////////////////////////BUSCA USUARIO POR NOME EXATO///////////////////////////////////////////////////////////////////////////
	public Usuario buscaUsuarioPorNomeExato(String nome) throws NegocioException {
		try {
			return gerenciadorBaseDados.buscaUsuarioPorNomeExato(nome);
		} catch (BaseDadosException e) {
			Log.gravaLog(e);
			throw new NegocioException("Problemas no acesso ao banco de dados.");
		}
	}
	///////////////////////////////BUSCA PROMOCAO///////////////////////////////////////////////////////////////////////////
	public Promocao buscaPromocao(int codigoLivro) throws NegocioException {
		try {
			return gerenciadorBaseDados.buscaPromocao(codigoLivro);
		} catch (BaseDadosException e) {
			Log.gravaLog(e);
			throw new NegocioException("Problemas no acesso ao banco de dados.");
		}
	}

	///////////////////////////////BUSCA LIVRO POR NOME EXATO///////////////////////////////////////////////////////////////////////////
	public Livro buscaLivroPorNomeExato(String nome) throws NegocioException {
		try {
			return gerenciadorBaseDados.buscaLivroPorNomeExato(nome);
		} catch (BaseDadosException e) {
			Log.gravaLog(e);
			throw new NegocioException("Problemas no acesso ao banco de dados.");
		}
	}



	///////////////////////////////BUSCA LIVRO POR NOME///////////////////////////////////////////////////////////////////////////
	public List<Livro> buscaLivrosPorNome(String nome) throws NegocioException {
		try {
			return gerenciadorBaseDados.buscaLivrosPorNome(nome);
		} catch (BaseDadosException e) {
			Log.gravaLog(e);
			throw new NegocioException("Problemas no acesso ao banco de dados.");
		}
	}

	///////////////////////////////BUSCA USUARIO POR NOME///////////////////////////////////////////////////////////////////////////
	public List<Usuario> buscaUsuariosPorNome(String nome) throws NegocioException {
		try {
			return gerenciadorBaseDados.buscaUsuariosPorNome(nome);
		} catch (BaseDadosException e) {
			Log.gravaLog(e);
			throw new NegocioException("Problemas no acesso ao banco de dados.");
		}
	}

	////////////// BUSCA USUARIO ////////////////////
	public Usuario buscaUsuario(int codigo) throws NegocioException {
		try {
			return gerenciadorBaseDados.buscaUsuario(codigo);
		} catch (BaseDadosException e) {
			Log.gravaLog(e);
			throw new NegocioException("Problemas no acesso ao banco de dados.");
		}
	}

	////////////// BUSCA LIVRO ////////////////////
	public Livro buscaLivro(int codigo) throws NegocioException {
		try {
			return gerenciadorBaseDados.buscaLivro(codigo);
		} catch (BaseDadosException e) {
			Log.gravaLog(e);
			throw new NegocioException("Problemas no acesso ao banco de dados.");
		}
	}

	////////////// BUSCA VENDA ////////////////////
	public Venda buscaVenda(int codigo) throws NegocioException {
		try {
			return gerenciadorBaseDados.buscaVenda(codigo);
		} catch (BaseDadosException e) {
			Log.gravaLog(e);
			throw new NegocioException("Problemas no acesso ao banco de dados.");
		}
	}

	////////////// BUSCA ALUGUEL ////////////////////
	public Aluguel buscaAluguel(int codigo) throws NegocioException {
		try {
			return gerenciadorBaseDados.buscaAluguel(codigo);
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
			gerenciadorBaseDados.alteraUsuario(usuario);
			Venda venda = new Venda(0, livro, usuario);
			gerenciadorBaseDados.insereVenda(venda);
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
			gerenciadorBaseDados.insereVenda(venda);
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
			gerenciadorBaseDados.alteraUsuario(usuario);
		} catch (BaseDadosException e) {
			Log.gravaLog(e);
			throw new NegocioException("Problemas no acesso ao banco de dados!");
		}
	}

	public List<Promocao> buscaPromocaoLista(int codigoPromocao) throws NegocioException {
		try {
			return gerenciadorBaseDados.buscaPromocaoLista(codigoPromocao);
		} catch (BaseDadosException e) {
			Log.gravaLog(e);
			throw new NegocioException("Problemas no acesso ao banco de dados.");
		}
	}

	public List<Venda> buscaVendasDoUsuario(int codigoUsuario) throws NegocioException {
		try {
			return gerenciadorBaseDados.buscaVendasDoUsuario(codigoUsuario);
		} catch (BaseDadosException e) {
			Log.gravaLog(e);
			throw new NegocioException("Problemas no acesso ao banco de dados.");
		}
	}

	public List<Aluguel> buscaAlugueisDoUsuario(int codigoUsuario) throws NegocioException {
		try {
			return gerenciadorBaseDados.buscaAlugueisDoUsuario(codigoUsuario);
		} catch (BaseDadosException e) {
			Log.gravaLog(e);
			throw new NegocioException("Problemas no acesso ao banco de dados.");
		}
	}

	public void desativaUsuario(int codigoUsuario) throws NegocioException {
		if (codigoUsuario == 1) {
			throw new NegocioException("Admin nao pode ser desativado.");
		}

		try {
			Usuario usuario = buscaUsuario(codigoUsuario);
			if (usuario.isAtivado()) {
				usuario.setAtivado(false);
				gerenciadorBaseDados.alteraUsuario(usuario);
			}
			else {
				throw new NegocioException("Usuario ja esta Desativado.");
			}
		} catch (BaseDadosException e) {
			Log.gravaLog(e);
			throw new NegocioException("Problemas no acesso ao banco de dados.");
		}
	}


	public void ativaUsuario(int codigoUsuario) throws NegocioException {
		try {
			Usuario usuario = buscaUsuario(codigoUsuario);
			if (usuario.isAtivado() == false) {
				usuario.setAtivado(true);
				gerenciadorBaseDados.alteraUsuario(usuario);
			}
			else {
				throw new NegocioException("Usuario ja esta Ativado.");
			}
		} catch (BaseDadosException e) {
			Log.gravaLog(e);
			throw new NegocioException("Problemas no acesso ao banco de dados.");
		}
	}

	public void removePromocao(int codigoLivro) throws NegocioException {
		try {
			gerenciadorBaseDados.removePromocao(codigoLivro);
		} catch (BaseDadosException e) {
			Log.gravaLog(e);
			throw new NegocioException("Problemas no acesso ao banco de dados.");
		}
	}

	public void removeLivro(int codigoLivro) throws NegocioException {
		try {
			gerenciadorBaseDados.removeLivro(codigoLivro);
		} catch (BaseDadosException e) {
			Log.gravaLog(e);
			throw new NegocioException("Problemas no acesso ao banco de dados.");
		}
	}

	public List<Livro> listaLivrosRecentes(int limite) throws NegocioException {
		try {
			return gerenciadorBaseDados.listaLivrosRecentes(limite);
		} catch (BaseDadosException e) {
			Log.gravaLog(e);
			throw new NegocioException("Problemas no acesso ao banco de dados.");
		}
	}
}
