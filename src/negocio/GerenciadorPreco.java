package negocio;

import java.time.Instant;
import java.util.Date;

import basedados.FachadaBaseDados;
import dto.Livro;
import dto.Promocao;
import dto.Usuario;

public class GerenciadorPreco {
	private final static double DESCONTO_ANIVERSARIO = 0.7;
	private FachadaBaseDados baseDados;
	
	public GerenciadorPreco(FachadaBaseDados db) {
		this.baseDados = db;
	}
	
	public int calcularPrecoParaLivro(Livro livro, Promocao promocao,
									Usuario comprador, boolean cartaoClube) throws NegocioException {
		int preco = livro.getPreco();
		
		if (promocao != null) {
			preco = promocao.getPreco();
		}
		
		Date hoje = Date.from(Instant.now());
		Date aniversario = comprador.getDataNascimento();
		if (cartaoClube && hoje.getDay() == aniversario.getDay() && hoje.getMonth() == aniversario.getMonth()) {
			preco *= DESCONTO_ANIVERSARIO;
		}
		
		return preco;
	}
}
