package cartoes;

import java.sql.SQLException;

import basedados.BaseDadosException;

public class GerenciadorVisa extends GerenciadorCartoes {
	public GerenciadorVisa() throws BaseDadosException {
		super();
	}

	@Override
	public String getNomeOperadoraCartao() {
		return "Visa";
	}

	@Override
	protected void populaTabelas() throws BaseDadosException, SQLException {
		// Já populada.
		if (buscaCartao(333333333) != null) return;
		
		insereCartao(new Cartao(333333333, 21, 2, 123, 50000));
		insereCartao(new Cartao(444444444, 22, 5, 999, 0));
	}
}
