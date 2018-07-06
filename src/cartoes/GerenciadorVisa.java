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
}
