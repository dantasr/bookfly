package cartoes;

import java.sql.SQLException;

import basedados.BaseDadosException;
import basedados.ConectorJDBC;
import utilidades.Log;

/**
 * Classe que serve de base para os cartões MasterCard, Visa e ELO.
 * Possui a grande parte das funcoes que elas vão usar. 
 */
public abstract class GerenciadorCartoes extends ConectorJDBC {
	protected static final String PASSWORD = "nico0812"; //INSERIR SUA SENHA 
	protected static final String USER = "root";
	protected static final String HOST = "localhost";
	
	protected boolean jaCriouBD;
	
	public GerenciadorCartoes() throws BaseDadosException {
		super(DB.MYSQL);
		
		try {
			criaBancoDeDados();
			criaTabelaCartoes();
			populaTabelas();
		} catch (SQLException e) {
			Log.gravaLog(e);
			throw new BaseDadosException("Erro ao criar as tabelas dos cartões.");
		}
	}
	
	@Override
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

	public abstract String getNomeOperadoraCartao();
	
	@Override
	protected String getDbName() {
		return jaCriouBD ? getNomeOperadoraCartao() : "";
	}
	
	public Cartao buscaCartao(int codigoCartao) throws BaseDadosException {
		Cartao cartao = null;
		
		abreConexao();
		preparaComandoSQL("select ano, mes, cvv, saldo from Cartao where codigo = ?");
		try {
			pstmt.setInt(1, codigoCartao);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				int ano = rs.getInt(1);
				int mes = rs.getInt(2);
				int cvv = rs.getInt(3);
				int saldo = rs.getInt(4);
				
				cartao = new Cartao(codigoCartao, ano, mes, cvv, saldo);
			}
		} catch (SQLException e) {
			Log.gravaLog(e);
			throw new BaseDadosException("Erro durante o acesso ao banco de dados");
		}
		
		return cartao;
	}
	
	public Cartao buscaCartaoComCampos(int codigo, int ano, int mes, int cvv) throws BaseDadosException {
		Cartao cartao = buscaCartao(codigo);
		
		if (cartao.getCodigo() != codigo || cartao.getAno() != ano
				|| cartao.getMes() != mes || cartao.getCvv() != cvv)
			return null;
		
		return cartao;
	}
	
	public void alteraCartao(Cartao cartao) throws BaseDadosException {
		abreConexao();
		preparaComandoSQL("update Cartao set ano = ?, mes = ?, cvv = ?, saldo = ? where codigo = ?");
		try {
			pstmt.setInt(1, cartao.getAno());
			pstmt.setInt(2, cartao.getMes());
			pstmt.setInt(3, cartao.getCvv());
			pstmt.setInt(4, cartao.getSaldo());
			pstmt.setInt(5, cartao.getCodigo());
			
			pstmt.execute();
		} catch (SQLException e) {
			throw new BaseDadosException("Erro durante o acesso ao banco de dados");
		}
	}
	
	public void realizarDebito(Cartao cartao, int valor) throws CartaoSemSaldoException, BaseDadosException {
		if (cartao.getSaldo() < valor)
			throw new CartaoSemSaldoException("O cartao não possui saldo suficiente para realizar um débito de " + valor);
		
		cartao.setSaldo(cartao.getSaldo() - valor);
		alteraCartao(cartao);
	}
	
	protected void criaBancoDeDados() throws SQLException, BaseDadosException {
		abreConexao();
		jaCriouBD = true;
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
	
	protected void insereCartao(Cartao cartao) throws BaseDadosException, SQLException {
		abreConexao();
		preparaComandoSQL("insert into Cartao (codigo, ano, mes, cvv, saldo) values (?, ?, ?, ?, ?)");
		pstmt.setInt(1, cartao.getCodigo());
		pstmt.setInt(2, cartao.getAno());
		pstmt.setInt(3, cartao.getMes());
		pstmt.setInt(4, cartao.getCvv());
		pstmt.setInt(5, cartao.getSaldo());
		pstmt.execute();
		fechaConexao();
	}
	
	protected abstract void populaTabelas() throws BaseDadosException, SQLException;
}
