package negocio;

import java.sql.Date;
import java.util.List;

import basedados.FachadaBaseDados;
import cartoes.Cartao;
import cartoes.GerenciadorCartoes;
import dto.Aluguel;
import dto.Livro;
import dto.Promocao;
import dto.Usuario;
import dto.Venda;
import negocio.RegrasNegocioPromocao.PromocaoCalculada;

public class FachadaRegrasNegocio {
	private FachadaBaseDados fachadaBaseDados;
	
	private RegrasNegocioUsuario regrasNegocioUsuario;
	private RegrasNegocioLivro regrasNegocioLivro;
	private RegrasNegocioPromocao regrasNegocioPromocao;
	private RegrasNegocioVenda regrasNegocioVenda;
	private RegrasNegocioAluguel regrasNegocioAluguel;
	private RegrasNegocioCartaoClube regrasNegocioCartaoClube;

	public FachadaRegrasNegocio(FachadaBaseDados db) {
		this.fachadaBaseDados = db;
		
		regrasNegocioUsuario = new RegrasNegocioUsuario(fachadaBaseDados);
		regrasNegocioLivro = new RegrasNegocioLivro(fachadaBaseDados);
		regrasNegocioPromocao = new RegrasNegocioPromocao(fachadaBaseDados);
		regrasNegocioVenda = new RegrasNegocioVenda(fachadaBaseDados);
		regrasNegocioAluguel = new RegrasNegocioAluguel(fachadaBaseDados);
		regrasNegocioCartaoClube = new RegrasNegocioCartaoClube(fachadaBaseDados);
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

	public void cadastraPromocao(int codigo, int novoPreco) throws NegocioException {
		regrasNegocioPromocao.cadastraPromocao(codigo, novoPreco);
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
		return regrasNegocioVenda.listaVendas();
	}

	///////////////////////////////LISTA ALUGUEIS///////////////////////////////////////////////////////////////////////////
	public List<Aluguel> listaAlugueis() throws NegocioException {
		return regrasNegocioAluguel.listaAlugueis();
	}

	///////////////////////////////BUSCA USUARIO POR NOME EXATO///////////////////////////////////////////////////////////////////////////
	public Usuario buscaUsuarioPorNomeExato(String nome) throws NegocioException {
		return regrasNegocioUsuario.buscaUsuarioPorNomeExato(nome);
	}
	///////////////////////////////BUSCA PROMOCAO///////////////////////////////////////////////////////////////////////////
	public Promocao buscaPromocao(int codigoLivro) throws NegocioException {
		return regrasNegocioPromocao.buscaPromocao(codigoLivro);
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
		return regrasNegocioVenda.buscaVenda(codigo);
	}

	////////////// BUSCA ALUGUEL ////////////////////
	public Aluguel buscaAluguel(int codigo) throws NegocioException {
		return regrasNegocioAluguel.buscaAluguel(codigo);
	}

	public void vendeLivroCartaoClube(Usuario usuario, Livro livro, int preco) throws NegocioException {
		regrasNegocioVenda.vendeLivroCartaoClube(usuario, livro, preco);
	}

	public void vendeLivroCartaoNormal(GerenciadorCartoes gerenciadorCartoes, Cartao cartao, Usuario usuario, Livro livro, int preco) throws NegocioException {
		regrasNegocioVenda.vendeLivroCartaoNormal(gerenciadorCartoes, cartao, usuario, livro, preco);
	}

	public void insereCreditoCartaoClube(GerenciadorCartoes gerenciadorCartoes, Cartao cartao, Usuario usuario, int valor) throws NegocioException {
		regrasNegocioCartaoClube.insereCreditoCartaoClube(gerenciadorCartoes, cartao, usuario, valor);
	}

	public List<Venda> buscaVendasDoUsuario(int codigoUsuario) throws NegocioException {
		return regrasNegocioVenda.buscaVendasDoUsuario(codigoUsuario);
	}

	public List<Aluguel> buscaAlugueisDoUsuario(int codigoUsuario) throws NegocioException {
		return regrasNegocioAluguel.buscaAlugueisDoUsuario(codigoUsuario);
	}

	public void desativaUsuario(int codigoUsuario) throws NegocioException {
		regrasNegocioUsuario.desativaUsuario(codigoUsuario);
	}

	public void ativaUsuario(int codigoUsuario) throws NegocioException {
		regrasNegocioUsuario.ativaUsuario(codigoUsuario);
	}

	public void removePromocao(int codigoLivro) throws NegocioException {
		regrasNegocioPromocao.removePromocao(codigoLivro);
	}

	public void removeLivro(int codigoLivro) throws NegocioException {
		regrasNegocioLivro.removeLivro(codigoLivro);
	}

	public List<Livro> listaLivrosRecentes(int limite) throws NegocioException {
		return regrasNegocioLivro.listaLivrosRecentes(limite);
	}
	
	public PromocaoCalculada calcularValorEmPromocao(Livro livro, Usuario usuario) throws NegocioException {
		return regrasNegocioPromocao.calcularValorEmPromocao(livro, usuario);
	}
	
	public int calculaPrecoDoAluguel(Livro livro) {
		return regrasNegocioAluguel.calculaPrecoDoAluguel(livro);
	}
	
	public void realizaAluguel(Livro livro, Usuario usuario) throws NegocioException {
		regrasNegocioAluguel.realizaAluguel(livro, usuario);
	}
	
	public void reativaUsuarioNoLogin(Usuario usuario) throws NegocioException {
		regrasNegocioUsuario.reativaUsuarioNoLogin(usuario);
	}
	
	public void atualizaTempoDeUltimoLogin(Usuario usuario) throws NegocioException {
		regrasNegocioUsuario.atualizaTempoDeUltimoLogin(usuario);
	}
}
