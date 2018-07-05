package basedados.dao.jdbc;

import java.sql.Date;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import basedados.BaseDadosException;
import basedados.dao.LivroDao;
import dto.Livro;
import utilidades.Log;

public class LivroDaoJdbc extends ConectorDaoJdbc implements LivroDao {

	public LivroDaoJdbc() throws BaseDadosException {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public void insere(Livro livro) throws BaseDadosException {
		abreConexao();
		preparaComandoSQL("insert into Livro (codigo, nome, autor, editora, preco, dataLancamento) "
				+ "values (?, ?, ?, ?, ?, ?)");

		try {
			pstmt.setInt(1, livro.getCodigo());
			pstmt.setString(2, livro.getNome());
			pstmt.setString(3, livro.getAutor());
			pstmt.setString(4, livro.getEditora());
			pstmt.setInt(5, livro.getPreco());
			pstmt.setDate(6, livro.getDataLancamento());
			pstmt.execute();
		} catch (SQLException e) {
			Log.gravaLog(e);
			throw new BaseDadosException("Erro ao setar os parÃ¢metros da consulta.");
		}

		fechaConexao();
	}

	@Override
	public Livro busca(int codigo) throws BaseDadosException {
		abreConexao();
		preparaComandoSQL("select nome, autor, editora, preco, dataLancamento from Livro where codigo=" + codigo);
		Livro livro = null;

		try {
			rs = pstmt.executeQuery();

			if (rs.next()) {
				String nome = rs.getString(1);
				String autor = rs.getString(2);
				String editora = rs.getString(3);
				int preco = rs.getInt(4);
				Date dataLancamento = rs.getDate(5);
				livro = new Livro(codigo, nome, autor, editora, preco, dataLancamento);
			}
		} catch (SQLException e) {
			Log.gravaLog(e);
			throw new BaseDadosException("Problemas ao ler o resultado da consulta.");
		}

		fechaConexao();
		return livro;
	}

	@Override
	public List<Livro> listaTodos() throws BaseDadosException {
		LinkedList<Livro> livros = new LinkedList<Livro>();
		abreConexao();
		preparaComandoSQL("select codigo, nome, autor, editora, preco, dataLancamento from Livro");

		try {
			rs = pstmt.executeQuery();

			while (rs.next()) {
				int codigo = rs.getInt(1);
				String nome = rs.getString(2);
				String autor = rs.getString(3);
				String editora = rs.getString(4);
				int preco = rs.getInt(5);
				Date dataLancamento = rs.getDate(6);

				Livro livro = new Livro(codigo, nome, autor, editora, preco, dataLancamento);
				livros.add(livro);
			}
		} catch (SQLException e) {
			Log.gravaLog(e);
			throw new BaseDadosException("Problemas ao ler o resultado da consulta.");
		}

		fechaConexao();
		return livros;
	}

	@Override
	public void remove(int codigoLivro) throws BaseDadosException {
		abreConexao();
		preparaComandoSQL("delete from Livro where codigo = ?");
		try {
			pstmt.setInt(1, codigoLivro);
			pstmt.execute();
		} catch (SQLException e) {
			Log.gravaLog(e);
			throw new BaseDadosException("Erro ao executar remoção");
		}
		fechaConexao();
	}

	@Override
	public List<Livro> buscaLivrosPorNome(String tituloPesquisado) throws BaseDadosException {
		LinkedList<Livro> livros = new LinkedList<Livro>();
		abreConexao();
		preparaComandoSQL(
				"select codigo, nome, autor, editora, preco, dataLancamento from Livro WHERE LOWER(nome) LIKE ?");

		try {
			pstmt.setString(1, ("%" + tituloPesquisado + "%").toLowerCase());
			rs = pstmt.executeQuery();

			while (rs.next()) {
				int codigo = rs.getInt(1);
				String nome = rs.getString(2);
				String autor = rs.getString(3);
				String editora = rs.getString(4);
				int preco = rs.getInt(5);
				Date dataLancamento = rs.getDate(6);

				Livro livro = new Livro(codigo, nome, autor, editora, preco, dataLancamento);
				livros.add(livro);
			}
		} catch (SQLException e) {
			Log.gravaLog(e);
			throw new BaseDadosException("Problemas ao ler o resultado da consulta.");
		}

		fechaConexao();
		return livros;
	}

	@Override
	public Livro buscaLivroPorNomeExato(String nome) throws BaseDadosException {
		Livro livro = null;
		abreConexao();
		try {
			preparaComandoSQL("SELECT codigo FROM Livro WHERE nome = ?");
			pstmt.setString(1, nome);

			rs = pstmt.executeQuery();

			if (rs.next()) {
				int codigo = rs.getInt(1);

				livro = busca(codigo);
			}
		} catch (SQLException e) {
			Log.gravaLog(e);
			throw new BaseDadosException("Problemas ao ler o resultado da consulta.");
		}
		fechaConexao();

		return livro;
	}

	@Override
	public List<Livro> listaLivrosRecentes(int limite) throws BaseDadosException {
		LinkedList<Livro> livros = new LinkedList<Livro>();
		abreConexao();
		// Recentes por inclusão ou recentes por lancamento?
		preparaComandoSQL("select codigo, nome, autor, editora, preco, dataLancamento from Livro"
				+ " ORDER BY codigo DESC LIMIT " + limite);

		try {
			rs = pstmt.executeQuery();

			while (rs.next()) {
				int codigo = rs.getInt(1);
				String nome = rs.getString(2);
				String autor = rs.getString(3);
				String editora = rs.getString(4);
				int preco = rs.getInt(5);
				Date dataLancamento = rs.getDate(6);

				Livro livro = new Livro(codigo, nome, autor, editora, preco, dataLancamento);
				livros.add(livro);
			}
		} catch (SQLException e) {
			Log.gravaLog(e);
			throw new BaseDadosException("Problemas ao ler o resultado da consulta.");
		}

		fechaConexao();
		return livros;
	}
	
}
