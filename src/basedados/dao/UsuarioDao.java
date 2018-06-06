package basedados.dao;

import java.util.List;

import basedados.BaseDadosException;
import dto.Usuario;

public interface UsuarioDao {
	public void insere(Usuario usuario) throws BaseDadosException;
	public Usuario busca(int codigoUsuario) throws BaseDadosException;
	public List<Usuario> listaTodos() throws BaseDadosException;
	public void remove(int codigoUsuario)throws BaseDadosException;
	public List<Usuario> buscaPorNome(String usuario) throws BaseDadosException;
	public Usuario buscaPorNomeExato(String nome) throws BaseDadosException;
}
