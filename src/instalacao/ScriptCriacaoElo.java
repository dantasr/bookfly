package instalacao;

import java.sql.SQLException;

import basedados.BaseDadosException;
import cartoes.Cartao;
import cartoes.GerenciadorElo;

public class ScriptCriacaoElo extends GerenciadorElo {
	public ScriptCriacaoElo() throws BaseDadosException {
		super();
	}

	protected void criaBancoDeDados() throws SQLException, BaseDadosException {
		// freire tb fez isso. n�o me culpem.
		String copiaDbName = DB_NAME;
		DB_NAME = "";
		abreConexao();
		jaCriouBD = true;
		DB_NAME = copiaDbName;
		try {
			preparaComandoSQL("drop database " + getDbName());
			pstmt.execute();
		} catch (Exception e) {} // caso ela j� n�o exista... 
		preparaComandoSQL("create database if not exists " + getDbName());
		preparaComandoSQL("create database if not exists " + getDbName());
		pstmt.execute();
		fechaConexao();
	}
	
	protected void criaTabelaCartoes() throws SQLException, BaseDadosException {
		abreConexao();
		preparaComandoSQL("create table if not exists Cartao ("
				+ "codigo int unsigned not null primary key,"
				+ "ano int unsigned not null,"
				+ "mes int unsigned not null,"
				+ "cvv int unsigned not null,"
				+ "saldo int unsigned not null)");
		pstmt.execute();
		fechaConexao();
	}
	
	public void criaTabelasDB() throws BaseDadosException {
		try {
			criaBancoDeDados();
			criaTabelaCartoes();
			populaTabelas();
		} catch (SQLException e) {
			throw new BaseDadosException("Erro ao criar banco de dados.");
		}
	}
	
	protected void populaTabelas() throws BaseDadosException, SQLException {
		// J� populada.
		if (buscaCartao(123456789) != null) return;
		
		insereCartao(new Cartao(123456789, 21, 2, 123, 50000));
		insereCartao(new Cartao(999999999, 22, 5, 999, 0));
	}
}
