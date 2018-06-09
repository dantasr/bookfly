package negocio;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;

import basedados.BaseDadosException;
import basedados.FachadaBaseDados;
import cartoes.Cartao;
import cartoes.CartaoSemSaldoException;
import cartoes.GerenciadorCartoes;
import dto.Livro;
import dto.Usuario;
import dto.Venda;
import utilidades.Log;

public class RegrasNegocioVenda {
	private FachadaBaseDados fachadaBaseDados;
	
	public RegrasNegocioVenda(FachadaBaseDados fachadaBaseDados) {
		this.fachadaBaseDados = fachadaBaseDados;
	}
	
	public List<Venda> listaVendas() throws NegocioException {
		try {
			return fachadaBaseDados.listaVendas();
		} catch (BaseDadosException e) {
			Log.gravaLog(e);
			throw new NegocioException("Problemas no acesso ao banco de dados.");
		}
	}
	
	public Venda buscaVenda(int codigo) throws NegocioException {
		try {
			return fachadaBaseDados.buscaVenda(codigo);
		} catch (BaseDadosException e) {
			Log.gravaLog(e);
			throw new NegocioException("Problemas no acesso ao banco de dados.");
		}
	}
	
	public void vendeLivroCartaoClube(Usuario usuario, Livro livro, int preco) throws NegocioException {
		if (preco > usuario.getSaldoCartaoClube()) {
			throw new NegocioException("O usuario não possui saldo suficiente.");
		}

		usuario.setSaldoCartaoClube(usuario.getSaldoCartaoClube() - preco);
		Timestamp dataDaVenda = Timestamp.from(Instant.now());
		try {
			fachadaBaseDados.alteraUsuario(usuario);
			Venda venda = new Venda(0, livro, usuario, dataDaVenda);
			fachadaBaseDados.insereVenda(venda);
		} catch (BaseDadosException e) {
			Log.gravaLog(e);
			throw new NegocioException("Problemas no acesso ao banco de dados.");
		}
	}
	
	public void vendeLivroCartaoNormal(GerenciadorCartoes gerenciadorCartoes, Cartao cartao, Usuario usuario, Livro livro, int preco) throws NegocioException {
		try {
			gerenciadorCartoes.realizarDebito(cartao, preco);
		} catch (CartaoSemSaldoException e) {
			Log.gravaLog(e);
			throw new NegocioException("Cartão não possui saldo!");
		} catch (BaseDadosException e) {
			Log.gravaLog(e);
			throw new NegocioException("Problemas na conexão com o banco de dados.");
		}

		try {
			Timestamp dataDaVenda = Timestamp.from(Instant.now());
			Venda venda = new Venda(0, livro, usuario, dataDaVenda);
			fachadaBaseDados.insereVenda(venda);
		} catch (BaseDadosException e) {
			Log.gravaLog(e);
			throw new NegocioException("Problemas no acesso ao banco de dados!");
		}
	}
	
	public List<Venda> buscaVendasDoUsuario(int codigoUsuario) throws NegocioException {
		try {
			return fachadaBaseDados.buscaVendasDoUsuario(codigoUsuario);
		} catch (BaseDadosException e) {
			Log.gravaLog(e);
			throw new NegocioException("Problemas no acesso ao banco de dados.");
		}
	}
}
