package instalacao;

import java.sql.SQLException;

import basedados.BaseDadosException;
import cartoes.Cartao;
import cartoes.GerenciadorVisa;

public class ScriptCriacaoVisa extends GerenciadorVisa {
	public ScriptCriacaoVisa() throws BaseDadosException {
		super();
	}

	protected void criaBancoDeDados() throws SQLException, BaseDadosException {
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
	
	protected void populaTabelas() throws BaseDadosException, SQLException {
		// Já populada.
		if (buscaCartao(333333333) != null) return;
		
		insereCartao(new Cartao(333333333, 21, 2, 123, 50000));
		insereCartao(new Cartao(444444444, 22, 5, 999, 0));
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
}
