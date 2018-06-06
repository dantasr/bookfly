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

	@Override
	protected void populaTabelas() throws BaseDadosException, SQLException {
		// Já populada.
		if (buscaCartao(123456789) != null) return;
		
		insereCartao(new Cartao(123456789, 21, 2, 123, 50000));
		insereCartao(new Cartao(999999999, 22, 5, 999, 0));
	}
}
