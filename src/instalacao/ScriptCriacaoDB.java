package instalacao;

import java.sql.Date;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Duration;
import java.time.Instant;
import basedados.BaseDadosException;
import basedados.dao.AluguelDao;
import basedados.dao.LivroDao;
import basedados.dao.PromocaoDao;
import basedados.dao.UsuarioDao;
import basedados.dao.VendaDao;
import basedados.dao.jdbc.AluguelDaoJdbc;
import basedados.dao.jdbc.ConectorDaoJdbc;
import basedados.dao.jdbc.LivroDaoJdbc;
import basedados.dao.jdbc.PromocaoDaoJdbc;
import basedados.dao.jdbc.UsuarioDaoJdbc;
import basedados.dao.jdbc.VendaDaoJdbc;
import dto.Livro;
import dto.Usuario;
import utilidades.Log;

public class ScriptCriacaoDB extends ConectorDaoJdbc {
	private AluguelDao aluguelDao;
	private LivroDao livroDao;
	private PromocaoDao promocaoDao;
	private UsuarioDao usuarioDao;
	private VendaDao vendaDao;
	
	public ScriptCriacaoDB() throws BaseDadosException {
		super();
		livroDao = new LivroDaoJdbc();
		usuarioDao = new UsuarioDaoJdbc();
		aluguelDao = new AluguelDaoJdbc(livroDao, usuarioDao);
		promocaoDao = new PromocaoDaoJdbc(livroDao);
		vendaDao = new VendaDaoJdbc(livroDao, usuarioDao);
	}
	
	public void criaTabelasBD() throws BaseDadosException {
		try {
			criaBancoDeDados();
			criaTabelaLivro();
			criaTabelaUsuario();
			criaTabelaAluguel();
			criaTabelaPromocao();
			criaTabelaVenda();
			populaTabelas();
		} catch (SQLException e) {
			Log.gravaLog(e);
			throw new BaseDadosException(
					"Erro ao tentar criar o banco de dados.");
		}
	}

	private void criaTabelaVenda() throws SQLException, BaseDadosException {
		abreConexao();
		preparaComandoSQL(
				"create table if not exists Venda ("
						+ "codigo int unsigned not null auto_increment primary key,"
						+ "codigoLivro int unsigned not null,"
						+ "codigoUsuario int unsigned not null,"
						+ "dataDaVenda datetime not null,"
						+ "constraint fk_Venda_Livro FOREIGN KEY (codigoLivro) REFERENCES Livro (codigo) ON DELETE CASCADE,"
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
				+ "ativado boolean not null,"
				+ "ultimoLogin datetime not null)");
		pstmt.execute();
		fechaConexao();
	}

	/////////////////////////////CRIA TABELA PROMOCAO NO BD///////////////////////////////////////////////////////////////////////////
	private void criaTabelaPromocao() throws SQLException, BaseDadosException {
		abreConexao();
		preparaComandoSQL(
				"create table if not exists Promocao (" 
						+ "codigo int unsigned not null primary key,"
						+ "preco int unsigned not null,"
						+ "constraint uniq_codigo UNIQUE (codigo))"
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
				+ "dataDoAluguel datetime not null," 
				+ "expiracao datetime not null,"
				+ "constraint fk_Aluguel_Livro FOREIGN KEY (codigoLivro) REFERENCES Livro (codigo) ON DELETE CASCADE,"
				+ "constraint fk_Aluguel_Usuario FOREIGN KEY (codigoUsuario) REFERENCES Usuario (codigo))");
		pstmt.execute();
		fechaConexao();
	}

	/////////////////////////////CRIA BANCO DE DADOS///////////////////////////////////////////////////////////////////////////
	private void criaBancoDeDados() throws SQLException, BaseDadosException {
		// freire tb fez isso. não me culpem.
		String copiaDbName = DB_NAME;
		DB_NAME = "";
		abreConexao();
		jaCriouBD = true;
		DB_NAME = copiaDbName;
		try {
			preparaComandoSQL("drop database " + getDbName());
			pstmt.execute();
		} catch (Exception e) {} // caso ela já não exista... 
		preparaComandoSQL("create database if not exists " + getDbName());
		pstmt.execute();
		fechaConexao();
	}

	/////////////////////////////POPULA TABELAS DO BD///////////////////////////////////////////////////////////////////////////
	@SuppressWarnings("deprecation")
	private void populaTabelas() throws BaseDadosException {
		if (usuarioDao.busca(1) != null) {
			return;
		}

		Usuario u1, u2;
		Livro l1, l2;

		usuarioDao.insere(
				u1 = new Usuario(1, "admin", new Date(1999 - 1900, 12, 23), "11 12345678", "123456789-10", "admin", 50000, true, true, Timestamp.from(Instant.now())));
		usuarioDao.insere(
				u2 = new Usuario(1, "freire", new Date(1999 - 1900, 12, 23), "11 12345678", "123456789-10", "freire", 50000, false, true, Timestamp.from(Instant.now())));

		livroDao.insere(
				l1 = new Livro(1,"Jogador N1","Ernest Cline","Random House",95, new Date(2011 - 1900, 8, 16)));
		livroDao.insere(
				l2 = new Livro(2,"Harry Potter","J.K Rowling","ABC",65, new Date(2011 - 1900, 8, 16)));
	}
}
