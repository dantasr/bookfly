package cartoes;

import java.sql.SQLException;

import basedados.BaseDadosException;

public class GerenciadorMastercard extends GerenciadorCartoes {
	public GerenciadorMastercard() throws BaseDadosException {
		super();
	}

	@Override
	public String getNomeOperadoraCartao() {
		return "MasterCard";
	}
}
