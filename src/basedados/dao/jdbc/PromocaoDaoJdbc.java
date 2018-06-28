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
		preparaComandoSQL("INSERT INTO Promocao (codigo, preco)" + "VALUES (?, ?)");

		try {
			pstmt.setInt(1, promocao.getCodigo());
			pstmt.setInt(2, promocao.getPreco());
			pstmt.execute();
		} catch (SQLException e) {
			Log.gravaLog(e);
			throw new BaseDadosException("Erro ao setar os par√¢metros da consulta.");
		}

		fechaConexao();
	}

	@Override
	public Promocao busca(int codigoLivro) throws BaseDadosException {
		abreConexao();
		preparaComandoSQL("select preco from Promocao where codigo =" + codigoLivro);
		Livro livro = null;
		Promocao promo = null;
		try {
			rs = pstmt.executeQuery();

			if (rs.next()) {
				int preco = rs.getInt(1);
				promo = new Promocao(codigoLivro, preco);
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
		abreConexao();
		preparaComandoSQL("select codigo, preco from Promocao");

		try {
			rs = pstmt.executeQuery();

			while (rs.next()) {
				int codigo = rs.getInt(1);
				int preco = rs.getInt(2);
				Promocao promocao = new Promocao(codigo, preco);

				promocoes.add(promocao);
			}
		} catch (SQLException e) {
			Log.gravaLog(e);
			throw new BaseDadosException("Problemas ao ler o resultado da consulta.");
		}
		fechaConexao();

		return promocoes;
	}

	@Override
	public void remove(int codigoPromocao) throws BaseDadosException {
		abreConexao();
		preparaComandoSQL("delete from Promocao where codigo = ?");
		try {
			pstmt.setInt(1, codigoPromocao);
			pstmt.execute();
		}
		catch (SQLException e) {
			Log.gravaLog(e);
			throw new BaseDadosException("Problema na conex„o com o banco de dados.");
		}
	}
}
