package instalacao;

import java.sql.SQLException;

import basedados.BaseDadosException;
import cartoes.GerenciadorVisa;

public class ScriptCriacaoVisa extends GerenciadorVisa {
	public ScriptCriacaoVisa() throws BaseDadosException {
		super();
	}

	protected void criaBancoDeDados() throws SQLException, BaseDadosException {
		// freire tb fez isso. n�o me culpem.
		String copiaDbName = DB_NAME;
		DB_NAME = "";
		abreConexao();
		jaCriouBD = true;
		DB_NAME = copiaDbName;
		preparaComandoSQL("create database if not exists " + getDbName());
		pstmt.execute();
		fechaConexao();
	}
	
	protected void criaTabelaCartoes() throws BaseDadosException, SQLException {
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
		} catch (SQLException e) {
			throw new BaseDadosException("Erro ao criar banco de dados.");
		}
	}
}
