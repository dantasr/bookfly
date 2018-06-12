package visualizacao.principal;

import java.util.HashMap;

import javax.swing.JFrame;

import controller.IAcceptRequests;
import controller.Pedido;

public abstract class TelaBase extends JFrame implements IAcceptRequests {
	protected Pedido sessao;
	public abstract void show(Pedido params);
	
	public void acceptRequest(Pedido params) {
		sessao = params;
		show(sessao);
	}
}
