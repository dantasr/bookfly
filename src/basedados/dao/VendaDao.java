package basedados.dao;

import java.util.List;

import basedados.BaseDadosException;
import dto.Usuario;
import dto.Venda;

public interface VendaDao {
	public void insere(Venda venda) throws BaseDadosException;
	public Venda busca(int codigo)  throws BaseDadosException;
	public List<Venda> listaTodos() throws BaseDadosException;
	
	public List<Venda> buscaDoUsuario(int codigoUsuario) throws BaseDadosException;
}
