package negocio;

import main.Contexto;
import utilidades.Log;
import dto.Promocao;
import basedados.BaseDadosException;
import basedados.FachadaBaseDados;

public class RegrasNegocioPromocao {
	private FachadaBaseDados fachadaBaseDados;
	
	public RegrasNegocioPromocao(FachadaBaseDados fachadaBaseDados) {
		this.fachadaBaseDados = fachadaBaseDados;
	}
	
	public void cadastraPromocao(int codigo,Contexto contexto, int novoPreco) throws NegocioException {
		try {
			Promocao promocao = new Promocao(codigo,contexto.getGerenciadorRegrasNegocio().buscaLivro(codigo),novoPreco);
			fachadaBaseDados.inserePromocao(promocao);
		} catch (BaseDadosException e) {
			Log.gravaLog(e);
			throw new NegocioException("Problemas no acesso ao banco de dados.");
		}
	}
}
