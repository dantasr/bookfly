package basedados.dao.jdbc;

import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import basedados.BaseDadosException;
import basedados.dao.LivroDao;
import basedados.dao.UsuarioDao;
import basedados.dao.VendaDao;
import dto.Usuario;
import dto.Venda;
import utilidades.Log;

public class VendaDaoJdbc extends ConectorDaoJdbc implements VendaDao {
	private LivroDao livroDao;
	private UsuarioDao usuarioDao;
	
	public VendaDaoJdbc(LivroDao livroDao, UsuarioDao usuarioDao) throws BaseDadosException {
		super();
		this.livroDao = livroDao;
		this.usuarioDao = usuarioDao;
	}

	@Override
	public void insere(Venda venda) throws BaseDadosException {
		abreConexao();

		try {
			preparaComandoSQL("insert into Venda (codigoLivro, codigoUsuario) values (?, ?)");
			pstmt.setInt(1, venda.getLivro().getCodigo());
			pstmt.setInt(2, venda.getUsuario().getCodigo());
			pstmt.execute();
		} catch (SQLException e) {
			Log.gravaLog(e);
			throw new BaseDadosException("Erro ao setar os parÃ¢metros da consulta.");
		}

		fechaConexao();
	}

	@Override
	public Venda busca(int codigo) throws BaseDadosException {
		abreConexao();
		preparaComandoSQL("select codigoLivro, codigoUsuario from Venda where codigo=" + codigo);
		Venda venda = null;

		try {
			rs = pstmt.executeQuery();

			if (rs.next()) {
				int codigoLivro = rs.getInt(1);
				int codigoUsuario = rs.getInt(2);

				venda = new Venda(codigo, livroDao.busca(codigoLivro), usuarioDao.busca(codigoUsuario));
			}
		} catch (SQLException e) {
			Log.gravaLog(e);
			throw new BaseDadosException("Problemas ao ler o resultado da consulta.");
		}

		fechaConexao();
		return venda;
	}
	
	public List<Venda> listaTodos() throws BaseDadosException {
		LinkedList<Venda> vendas = new LinkedList<Venda>();
		// Similar a função acima, usar essas listas pra evitar o bug do Freire.
		LinkedList<Integer> codigoLivroDasVendas = new LinkedList<Integer>();
		LinkedList<Integer> codigoUsuarioDasVendas = new LinkedList<Integer>();
		abreConexao();

		preparaComandoSQL("select codigo, codigoLivro, codigoUsuario from Venda");

		try {
			rs = pstmt.executeQuery();

			while (rs.next()) {
				int codigo = rs.getInt(1);
				int codigoLivro = rs.getInt(2);
				int codigoUsuario = rs.getInt(3);

				Venda venda = new Venda(codigo, null, null);
				codigoLivroDasVendas.add(codigoLivro);
				codigoUsuarioDasVendas.add(codigoUsuario);
				vendas.add(venda);
			}
		} catch (SQLException e) {
			Log.gravaLog(e);
			throw new BaseDadosException("Problemas ao ler o resultado da consulta.");
		}
		fechaConexao();

		for (int i = 0; i < codigoLivroDasVendas.size(); i++) {
			Venda venda = vendas.get(i);
			int codigoLivro = codigoLivroDasVendas.get(i);
			int codigoUsuario = codigoUsuarioDasVendas.get(i);

			venda.setLivro(livroDao.busca(codigoLivro));
			venda.setUsuario(usuarioDao.busca(codigoUsuario));
		}

		return vendas;
	}

	@Override
	public List<Venda> buscaDoUsuario(int codigoUsuario) throws BaseDadosException {
		LinkedList<Venda> vendas = new LinkedList<Venda>();
		// Similar a função acima, usar essas listas pra evitar o bug do Freire.
		LinkedList<Integer> codigoLivroDasVendas = new LinkedList<Integer>();
		Usuario usuario = usuarioDao.busca(codigoUsuario);
		abreConexao();

		preparaComandoSQL("select codigo, codigoLivro from Venda WHERE codigoUsuario = ?");

		try {
			pstmt.setInt(1, codigoUsuario);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				int codigo = rs.getInt(1);
				int codigoLivro = rs.getInt(2);

				Venda venda = new Venda(codigo, null, usuario);
				codigoLivroDasVendas.add(codigoLivro);
				vendas.add(venda);
			}
		} catch (SQLException e) {
			Log.gravaLog(e);
			throw new BaseDadosException("Problemas ao ler o resultado da consulta.");
		}
		fechaConexao();
		
		for (int i = 0; i < codigoLivroDasVendas.size(); i++) {
			Venda venda = vendas.get(i);
			int codigoLivro = codigoLivroDasVendas.get(i);

			venda.setLivro(livroDao.busca(codigoLivro));
		}

		return vendas;
	}
}
