package negocio;

import java.sql.Date;
import java.util.List;

import utilidades.Log;
import dto.Usuario;
import basedados.BaseDadosException;
import basedados.FachadaBaseDados;

public class RegrasNegocioUsuario {
	private FachadaBaseDados fachadaBaseDados;
	
	public RegrasNegocioUsuario(FachadaBaseDados fachadaBaseDados) {
		this.fachadaBaseDados = fachadaBaseDados;
	}
	
	public void cadastraUsuario(int codigo, String nome, Date dataNascimento, String telefone, String cpf,
			String senha) throws NegocioException {
		if (buscaUsuarioPorNomeExato(nome) != null) {
			throw new NegocioException("Usuario com este nome ja existe!");
		}
		
		Usuario usuario = new Usuario(codigo, nome, dataNascimento, telefone, cpf, senha, 0, false, true);
		try {
			fachadaBaseDados.insereUsuario(usuario);
		} catch (BaseDadosException e) {
			Log.gravaLog(e);
			throw new NegocioException("Problemas no acesso ao banco de dados.");
		}
	}
	
	public void cadastraUsuario(int codigo, String nome, int dia, int mes, int ano, String telefone, String cpf,
			String senha) throws NegocioException {
		cadastraUsuario(codigo, nome, new Date(ano, mes, dia), telefone, cpf, senha);
	}
	
	public List<Usuario> listaUsuarios() throws NegocioException {
		try {
			return fachadaBaseDados.listaUsuarios();
		} catch (BaseDadosException e) {
			Log.gravaLog(e);
			throw new NegocioException("Problemas no acesso ao banco de dados.");
		}
	}
	
	public Usuario buscaUsuarioPorNomeExato(String nome) throws NegocioException {
		try {
			return fachadaBaseDados.buscaUsuarioPorNomeExato(nome);
		} catch (BaseDadosException e) {
			Log.gravaLog(e);
			throw new NegocioException("Problemas no acesso ao banco de dados.");
		}
	}
	
	public List<Usuario> buscaUsuariosPorNome(String nome) throws NegocioException {
		try {
			return fachadaBaseDados.buscaUsuariosPorNome(nome);
		} catch (BaseDadosException e) {
			Log.gravaLog(e);
			throw new NegocioException("Problemas no acesso ao banco de dados.");
		}
	}

	////////////// BUSCA USUARIO ////////////////////
	public Usuario buscaUsuario(int codigo) throws NegocioException {
		try {
			return fachadaBaseDados.buscaUsuario(codigo);
		} catch (BaseDadosException e) {
			Log.gravaLog(e);
			throw new NegocioException("Problemas no acesso ao banco de dados.");
		}
	}
	
	public void desativaUsuario(int codigoUsuario) throws NegocioException {
		if (codigoUsuario == 1) {
			throw new NegocioException("Admin nao pode ser desativado.");
		}

		try {
			Usuario usuario = buscaUsuario(codigoUsuario);
			if (usuario.isAtivado()) {
				usuario.setAtivado(false);
				fachadaBaseDados.alteraUsuario(usuario);
			}
			else {
				throw new NegocioException("Usuario ja esta Desativado.");
			}
		} catch (BaseDadosException e) {
			Log.gravaLog(e);
			throw new NegocioException("Problemas no acesso ao banco de dados.");
		}
	}


	public void ativaUsuario(int codigoUsuario) throws NegocioException {
		try {
			Usuario usuario = buscaUsuario(codigoUsuario);
			if (usuario.isAtivado() == false) {
				usuario.setAtivado(true);
				fachadaBaseDados.alteraUsuario(usuario);
			}
			else {
				throw new NegocioException("Usuario ja esta Ativado.");
			}
		} catch (BaseDadosException e) {
			Log.gravaLog(e);
			throw new NegocioException("Problemas no acesso ao banco de dados.");
		}
	}
	
	public void reativaUsuarioNoLogin(Usuario usuario) throws NegocioException {
		try {
			usuario.setSaldoCartaoClube((int) (0.8 * usuario.getSaldoCartaoClube()));
			usuario.setAtivado(true);
			
			fachadaBaseDados.alteraUsuario(usuario);
		} catch (BaseDadosException e) {
			Log.gravaLog(e);
			throw new NegocioException("Problemas no acesso ao banco de dados.");
		}
	}
}
