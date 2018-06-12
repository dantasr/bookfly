package basedados.dao;

import java.util.List;

import basedados.BaseDadosException;
import dto.Aluguel;;

public interface AluguelDao {
	public void insere(Aluguel aluguel) throws BaseDadosException;
	public Aluguel busca(int codigoAluguel)throws BaseDadosException;
	public List<Aluguel> listaTodos()throws BaseDadosException;
	public void remove(int codigoAluguel)throws BaseDadosException;
	public List<Aluguel> buscaDoUsuario(int codigoUsuario) throws BaseDadosException;
}
