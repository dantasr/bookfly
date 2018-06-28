package factory;

import basedados.BaseDadosException;
import basedados.dao.AluguelDao;
import basedados.dao.LivroDao;
import basedados.dao.PromocaoDao;
import basedados.dao.UsuarioDao;
import basedados.dao.VendaDao;
import basedados.dao.jdbc.AluguelDaoJdbc;
import basedados.dao.jdbc.LivroDaoJdbc;
import basedados.dao.jdbc.PromocaoDaoJdbc;
import basedados.dao.jdbc.UsuarioDaoJdbc;
import basedados.dao.jdbc.VendaDaoJdbc;

public class DaoJdbcFactory implements DaoAbstractFactory {
	@Override
	public AluguelDao criaAluguelDao(LivroDao livroDao, UsuarioDao usuarioDao) throws BaseDadosException {
		return new AluguelDaoJdbc(livroDao, usuarioDao);
	}

	@Override
	public LivroDao criaLivroDao() throws BaseDadosException {
		return new LivroDaoJdbc();
	}

	@Override
	public PromocaoDao criaPromocaoDao(LivroDao livroDao) throws BaseDadosException {
		return new PromocaoDaoJdbc(livroDao);
	}

	@Override
	public UsuarioDao criaUsuarioDao() throws BaseDadosException {
		return new UsuarioDaoJdbc();
	}

	@Override
	public VendaDao criaVendaDao(LivroDao livroDao, UsuarioDao usuarioDao) throws BaseDadosException {
		return new VendaDaoJdbc(livroDao, usuarioDao);
	}
}
