package basedados.dao;

import java.util.List;

import basedados.BaseDadosException;
import dto.Livro;

public interface LivroDao {
	public void insere(Livro livro) throws BaseDadosException;
	public Livro busca(int codigoLivro)throws BaseDadosException;
	public List<Livro> listaTodos()throws BaseDadosException;
	public void remove(int codigoLivro)throws BaseDadosException;
	public List<Livro> buscaLivrosPorNome(String livro) throws BaseDadosException;
	public Livro buscaLivroPorNomeExato(String nome) throws BaseDadosException;
	public List<Livro> listaLivrosRecentes(int limite) throws BaseDadosException;
}
