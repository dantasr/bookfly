package basedados;

import java.sql.Date;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JOptionPane;

import utilidades.Log;
import beans.Aluguel;
import beans.Livro;
import beans.Promocao;
import beans.Usuario;
import beans.Venda;
import main.Contexto;

public class GerenciadorBaseDadosJDBC extends ConectorJDBC implements GerenciadorBaseDados {

	private static final String PASSWORD = "nico0812"; //INSERIR SUA SENHA 
	private static final String USER = "root";
	private static final String HOST = "localhost";
	private static final String DB_NAME = "bookfly";
	private boolean jaCriouBD;

	public GerenciadorBaseDadosJDBC() throws BaseDadosException {
		super(DB.MYSQL);

		try {
			criaBancoDeDados();
			criaTabelaLivro();
			criaTabelaUsuario();
			criaTabelaVenda();
			criaTabelaPromocao();
			criaTabelaAluguel();
			populaTabelas();
		} catch (SQLException e) {
			Log.gravaLog(e);
			throw new BaseDadosException("Erro ao tentar criar o banco de dados.");
		}
	}

	protected String getUser() {
		return USER;
	}

	@Override
	protected String getPassword() {
		return PASSWORD;
	}

	@Override
	protected String getDbHost() {
		return HOST;
	}

	@Override
	protected String getDbName() {
		return jaCriouBD ? DB_NAME : "";
	}

	/////////////////////////////INSERE USUARIO NO BD///////////////////////////////////////////////////////////////////////////
	public void insereUsuario(Usuario usuario) throws BaseDadosException {
		abreConexao();
		preparaComandoSQL("insert into Usuario (nome, dataNascimento, telefone, cpf, senha, saldoCartaoClube, administrador, ativado) "
				+ "values (?, ?, ?, ?, ?, ?, ?, ?)");

		try {
			pstmt.setString(1, usuario.getNome());
			pstmt.setDate(2, usuario.getDataNascimento());
			pstmt.setString(3, usuario.getTelefone());
			pstmt.setString(4, usuario.getCpf());
			pstmt.setString(5, usuario.getSenha());
			pstmt.setInt(6, usuario.getSaldoCartaoClube());
			pstmt.setBoolean(7, usuario.isAdministrador());
			pstmt.setBoolean(8, usuario.isAtivado());
			pstmt.execute();
		} catch (SQLException e) {
			Log.gravaLog(e);
			throw new BaseDadosException("Erro ao setar os parÃ¢metros da consulta.");
		}

		fechaConexao();
	}

	///////////////////////////INSERE LIVRO NO BD///////////////////////////////////////////////////////////////////////////
	public void insereLivro(Livro livro) throws BaseDadosException {
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

	/////////////////////////////INSERE PROMOCAO NO BD///////////////////////////////////////////////////////////////////////////
	public void inserePromocao(Promocao promocao) throws BaseDadosException {
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

	/////////////////////////////INSERE	VENDA NO BD///////////////////////////////////////////////////////////////////////////
	public void insereVenda(Venda venda) throws BaseDadosException {
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

	/////////////////////////////INSERE VENDA NO BD///////////////////////////////////////////////////////////////////////////
	public void insereAluguel(Aluguel aluguel) throws BaseDadosException {
		abreConexao();

		try {
			preparaComandoSQL("insert into Aluguel (codigoLivro, codigoUsuario, expiracao) " + "values (?, ?, ?)");
			pstmt.setInt(1, aluguel.getLivro().getCodigo());
			pstmt.setInt(2, aluguel.getUsuario().getCodigo());
			pstmt.setTimestamp(3, aluguel.getExpiracao());
			pstmt.execute();
		} catch (SQLException e) {
			Log.gravaLog(e);
			throw new BaseDadosException("Erro ao setar os parÃ¢metros da consulta.");
		}

		fechaConexao();
	}

	////////////////////////////BUSCA ALUGUEL NO BD///////////////////////////////////////////////////////////////////////////
	public Aluguel buscaAluguel(int codigo) throws BaseDadosException {
		abreConexao();
		preparaComandoSQL("select codigoLivro, codigoUsuario, expiracao from CD where codigo=" + codigo);
		Aluguel aluguel = null;

		try {
			rs = pstmt.executeQuery();

			if (rs.next()) {
				int codigoLivro = rs.getInt(1);
				int codigoUsuario = rs.getInt(2);
				Timestamp expiracao = rs.getTimestamp(3);
				aluguel = new Aluguel(codigo, buscaLivro(codigoLivro), buscaUsuario(codigoUsuario), expiracao);
			}
		} catch (SQLException e) {
			Log.gravaLog(e);
			throw new BaseDadosException("Problemas ao ler o resultado da consulta.");
		}

		fechaConexao();
		return aluguel;
	}

	/////////////////////////////BUSCA LIVRO NO BD///////////////////////////////////////////////////////////////////////////
	public Livro buscaLivro(int codigo) throws BaseDadosException {
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
	
	/////////////////////////////BUSCA PROMOCAO NO BD///////////////////////////////////////////////////////////////////////////
	public Promocao buscaPromocao(int codigo) throws BaseDadosException {
		abreConexao();
		preparaComandoSQL("select preco from Promocao where codigo =" + codigo);
		Livro livro = null;
		Promocao promo = null;
		try {
			rs = pstmt.executeQuery();

			if (rs.next()) {
				int preco = rs.getInt(1);
				livro = buscaLivro(codigo);
				promo = new Promocao(codigo, livro, preco);
			}
		} catch (SQLException e) {
			Log.gravaLog(e);
			throw new BaseDadosException("Problemas ao ler o resultado da consulta.");
		}

		fechaConexao();
		return promo;
	}

	/////////////////////////////BUSCA USUARIO NO BD///////////////////////////////////////////////////////////////////////////
	public Usuario buscaUsuario(int codigo) throws BaseDadosException {
		abreConexao();
		preparaComandoSQL(
				"select nome, dataNascimento, telefone, cpf, senha, saldoCartaoClube, administrador, ativado from Usuario where codigo=" + codigo);
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
				
				usuario = new Usuario(codigo, nome, dataNascimento, telefone, cpf, senha, saldoCartaoClube, administrador, ativado);
			}
		} catch (SQLException e) {
			Log.gravaLog(e);
			throw new BaseDadosException("Problemas ao ler o resultado da consulta.");
		}

		fechaConexao();
		return usuario;
	}

	/////////////////////////////LISTA USUARIOS DO BD///////////////////////////////////////////////////////////////////////////
	public LinkedList<Usuario> listaUsuarios() throws BaseDadosException {
		LinkedList<Usuario> usuarios = new LinkedList<Usuario>();
		abreConexao();
		preparaComandoSQL("select codigo, nome, dataNascimento, telefone, cpf, senha, saldoCartaoClube, administrador, ativado from Usuario");

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

				Usuario usuario = new Usuario(codigo, nome, dataNascimento, telefone, cpf, senha, saldoCartaoClube, administrador, ativado);
				usuarios.add(usuario);
			}
		} catch (SQLException e) {
			Log.gravaLog(e);
			throw new BaseDadosException("Problemas ao ler o resultado da consulta.");
		}
		fechaConexao();

		return usuarios;
	}

	/////////////////////////////LISTA LIVROS DO BD///////////////////////////////////////////////////////////////////////////
	public LinkedList<Livro> listaLivros() throws BaseDadosException {
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

	/////////////////////////////LISTA PROMOCOES DO BD///////////////////////////////////////////////////////////////////////////
	@Override
	public List<Promocao> listaPromocoes() throws BaseDadosException {
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

			promocao.setLivro(buscaLivro(codigoLivro));
		}

		return promocoes;
	}

	/////////////////////////////LISTA VENDAS DO BD///////////////////////////////////////////////////////////////////////////
	@Override
	public List<Venda> listaVendas() throws BaseDadosException {
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

			venda.setLivro(buscaLivro(codigoLivro));
			venda.setUsuario(buscaUsuario(codigoUsuario));
		}

		return vendas;
	}

	/////////////////////////////LISTA ALUGUEIS DO BD///////////////////////////////////////////////////////////////////////////
	@Override
	public List<Aluguel> listaAlugueis() throws BaseDadosException {
		LinkedList<Aluguel> alugueis = new LinkedList<Aluguel>();
		LinkedList<Integer> codigoLivroDosAlugueis = new LinkedList<Integer>();
		LinkedList<Integer> codigoUsuarioDosAlugueis = new LinkedList<Integer>();
		abreConexao();

		preparaComandoSQL("select codigo, codigoLivro, codigoUsuario, expiracao from Aluguel");

		try {
			rs = pstmt.executeQuery();

			while (rs.next()) {
				int codigo = rs.getInt(1);
				int codigoLivro = rs.getInt(2);
				int codigoUsuario = rs.getInt(3);
				Timestamp expiracao = rs.getTimestamp(4);

				Aluguel aluguel = new Aluguel(codigo, null, null, expiracao);
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

			aluguel.setLivro(buscaLivro(codigoLivro));
			aluguel.setUsuario(buscaUsuario(codigoUsuario));
		}

		return alugueis;
	}

	/////////////////////////////CRIA TABELA VENDA NO BD///////////////////////////////////////////////////////////////////////////
	private void criaTabelaVenda() throws SQLException, BaseDadosException {
		abreConexao();
		preparaComandoSQL(
				"create table if not exists Venda ("
						+ "codigo int unsigned not null auto_increment primary key,"
						+ "codigoLivro int unsigned not null," + "codigoUsuario int unsigned not null,"
						+ "constraint fk_Venda_Livro FOREIGN KEY (codigoLivro) REFERENCES Livro (codigo),"
						+ "constraint fk_Venda_Usuario FOREIGN KEY (codigoUsuario) REFERENCES Usuario (codigo))");
		pstmt.execute();
		fechaConexao();
	}

	private void criaTabelaUsuario() throws SQLException, BaseDadosException {
		abreConexao();
		preparaComandoSQL("create table if not exists Usuario ("
				+ "codigo int unsigned not null auto_increment primary key,"
				+ "nome varchar(50) not null,"
				+ "dataNascimento date not null," 
				+ "telefone varchar(50) not null," 
				+ "cpf varchar(20) not null,"
				+ "senha varchar(50) not null,"
				+ "saldoCartaoClube int unsigned not null,"
				+ "administrador boolean not null,"
				+ "ativado boolean not null)");
		pstmt.execute();
		fechaConexao();
	}

	/////////////////////////////CRIA TABELA PROMOCAO NO BD///////////////////////////////////////////////////////////////////////////
	private void criaTabelaPromocao() throws SQLException, BaseDadosException {
		abreConexao();
		preparaComandoSQL(
				"create table if not exists Promocao (" 
						+ "codigo int unsigned not null auto_increment primary key,"
						+ "codigoLivro int unsigned not null," 
						+ "preco int unsigned not null,"
						+ "constraint fk_Promocao_Livro FOREIGN KEY (codigoLivro) REFERENCES Livro (codigo))"
						);
		pstmt.execute();
		fechaConexao();
	}

	/////////////////////////////CRIA TABELA LIVRO NO BD///////////////////////////////////////////////////////////////////////////
	private void criaTabelaLivro() throws SQLException, BaseDadosException {
		abreConexao();
		preparaComandoSQL("create table if not exists Livro (" 
				+ "codigo int unsigned not null primary key,"
				+ "nome varchar(100) not null," 
				+ "autor varchar(100) not null," 
				+ "editora varchar(100) not null,"
				+ "preco int unsigned not null," 
				+ "dataLancamento date not null)");
		pstmt.execute();
		fechaConexao();
	}

	/////////////////////////////CRIA TABELA ALUGUEL NO BD///////////////////////////////////////////////////////////////////////////
	private void criaTabelaAluguel() throws SQLException, BaseDadosException {
		abreConexao();
		preparaComandoSQL("create table if not exists Aluguel ("
				+ "codigo int unsigned not null auto_increment primary key," 
				+ "codigoLivro int unsigned not null,"
				+ "codigoUsuario int unsigned not null," 
				+ "expiracao datetime not null,"
				+ "constraint fk_Aluguel_Livro FOREIGN KEY (codigoLivro) REFERENCES Livro (codigo),"
				+ "constraint fk_Aluguel_Usuario FOREIGN KEY (codigoUsuario) REFERENCES Usuario (codigo))");
		pstmt.execute();
		fechaConexao();
	}

	/////////////////////////////CRIA BANCO DE DADOS///////////////////////////////////////////////////////////////////////////
	private void criaBancoDeDados() throws SQLException, BaseDadosException {
		abreConexao();
		jaCriouBD = true;
		preparaComandoSQL("create database if not exists " + getDbName());
		pstmt.execute();
		fechaConexao();
	}

	/////////////////////////////POPULA TABELAS DO BD///////////////////////////////////////////////////////////////////////////
	@SuppressWarnings("deprecation")
	private void populaTabelas() throws BaseDadosException {
		if (buscaUsuario(1) != null) {
			return;
		}

		Usuario u1, u2;
		Livro l1, l2;

		insereUsuario(
				u1 = new Usuario(1, "admin", new Date(1999 - 1900, 12, 23), "11 12345678", "123456789-10", "admin", 50000, true, true));
		insereUsuario(
				u2 = new Usuario(1, "freire", new Date(1999 - 1900, 12, 23), "11 12345678", "123456789-10", "freire", 50000, false, true));

		insereLivro(
				l1 = new Livro(1,"Jogador N1","Ernest Cline","Random House",95, new Date(2011 - 1900, 8, 16)));
		insereLivro(
				l2 = new Livro(2,"Harry Potter","J.K Rowling","ABC",65, new Date(2011 - 1900, 8, 16)));
	}
	/////////////////////////////BUSCA VENDAS DO BD///////////////////////////////////////////////////////////////////////////
	@Override
	public Venda buscaVenda(int codigo) throws BaseDadosException {
		abreConexao();
		preparaComandoSQL("select codigoLivro, codigoUsuario from Venda where codigo=" + codigo);
		Venda venda = null;

		try {
			rs = pstmt.executeQuery();

			if (rs.next()) {
				int codigoLivro = rs.getInt(1);
				int codigoUsuario = rs.getInt(2);

				venda = new Venda(codigo, buscaLivro(codigoLivro), buscaUsuario(codigoUsuario));
			}
		} catch (SQLException e) {
			Log.gravaLog(e);
			throw new BaseDadosException("Problemas ao ler o resultado da consulta.");
		}

		fechaConexao();
		return venda;
	}


	/////////////////////////////BUSCA USUARIO POR NOME EXATO NO BD///////////////////////////////////////////////////////////////////////////
	public Usuario buscaUsuarioPorNomeExato(String nome) throws BaseDadosException {
		Usuario usuario = null;
		abreConexao();
		try {
			preparaComandoSQL("SELECT codigo FROM Usuario WHERE nome = ?");
			pstmt.setString(1, nome);

			rs = pstmt.executeQuery();

			if (rs.next()) {
				int codigo = rs.getInt(1);

				usuario = buscaUsuario(codigo);
			}
		} catch (SQLException e) {
			Log.gravaLog(e);
			throw new BaseDadosException("Problemas ao ler o resultado da consulta.");
		}
		fechaConexao();

		return usuario;
	}
	
	/////////////////////////////BUSCA LIVRO POR NOME EXATO NO BD///////////////////////////////////////////////////////////////////////////
	public Livro buscaLivroPorNomeExato(String nome) throws BaseDadosException {
		Livro livro = null;
		abreConexao();
		try {
			preparaComandoSQL("SELECT codigo FROM Livro WHERE nome = ?");
			pstmt.setString(1, nome);

			rs = pstmt.executeQuery();

			if (rs.next()) {
				int codigo = rs.getInt(1);

				livro = buscaLivro(codigo);
			}
		} catch (SQLException e) {
			Log.gravaLog(e);
			throw new BaseDadosException("Problemas ao ler o resultado da consulta.");
		}
		fechaConexao();

		return livro;
	}

	/////////////////////////////BUSCA USUARIOS POR NOME NO BD///////////////////////////////////////////////////////////////////////////
	@Override
	public List<Usuario> buscaUsuariosPorNome(String termo) throws BaseDadosException {
		List<Usuario> usuarios = new LinkedList<Usuario>();
		abreConexao();
		preparaComandoSQL(
				"select codigo, nome, dataNascimento, telefone, cpf, senha, saldoCartaoClube, administrador, ativado from Usuario where LOWER(nome) like ?");

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

				Usuario usuario = new Usuario(codigo, nome, dataNascimento, telefone, cpf, senha, saldoCartaoClube,
						administrador, ativado);
				usuarios.add(usuario);
			}
		} catch (SQLException e) {
			Log.gravaLog(e);
			throw new BaseDadosException("Problemas ao ler o resultado da consulta.");
		}
		fechaConexao();

		return usuarios;
	}

	/////////////////////////////BUSCA LIVROS POR NOME NO BD///////////////////////////////////////////////////////////////////////////
	@Override
	public List<Livro> buscaLivrosPorNome(String termo) throws BaseDadosException {
		LinkedList<Livro> livros = new LinkedList<Livro>();
		abreConexao();
		preparaComandoSQL(
				"select codigo, nome, autor, editora, preco, dataLancamento from Livro WHERE LOWER(nome) LIKE ?");

		try {
			pstmt.setString(1, ("%" + termo + "%").toLowerCase());
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
	
	public void alteraUsuario(Usuario usuario) throws BaseDadosException {
		abreConexao();
		preparaComandoSQL("update Usuario set nome = ?, dataNascimento = ?, telefone = ?, cpf = ?, senha = ?,"
				+ " saldoCartaoClube = ?, administrador = ?, ativado = ? where codigo = ?");
		try {
			pstmt.setString(1, usuario.getNome());
			pstmt.setDate(2, usuario.getDataNascimento());
			pstmt.setString(3, usuario.getTelefone());
			pstmt.setString(4, usuario.getCpf());
			pstmt.setString(5, usuario.getSenha());
			pstmt.setInt(6, usuario.getSaldoCartaoClube());
			pstmt.setBoolean(7, usuario.isAdministrador());
			pstmt.setBoolean(8, usuario.isAtivado());
			pstmt.setInt(9, usuario.getCodigo());
			
			pstmt.execute();
		} catch (SQLException e) {
			Log.gravaLog(e);
			throw new BaseDadosException("Problemas no acesso ao banco de dados.");
		}
	}
	
	public List<Venda> buscaVendasDoUsuario(int codigoUsuario) throws BaseDadosException {
		LinkedList<Venda> vendas = new LinkedList<Venda>();
		// Similar a função acima, usar essas listas pra evitar o bug do Freire.
		LinkedList<Integer> codigoLivroDasVendas = new LinkedList<Integer>();
		Usuario usuario = buscaUsuario(codigoUsuario);
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

			venda.setLivro(buscaLivro(codigoLivro));
		}

		return vendas;
	}
	
	public List<Aluguel> buscaAlugueisDoUsuario(int codigoUsuario) throws BaseDadosException {
		LinkedList<Aluguel> alugueis = new LinkedList<Aluguel>();
		LinkedList<Integer> codigoLivroDosAlugueis = new LinkedList<Integer>();
		Usuario usuario = buscaUsuario(codigoUsuario);
		abreConexao();

		preparaComandoSQL("select codigo, codigoLivro, expiracao from Aluguel where codigoUsuario = ?");

		try {
			pstmt.setInt(1, codigoUsuario);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				int codigo = rs.getInt(1);
				int codigoLivro = rs.getInt(2);
				Timestamp expiracao = rs.getTimestamp(4);

				Aluguel aluguel = new Aluguel(codigo, null, usuario, expiracao);
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

			aluguel.setLivro(buscaLivro(codigoLivro));
		}

		return alugueis;
	}
	
	public void removeUsuario(int codigoUsuario) throws BaseDadosException {
		// deveriamos remover as vendas/alugueis dele tbm, mas isso n altera mt.
		// se ele n existe tanto faz se existem vendas pra ele ou n...
		abreConexao();
		preparaComandoSQL("delete from Usuario where codigo = ?");
		try {
			pstmt.setInt(1, codigoUsuario);
			pstmt.execute();
		} catch (SQLException e) {
			Log.gravaLog(e);
			throw new BaseDadosException("Problema na conexão com o banco de dados.");
		}
	}
	
	public void removeLivro(int codigoLivro) throws BaseDadosException {
		abreConexao();
		preparaComandoSQL("delete from Livro where codigo = ?");
		try {
			pstmt.setInt(1, codigoLivro);
			pstmt.execute();
			 removePromocao(codigoLivro);
		}
		catch (SQLException e) {
			Log.gravaLog(e);
			throw new BaseDadosException("Problema na conexão com o banco de dados.");
		}
	}
	
	public void removePromocao(int codigoLivro) throws BaseDadosException {
		abreConexao();
		preparaComandoSQL("delete from Promocao where codigoLivro = ?");
		try {
			pstmt.setInt(1, codigoLivro);
			pstmt.execute();
		}
		catch (SQLException e) {
			Log.gravaLog(e);
			throw new BaseDadosException("Problema na conexão com o banco de dados.");
		}
	}
	
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

	@Override
	public List<Promocao> buscaPromocaoLista(int codigoPromocao) throws BaseDadosException {
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
			promo.add(buscaPromocao(codigo));
		}

		fechaConexao();
		return promo;
	}
}
