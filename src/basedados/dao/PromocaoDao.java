package basedados.dao;

import java.util.List;

import basedados.BaseDadosException;
import dto.Promocao;

public interface PromocaoDao {
	public void insere(Promocao promocao) throws BaseDadosException;
	public Promocao busca(int codigoPromocao)throws BaseDadosException;
	public List<Promocao> listaTodos()throws BaseDadosException;
	public void remove(int codigoPromocao)throws BaseDadosException;
	public List<Promocao> buscaLista(int codigoPromocao) throws BaseDadosException;
}
