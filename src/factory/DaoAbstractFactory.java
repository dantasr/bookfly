package factory;

import basedados.BaseDadosException;
import basedados.dao.AluguelDao;
import basedados.dao.LivroDao;
import basedados.dao.PromocaoDao;
import basedados.dao.UsuarioDao;
import basedados.dao.VendaDao;

public interface DaoAbstractFactory {
	AluguelDao criaAluguelDao(LivroDao livroDao, UsuarioDao usuarioDao) throws BaseDadosException;
	LivroDao criaLivroDao()  throws BaseDadosException;
	PromocaoDao criaPromocaoDao(LivroDao livroDao) throws BaseDadosException;
	UsuarioDao criaUsuarioDao() throws BaseDadosException;
	VendaDao criaVendaDao(LivroDao livroDao, UsuarioDao usuarioDao) throws BaseDadosException;
}
