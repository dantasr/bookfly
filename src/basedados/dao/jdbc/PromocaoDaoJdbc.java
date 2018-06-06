package basedados.dao.jdbc;

import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import basedados.BaseDadosException;
import basedados.dao.LivroDao;
import basedados.dao.PromocaoDao;
import dto.Livro;
import dto.Promocao;
import utilidades.Log;

public class PromocaoDaoJdbc extends ConectorDaoJdbc implements PromocaoDao {
	private LivroDao livroDao;
	public PromocaoDaoJdbc(LivroDao livroDao) throws BaseDadosException {
		super();
		this.livroDao = livroDao;
		// TODO Auto-generated constructor stub
	}

	@Override
	public void insere(Promocao promocao) throws BaseDadosException {
		abreConexao();
		preparaComandoSQL("INSERT INTO Promocao (codigoLivro,preco)" + "VALUES (?, ?)");

		try {
			pstmt.setInt(1, promocao.getLivro().getCodigo());
			pstmt.setInt(2, promocao.getPreco());
			pstmt.execute();
		} catch (SQLException e) {
			Log.gravaLog(e);
			throw new BaseDadosException("Erro ao setar os parÃ¢metros da consulta.");
		}

		fechaConexao();
	}

	@Override
	public Promocao busca(int codigoPromocao) throws BaseDadosException {
		abreConexao();
		preparaComandoSQL("select preco from Promocao where codigo =" + codigoPromocao);
		Livro livro = null;
		Promocao promo = null;
		try {
			rs = pstmt.executeQuery();

			if (rs.next()) {
				int preco = rs.getInt(1);
				livro = livroDao.busca(codigoPromocao);
				promo = new Promocao(codigoPromocao, livro, preco);
			}
		} catch (SQLException e) {
			Log.gravaLog(e);
			throw new BaseDadosException("Problemas ao ler o resultado da consulta.");
		}

		fechaConexao();
		return promo;
	}

	@Override
	public List<Promocao> listaTodos() throws BaseDadosException {
		LinkedList<Promocao> promocoes = new LinkedList<Promocao>();
		// Usar essa lista pra evitar o erro do código do Freire.
		LinkedList<Integer> codigoLivroDasPromocoes = new LinkedList<Integer>();
		abreConexao();
		preparaComandoSQL("select codigo, codigoLivro, preco from Promocao");

		try {
			rs = pstmt.executeQuery();

			while (rs.next()) {
				int codigo = rs.getInt(1);
				int codigoLivro = rs.getInt(2);
				int preco = rs.getInt(3);
				Promocao promocao = new Promocao(codigo, null, preco);

				promocoes.add(promocao);
				codigoLivroDasPromocoes.add(codigoLivro);
			}
		} catch (SQLException e) {
			Log.gravaLog(e);
			throw new BaseDadosException("Problemas ao ler o resultado da consulta.");
		}
		fechaConexao();

		// Buscar os livros pra colocar em cada Promoção
		for (int i = 0; i < codigoLivroDasPromocoes.size(); i++) {
			Promocao promocao = promocoes.get(i);
			int codigoLivro = codigoLivroDasPromocoes.get(i);

			promocao.setLivro(livroDao.busca(codigoLivro));
		}

		return promocoes;
	}

	@Override
	public void remove(int codigoPromocao) throws BaseDadosException {
		abreConexao();
		preparaComandoSQL("delete from Promocao where codigoLivro = ?");
		try {
			pstmt.setInt(1, codigoPromocao);
			pstmt.execute();
		}
		catch (SQLException e) {
			Log.gravaLog(e);
			throw new BaseDadosException("Problema na conexão com o banco de dados.");
		}
	}

	@Override
	public List<Promocao> buscaLista(int codigoPromocao) throws BaseDadosException {
		LinkedList<Promocao> promo = new LinkedList<Promocao>();
		abreConexao();
		// Recentes por inclusão ou recentes por lancamento?
		preparaComandoSQL("select codigo from Promocao where codigoLivro =" + codigoPromocao);

		List<Integer> codigoPromocoes = new LinkedList<Integer>();

		try {
			rs = pstmt.executeQuery();

			while (rs.next()) {				
				int codigo = rs.getInt(1);
				codigoPromocoes.add(codigo);
			}
		} catch (SQLException e) {
			throw new BaseDadosException("Erro no acesso ao banco de dados!");
		}

		for (Integer codigo : codigoPromocoes) {
			promo.add(busca(codigo));
		}

		fechaConexao();
		return promo;
	}
}
