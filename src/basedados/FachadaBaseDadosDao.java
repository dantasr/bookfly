package basedados;

import java.sql.Date;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JOptionPane;

import basedados.dao.AluguelDao;
import basedados.dao.LivroDao;
import basedados.dao.PromocaoDao;
import basedados.dao.UsuarioDao;
import basedados.dao.VendaDao;
import basedados.dao.jdbc.AluguelDaoJdbc;
import basedados.dao.jdbc.LivroDaoJdbc;
import basedados.dao.jdbc.PromocaoDaoJdbc;
import basedados.dao.jdbc.UsuarioDaoJdbc;
import basedados.dao.jdbc.VendaDaoJdbc;
import controller.FrontController;
import dto.Aluguel;
import dto.Livro;
import dto.Promocao;
import dto.Usuario;
import dto.Venda;
import utilidades.Log;

public class FachadaBaseDadosDao implements FachadaBaseDados {

	private AluguelDao aluguelDao;
	private LivroDao livroDao;
	private PromocaoDao promocaoDao;
	private UsuarioDao usuarioDao;
	private VendaDao vendaDao;

	public FachadaBaseDadosDao() throws BaseDadosException {
		livroDao = new LivroDaoJdbc();
		usuarioDao = new UsuarioDaoJdbc();
		aluguelDao = new AluguelDaoJdbc(livroDao, usuarioDao);
		promocaoDao = new PromocaoDaoJdbc(livroDao);
		vendaDao = new VendaDaoJdbc(livroDao, usuarioDao);
	}

	/////////////////////////////INSERE USUARIO NO BD///////////////////////////////////////////////////////////////////////////
	public void insereUsuario(Usuario usuario) throws BaseDadosException {
		usuarioDao.insere(usuario);
	}

	///////////////////////////INSERE LIVRO NO BD///////////////////////////////////////////////////////////////////////////
	public void insereLivro(Livro livro) throws BaseDadosException {
		livroDao.insere(livro);
	}

	/////////////////////////////INSERE PROMOCAO NO BD///////////////////////////////////////////////////////////////////////////
	public void inserePromocao(Promocao promocao) throws BaseDadosException {
		promocaoDao.insere(promocao);
	}

	/////////////////////////////INSERE	VENDA NO BD///////////////////////////////////////////////////////////////////////////
	public void insereVenda(Venda venda) throws BaseDadosException {
		vendaDao.insere(venda);
	}

	/////////////////////////////INSERE VENDA NO BD///////////////////////////////////////////////////////////////////////////
	public void insereAluguel(Aluguel aluguel) throws BaseDadosException {
		aluguelDao.insere(aluguel);
	}

	////////////////////////////BUSCA ALUGUEL NO BD///////////////////////////////////////////////////////////////////////////
	public Aluguel buscaAluguel(int codigo) throws BaseDadosException {
		return aluguelDao.busca(codigo);
	}

	/////////////////////////////BUSCA LIVRO NO BD///////////////////////////////////////////////////////////////////////////
	public Livro buscaLivro(int codigo) throws BaseDadosException {
		return livroDao.busca(codigo);
	}
	
	/////////////////////////////BUSCA PROMOCAO NO BD///////////////////////////////////////////////////////////////////////////
	public Promocao buscaPromocao(int codigo) throws BaseDadosException {
		return promocaoDao.busca(codigo);
	}

	/////////////////////////////BUSCA USUARIO NO BD///////////////////////////////////////////////////////////////////////////
	public Usuario buscaUsuario(int codigo) throws BaseDadosException {
		return usuarioDao.busca(codigo);
	}

	/////////////////////////////LISTA USUARIOS DO BD///////////////////////////////////////////////////////////////////////////
	public List<Usuario> listaUsuarios() throws BaseDadosException {
		return usuarioDao.listaTodos();
	}

	/////////////////////////////LISTA LIVROS DO BD///////////////////////////////////////////////////////////////////////////
	public List<Livro> listaLivros() throws BaseDadosException {
		return livroDao.listaTodos();
	}

	/////////////////////////////LISTA PROMOCOES DO BD///////////////////////////////////////////////////////////////////////////
	@Override
	public List<Promocao> listaPromocoes() throws BaseDadosException {
		return promocaoDao.listaTodos();
	}

	/////////////////////////////LISTA VENDAS DO BD///////////////////////////////////////////////////////////////////////////
	@Override
	public List<Venda> listaVendas() throws BaseDadosException {
		return vendaDao.listaTodos();
	}

	/////////////////////////////LISTA ALUGUEIS DO BD///////////////////////////////////////////////////////////////////////////
	@Override
	public List<Aluguel> listaAlugueis() throws BaseDadosException {
		return aluguelDao.listaTodos();
	}

	/////////////////////////////BUSCA VENDAS DO BD///////////////////////////////////////////////////////////////////////////
	@Override
	public Venda buscaVenda(int codigo) throws BaseDadosException {
		return vendaDao.busca(codigo);
	}


	/////////////////////////////BUSCA USUARIO POR NOME EXATO NO BD///////////////////////////////////////////////////////////////////////////
	public Usuario buscaUsuarioPorNomeExato(String nome) throws BaseDadosException {
		return usuarioDao.buscaPorNomeExato(nome);
	}
	
	/////////////////////////////BUSCA LIVRO POR NOME EXATO NO BD///////////////////////////////////////////////////////////////////////////
	public Livro buscaLivroPorNomeExato(String nome) throws BaseDadosException {
		return livroDao.buscaLivroPorNomeExato(nome);
	}

	/////////////////////////////BUSCA USUARIOS POR NOME NO BD///////////////////////////////////////////////////////////////////////////
	@Override
	public List<Usuario> buscaUsuariosPorNome(String termo) throws BaseDadosException {
		return usuarioDao.buscaPorNome(termo);
	}

	/////////////////////////////BUSCA LIVROS POR NOME NO BD///////////////////////////////////////////////////////////////////////////
	@Override
	public List<Livro> buscaLivrosPorNome(String termo) throws BaseDadosException {
		return livroDao.buscaLivrosPorNome(termo);
	}
	
	public void alteraUsuario(Usuario usuario) throws BaseDadosException {
		usuarioDao.altera(usuario);
	}
	
	public List<Venda> buscaVendasDoUsuario(int codigoUsuario) throws BaseDadosException {
		return vendaDao.buscaDoUsuario(codigoUsuario);
	}
	
	public List<Aluguel> buscaAlugueisDoUsuario(int codigoUsuario) throws BaseDadosException {
		return aluguelDao.buscaDoUsuario(codigoUsuario);
	}
	
	public void removeUsuario(int codigoUsuario) throws BaseDadosException {
		usuarioDao.remove(codigoUsuario);
	}
	
	public void removeLivro(int codigoLivro) throws BaseDadosException {
		livroDao.remove(codigoLivro);
	}
	
	public void removePromocao(int codigoPromocao) throws BaseDadosException {
		promocaoDao.remove(codigoPromocao);
	}
	
	public List<Livro> listaLivrosRecentes(int limite) throws BaseDadosException {
		return livroDao.listaLivrosRecentes(limite);
	}

	@Override
	public List<Promocao> buscaPromocaoLista(int codigoPromocao) throws BaseDadosException {
		return promocaoDao.buscaLista(codigoPromocao);
	}
}
