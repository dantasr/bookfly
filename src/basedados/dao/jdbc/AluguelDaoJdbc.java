package basedados.dao.jdbc;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.LinkedList;
import java.util.List;

import basedados.BaseDadosException;
import basedados.dao.AluguelDao;
import basedados.dao.LivroDao;
import basedados.dao.UsuarioDao;
import dto.Aluguel;
import dto.Usuario;
import utilidades.Log;

public class AluguelDaoJdbc extends ConectorDaoJdbc implements AluguelDao {
	private LivroDao livroDao;
	private UsuarioDao usuarioDao;
	
	public AluguelDaoJdbc(LivroDao livroDao, UsuarioDao usuarioDao) throws BaseDadosException {
		super();
		this.livroDao = livroDao;
		this.usuarioDao = usuarioDao;
	}

	@Override
	public void insere(Aluguel aluguel) throws BaseDadosException {
		abreConexao();

		try {
			preparaComandoSQL("insert into Aluguel (codigoLivro, codigoUsuario, dataDoAluguel, expiracao) " + "values (?, ?, ?, ?)");
			pstmt.setInt(1, aluguel.getLivro().getCodigo());
			pstmt.setInt(2, aluguel.getUsuario().getCodigo());
			pstmt.setTimestamp(3, aluguel.getDataDoAluguel());
			pstmt.setTimestamp(4, aluguel.getExpiracao());
			pstmt.execute();
		} catch (SQLException e) {
			Log.gravaLog(e);
			throw new BaseDadosException("Erro ao setar os parâmetros da consulta.");
		}

		fechaConexao();
	}

	@Override
	public Aluguel busca(int codigo) throws BaseDadosException {
		abreConexao();
		preparaComandoSQL("select codigoLivro, codigoUsuario, dataDoAluguel, expiracao from Aluguel where codigo=" + codigo);
		Aluguel aluguel = null;

		try {
			rs = pstmt.executeQuery();

			if (rs.next()) {
				int codigoLivro = rs.getInt(1);
				int codigoUsuario = rs.getInt(2);
				Timestamp dataDoAluguel = rs.getTimestamp(3);
				Timestamp expiracao = rs.getTimestamp(4);
				aluguel = new Aluguel(codigo, livroDao.busca(codigoLivro), usuarioDao.busca(codigoUsuario), dataDoAluguel, expiracao);
			}
		} catch (SQLException e) {
			Log.gravaLog(e);
			throw new BaseDadosException("Problemas ao ler o resultado da consulta.");
		}

		fechaConexao();
		return aluguel;
	}

	@Override
	public List<Aluguel> listaTodos() throws BaseDadosException {
		LinkedList<Aluguel> alugueis = new LinkedList<Aluguel>();
		LinkedList<Integer> codigoLivroDosAlugueis = new LinkedList<Integer>();
		LinkedList<Integer> codigoUsuarioDosAlugueis = new LinkedList<Integer>();
		abreConexao();

		preparaComandoSQL("select codigo, codigoLivro, codigoUsuario, dataDoAluguel, expiracao from Aluguel");

		try {
			rs = pstmt.executeQuery();

			while (rs.next()) {
				int codigo = rs.getInt(1);
				int codigoLivro = rs.getInt(2);
				int codigoUsuario = rs.getInt(3);
				Timestamp dataDoAluguel = rs.getTimestamp(4);
				Timestamp expiracao = rs.getTimestamp(5);

				Aluguel aluguel = new Aluguel(codigo, null, null, dataDoAluguel, expiracao);
				codigoLivroDosAlugueis.add(codigoLivro);
				codigoUsuarioDosAlugueis.add(codigoUsuario);
				alugueis.add(aluguel);
			}
		} catch (SQLException e) {
			Log.gravaLog(e);
			throw new BaseDadosException("Problemas ao ler o resultado da consulta.");
		}
		fechaConexao();

		for (int i = 0; i < codigoLivroDosAlugueis.size(); i++) {
			Aluguel aluguel = alugueis.get(i);
			int codigoLivro = codigoLivroDosAlugueis.get(i);
			int codigoUsuario = codigoUsuarioDosAlugueis.get(i);

			aluguel.setLivro(livroDao.busca(codigoLivro));
			aluguel.setUsuario(usuarioDao.busca(codigoUsuario));
		}

		return alugueis;
	}

	@Override
	public void remove(int codigoAluguel) throws BaseDadosException {
		// TODO Auto-generated method stub
		return;
	}

	@Override
	public List<Aluguel> buscaDoUsuario(int codigoUsuario) throws BaseDadosException {
		LinkedList<Aluguel> alugueis = new LinkedList<Aluguel>();
		LinkedList<Integer> codigoLivroDosAlugueis = new LinkedList<Integer>();
		Usuario usuario = usuarioDao.busca(codigoUsuario);
		abreConexao();

		preparaComandoSQL("select codigo, codigoLivro, dataDoAluguel, expiracao from Aluguel where codigoUsuario = ?");

		try {
			pstmt.setInt(1, codigoUsuario);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				int codigo = rs.getInt(1);
				int codigoLivro = rs.getInt(2);
				Timestamp dataDoAluguel = rs.getTimestamp(3);
				Timestamp expiracao = rs.getTimestamp(4);

				Aluguel aluguel = new Aluguel(codigo, null, usuario, dataDoAluguel, expiracao);
				codigoLivroDosAlugueis.add(codigoLivro);
				alugueis.add(aluguel);
			}
		} catch (SQLException e) {
			Log.gravaLog(e);
			throw new BaseDadosException("Problemas ao ler o resultado da consulta.");
		}
		fechaConexao();

		for (int i = 0; i < codigoLivroDosAlugueis.size(); i++) {
			Aluguel aluguel = alugueis.get(i);
			int codigoLivro = codigoLivroDosAlugueis.get(i);

			aluguel.setLivro(livroDao.busca(codigoLivro));
		}

		return alugueis;
	}
}
