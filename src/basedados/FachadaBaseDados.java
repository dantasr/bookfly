package basedados;

import java.util.List;

import dto.Aluguel;
import dto.Livro;
import dto.Promocao;
import dto.Usuario;
import dto.Venda;

public interface FachadaBaseDados {
	// INSERÇÕES
	public void insereUsuario(Usuario usuario) throws BaseDadosException;
	public void insereLivro(Livro livro) throws BaseDadosException;
	public void inserePromocao(Promocao cd) throws BaseDadosException;
	public void insereVenda(Venda venda) throws BaseDadosException;
	public void insereAluguel(Aluguel aluguel) throws BaseDadosException;

	// BUSCAS
	public Venda buscaVenda(int codigo) throws BaseDadosException;
	public Livro buscaLivro(int codigo) throws BaseDadosException;
	public Usuario buscaUsuario(int codigoUsuario) throws BaseDadosException;
	public Promocao buscaPromocao(int codigoPromocao) throws BaseDadosException;
	public Aluguel buscaAluguel(int codigoAluguel) throws BaseDadosException;

	// LISTAGENS
	public List<Usuario> listaUsuarios() throws BaseDadosException;
	public List<Livro> listaLivros() throws BaseDadosException;
	public List<Venda> listaVendas() throws BaseDadosException;
	public List<Promocao> listaPromocoes() throws BaseDadosException;
	public List<Aluguel> listaAlugueis() throws BaseDadosException;
	
	// ALTERACOES
	public void alteraUsuario(Usuario usuario) throws BaseDadosException;
	
	// REMOCOES
	public void removeUsuario(int codigoUsuario) throws BaseDadosException;
	public void removeLivro(int codigoLivro) throws BaseDadosException;
	public void removePromocao(int codigoLivro) throws BaseDadosException;
	
	// OUTRAS
	public Usuario buscaUsuarioPorNomeExato(String nome) throws BaseDadosException;
	public List<Livro> buscaLivrosPorNome(String livro) throws BaseDadosException;
	public Livro buscaLivroPorNomeExato(String nome) throws BaseDadosException;
	public List<Usuario> buscaUsuariosPorNome(String termo) throws BaseDadosException;
	public List<Venda> buscaVendasDoUsuario(int codigoUsuario) throws BaseDadosException;
	public List<Aluguel> buscaAlugueisDoUsuario(int codigoUsuario) throws BaseDadosException;
	public List<Livro> listaLivrosRecentes(int limite) throws BaseDadosException;
}
