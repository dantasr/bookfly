package negocio;

import java.util.List;

import basedados.BaseDadosException;
import basedados.FachadaBaseDados;
import dto.Aluguel;
import utilidades.Log;

public class RegrasNegocioAluguel {
	private FachadaBaseDados fachadaBaseDados;
	
	public RegrasNegocioAluguel(FachadaBaseDados fachadaBaseDados) {
		this.fachadaBaseDados = fachadaBaseDados;
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
