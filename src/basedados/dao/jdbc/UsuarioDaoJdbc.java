package basedados.dao.jdbc;

import java.sql.Date;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.LinkedList;
import java.util.List;

import basedados.BaseDadosException;
import basedados.dao.UsuarioDao;
import dto.Usuario;
import utilidades.Log;

public class UsuarioDaoJdbc extends ConectorDaoJdbc implements UsuarioDao {

	public UsuarioDaoJdbc() throws BaseDadosException {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public void insere(Usuario usuario) throws BaseDadosException {
		abreConexao();
		preparaComandoSQL("insert into Usuario (nome, dataNascimento, telefone, cpf, senha, saldoCartaoClube, administrador, ativado, ultimoLogin) "
				+ "values (?, ?, ?, ?, ?, ?, ?, ?, ?)");

		try {
			pstmt.setString(1, usuario.getNome());
			pstmt.setDate(2, usuario.getDataNascimento());
			pstmt.setString(3, usuario.getTelefone());
			pstmt.setString(4, usuario.getCpf());
			pstmt.setString(5, usuario.getSenha());
			pstmt.setInt(6, usuario.getSaldoCartaoClube());
			pstmt.setBoolean(7, usuario.isAdministrador());
			pstmt.setBoolean(8, usuario.isAtivado());
			pstmt.setTimestamp(9, usuario.getUltimoLogin());
			pstmt.execute();
		} catch (SQLException e) {
			Log.gravaLog(e);
			throw new BaseDadosException("Erro ao setar os par√¢metros da consulta.");
		}

		fechaConexao();
	}

	@Override
	public Usuario busca(int codigo) throws BaseDadosException {
		abreConexao();
		preparaComandoSQL(
				"select nome, dataNascimento, telefone, cpf, senha, saldoCartaoClube, administrador, ativado, ultimoLogin from Usuario where codigo=" + codigo);
		Usuario usuario = null;

		try {
			rs = pstmt.executeQuery();

			if (rs.next()) {
				String nome = rs.getString(1);
				Date dataNascimento = rs.getDate(2);
				String telefone = rs.getString(3);
				String cpf = rs.getString(4);
				String senha = rs.getString(5);
				int saldoCartaoClube = rs.getInt(6);
				boolean administrador = rs.getBoolean(7);
				boolean ativado = rs.getBoolean(8);
				Timestamp ultimoLogin = rs.getTimestamp(9);
				
				usuario = new Usuario(codigo, nome, dataNascimento, telefone, cpf, senha, saldoCartaoClube, administrador, ativado, ultimoLogin);
			}
		} catch (SQLException e) {
			Log.gravaLog(e);
			throw new BaseDadosException("Problemas ao ler o resultado da consulta.");
		}

		fechaConexao();
		return usuario;
	}
	
	@Override
	public void altera(Usuario usuario) throws BaseDadosException {
		abreConexao();
		preparaComandoSQL("update Usuario set nome = ?, dataNascimento = ?, telefone = ?, cpf = ?, senha = ?,"
				+ " saldoCartaoClube = ?, administrador = ?, ativado = ?, ultimoLogin = ? where codigo = ?");
		try {
			pstmt.setString(1, usuario.getNome());
			pstmt.setDate(2, usuario.getDataNascimento());
			pstmt.setString(3, usuario.getTelefone());
			pstmt.setString(4, usuario.getCpf());
			pstmt.setString(5, usuario.getSenha());
			pstmt.setInt(6, usuario.getSaldoCartaoClube());
			pstmt.setBoolean(7, usuario.isAdministrador());
			pstmt.setBoolean(8, usuario.isAtivado());
			pstmt.setTimestamp(9, usuario.getUltimoLogin());
			pstmt.setInt(10, usuario.getCodigo());
			
			pstmt.execute();
		} catch (SQLException e) {
			Log.gravaLog(e);
			throw new BaseDadosException("Problemas no acesso ao banco de dados.");
		}
	}

	@Override
	public List<Usuario> listaTodos() throws BaseDadosException {
		LinkedList<Usuario> usuarios = new LinkedList<Usuario>();
		abreConexao();
		preparaComandoSQL("select codigo, nome, dataNascimento, telefone, cpf, senha, saldoCartaoClube, administrador, ativado, ultimoLogin from Usuario");

		try {
			rs = pstmt.executeQuery();

			while (rs.next()) {
				int codigo = rs.getInt(1);
				String nome = rs.getString(2);
				Date dataNascimento = rs.getDate(3);
				String telefone = rs.getString(4);
				String cpf = rs.getString(5);
				String senha = rs.getString(6);
				int saldoCartaoClube = rs.getInt(7);
				boolean administrador = rs.getBoolean(8);
				boolean ativado = rs.getBoolean(9);
				Timestamp ultimoLogin = rs.getTimestamp(10);

				Usuario usuario = new Usuario(codigo, nome, dataNascimento, telefone, cpf, senha, saldoCartaoClube, administrador, ativado, ultimoLogin);
				usuarios.add(usuario);
			}
		} catch (SQLException e) {
			Log.gravaLog(e);
			throw new BaseDadosException("Problemas ao ler o resultado da consulta.");
		}
		fechaConexao();

		return usuarios;
	}

	@Override
	public void remove(int codigoUsuario) throws BaseDadosException {
		// deveriamos r as vendas/alugueis dele tbm, mas isso n altera mt.
		// se ele n existe tanto faz se existem vendas pra ele ou n...
		abreConexao();
		preparaComandoSQL("delete from Usuario where codigo = ?");
		try {
			pstmt.setInt(1, codigoUsuario);
			pstmt.execute();
		} catch (SQLException e) {
			Log.gravaLog(e);
			throw new BaseDadosException("Problema na conex„o com o banco de dados.");
		}
	}

	@Override
	public List<Usuario> buscaPorNome(String termo) throws BaseDadosException {
		List<Usuario> usuarios = new LinkedList<Usuario>();
		abreConexao();
		preparaComandoSQL(
				"select codigo, nome, dataNascimento, telefone, cpf, senha, saldoCartaoClube, administrador, ativado, ultimoLogin from Usuario where LOWER(nome) like ?");

		try {
			pstmt.setString(1, ("%" + termo + "%").toLowerCase());
			rs = pstmt.executeQuery();

			while (rs.next()) {
				int codigo = rs.getInt(1);
				String nome = rs.getString(2);
				Date dataNascimento = rs.getDate(3);
				String telefone = rs.getString(4);
				String cpf = rs.getString(5);
				String senha = rs.getString(6);
				int saldoCartaoClube = rs.getInt(7);
				boolean administrador = rs.getBoolean(8);
				boolean ativado = rs.getBoolean(9);
				Timestamp ultimoLogin = rs.getTimestamp(10);

				Usuario usuario = new Usuario(codigo, nome, dataNascimento, telefone, cpf, senha, saldoCartaoClube,
						administrador, ativado, ultimoLogin);
				usuarios.add(usuario);
			}
		} catch (SQLException e) {
			Log.gravaLog(e);
			throw new BaseDadosException("Problemas ao ler o resultado da consulta.");
		}
		fechaConexao();

		return usuarios;
	}

	@Override
	public Usuario buscaPorNomeExato(String nome) throws BaseDadosException {
		Usuario usuario = null;
		abreConexao();
		try {
			preparaComandoSQL("SELECT codigo FROM Usuario WHERE nome = ?");
			pstmt.setString(1, nome);

			rs = pstmt.executeQuery();

			if (rs.next()) {
				int codigo = rs.getInt(1);

				usuario = busca(codigo);
			}
		} catch (SQLException e) {
			Log.gravaLog(e);
			throw new BaseDadosException("Problemas ao ler o resultado da consulta.");
		}
		fechaConexao();

		return usuario;
	}

}
