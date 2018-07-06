package negocio;

import utilidades.Log;
import dto.Livro;
import dto.Promocao;
import dto.Usuario;

import java.sql.Date;
import java.time.Instant;
import basedados.BaseDadosException;
import basedados.FachadaBaseDados;

public class RegrasNegocioPromocao {
	public class PromocaoCalculada {
		public String tipoDePromocao;
		public int valor;
	}
	
	private FachadaBaseDados fachadaBaseDados;
	
	public RegrasNegocioPromocao(FachadaBaseDados fachadaBaseDados) {
		this.fachadaBaseDados = fachadaBaseDados;
	}
	
	public void cadastraPromocao(int codigo, int novoPreco) throws NegocioException {
		try {
			if (buscaPromocao(codigo) != null) {
				throw new NegocioException("Livro já possui uma promoção!");
			}
			
			Promocao promocao = new Promocao(codigo, novoPreco);
			fachadaBaseDados.inserePromocao(promocao);
		} catch (BaseDadosException e) {
			Log.gravaLog(e);
			throw new NegocioException("Problemas no acesso ao banco de dados.");
		}
	}
	
	public Promocao buscaPromocao(int codigoLivro) throws NegocioException {
		try {			
			return fachadaBaseDados.buscaPromocao(codigoLivro);
		} catch (BaseDadosException e) {
			Log.gravaLog(e);
			throw new NegocioException("Problemas no acesso ao banco de dados.");
		}
	}
	
	public void removePromocao(int codigoLivro) throws NegocioException {
		try {
			if (buscaPromocao(codigoLivro) == null) {
				throw new NegocioException("Livro não possui uma promoção!");
			}
			
			fachadaBaseDados.removePromocao(codigoLivro);
		} catch (BaseDadosException e) {
			Log.gravaLog(e);
			throw new NegocioException("Problemas no acesso ao banco de dados.");
		}
	}
	
	public PromocaoCalculada calcularValorEmPromocao(Livro livro, Usuario usuario) throws NegocioException {
		String tipoDePromocao = "normal";
		int precoLivro = livro.getPreco();
		int preco = livro.getPreco();
		Promocao promocao = buscaPromocao(livro.getCodigo());
		
		if (promocao != null) {
			preco = promocao.getPreco();
			tipoDePromocao = "promocao";
		}
		
		java.util.Date dataDeHoje = Date.from(Instant.now());
		Date dataNascimento = usuario.getDataNascimento();
		
		if (preco > (precoLivro / 2) && (dataDeHoje.getDate() == dataNascimento.getDate()
				|| dataDeHoje.getMonth() == dataNascimento.getMonth())) {
			preco = precoLivro / 2;
			tipoDePromocao = "aniversario";
		}
		
		PromocaoCalculada ret = new PromocaoCalculada();
		ret.valor = preco;
		ret.tipoDePromocao = tipoDePromocao;
		
		return ret;
	}
}
