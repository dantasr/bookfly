package cartoes;

import java.sql.SQLException;

import basedados.BaseDadosException;
import basedados.dao.jdbc.ConectorDaoJdbc;
import utilidades.Log;

/**
 * Classe que serve de base para os cartões MasterCard, Visa e ELO.
 * Possui a grande parte das funcoes que elas vão usar. 
 */
public abstract class GerenciadorCartoes extends ConectorDaoJdbc {
	public GerenciadorCartoes() throws BaseDadosException {
		super();
		
		DB_NAME = "cartao" + getNomeOperadoraCartao();
	}

	protected boolean jaCriouBD;

	public abstract String getNomeOperadoraCartao();
	
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
		
		if (cartao == null)
			return null;
		
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
}
