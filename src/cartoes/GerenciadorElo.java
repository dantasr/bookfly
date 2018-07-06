package cartoes;

import java.sql.SQLException;

import basedados.BaseDadosException;

public class GerenciadorElo extends GerenciadorCartoes {

	public GerenciadorElo() throws BaseDadosException {
		super();
	}

	@Override
	public String getNomeOperadoraCartao() {
		return "ELO";
	}
}
