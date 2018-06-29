package negocio;

import basedados.BaseDadosException;
import basedados.FachadaBaseDados;
import cartoes.Cartao;
import cartoes.CartaoSemSaldoException;
import cartoes.GerenciadorCartoes;
import dto.Usuario;
import utilidades.Log;

public class RegrasNegocioCartaoClube {
	private FachadaBaseDados fachadaBaseDados;
	
	public RegrasNegocioCartaoClube(FachadaBaseDados fachadaBaseDados) {
		this.fachadaBaseDados = fachadaBaseDados;
	}
	
	public void insereCreditoCartaoClube(GerenciadorCartoes gerenciadorCartoes, Cartao cartao, Usuario usuario, int valor) throws NegocioException {
		if (valor < 0)
			throw new NegocioException("Valor inválido!");

		try {
			gerenciadorCartoes.realizarDebito(cartao, valor);
		} catch (CartaoSemSaldoException e) {
			Log.gravaLog(e);
			throw new NegocioException("Cartão não possui saldo!");
		} catch (BaseDadosException e) {
			Log.gravaLog(e);
			throw new NegocioException("Problemas na conexão com o banco de dados.");
		}

		// Famoso cash Level Up Games.
		if (valor >= 1000)
			valor = (int) (valor * 1.5);
		else if (valor >= 300)
			valor = (int) (valor * 1.3);
		else if (valor >= 100)
			valor = (int) (valor * 1.2);
		else if (valor >= 50)
			valor = (int) (valor * 1.1);
		
		usuario.setSaldoCartaoClube(usuario.getSaldoCartaoClube() + valor);
		try {
			fachadaBaseDados.alteraUsuario(usuario);
		} catch (BaseDadosException e) {
			Log.gravaLog(e);
			throw new NegocioException("Problemas no acesso ao banco de dados!");
		}
	}
}
