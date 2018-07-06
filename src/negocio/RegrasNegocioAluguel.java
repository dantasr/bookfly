package negocio;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.Period;
import java.util.List;

import basedados.BaseDadosException;
import basedados.FachadaBaseDados;
import dto.Aluguel;
import dto.Livro;
import dto.Usuario;
import utilidades.Log;

public class RegrasNegocioAluguel {
	private FachadaBaseDados fachadaBaseDados;
	
	public RegrasNegocioAluguel(FachadaBaseDados fachadaBaseDados) {
		this.fachadaBaseDados = fachadaBaseDados;
	}
	
	public int calculaPrecoDoAluguel(Livro livro) {
		return livro.getPreco() / 4;
	}
	
	public void realizaAluguel(Livro livro, Usuario usuario) throws NegocioException {
		try {
			int preco = calculaPrecoDoAluguel(livro);
			
			if (usuario.getSaldoCartaoClube() - preco < 0) {
				throw new NegocioException("Usuário não tem saldo suficiente!");
			}
			
			usuario.setSaldoCartaoClube(usuario.getSaldoCartaoClube() - preco);
			fachadaBaseDados.alteraUsuario(usuario);
			
			Timestamp agora = Timestamp.from(Instant.now());
			Timestamp tresDiasDepois = Timestamp.from(Instant.now().plus(Period.ofDays(3)));
			Aluguel aluguel = new Aluguel(0, livro, usuario, agora, tresDiasDepois);
			
			fachadaBaseDados.insereAluguel(aluguel);
		} catch (BaseDadosException e) {
			throw new NegocioException("Problemas no acesso ao banco de dados.");
		}
	}
	
	public List<Aluguel> listaAlugueis() throws NegocioException {
		try {
			return fachadaBaseDados.listaAlugueis();
		} catch (BaseDadosException e) {
			Log.gravaLog(e);
			throw new NegocioException("Problemas no acesso ao banco de dados.");
		}
	}
	
	public Aluguel buscaAluguel(int codigo) throws NegocioException {
		try {
			return fachadaBaseDados.buscaAluguel(codigo);
		} catch (BaseDadosException e) {
			Log.gravaLog(e);
			throw new NegocioException("Problemas no acesso ao banco de dados.");
		}
	}
	
	public List<Aluguel> buscaAlugueisDoUsuario(int codigoUsuario) throws NegocioException {
		try {
			return fachadaBaseDados.buscaAlugueisDoUsuario(codigoUsuario);
		} catch (BaseDadosException e) {
			Log.gravaLog(e);
			throw new NegocioException("Problemas no acesso ao banco de dados.");
		}
	}

}
