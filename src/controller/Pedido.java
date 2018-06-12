package controller;

import java.util.HashMap;

import dto.Usuario;

public class Pedido extends HashMap<String, Object> {
	private Usuario usuarioLogado;
	
	public static Pedido criarNovoPedido(Pedido original) {
		Pedido novoX = new Pedido();
		novoX.setUsuarioLogado(original.getUsuarioLogado());
		return novoX;
	}

	public Usuario getUsuarioLogado() {
		return usuarioLogado;
	}

	public void setUsuarioLogado(Usuario usuarioLogado) {
		this.usuarioLogado = usuarioLogado;
	}
}
