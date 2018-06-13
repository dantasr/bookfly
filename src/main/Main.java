package main;

import javax.swing.JOptionPane;
import javax.swing.UIManager;

import basedados.BaseDadosException;
import controller.FrontController;
import controller.FrontController.Request;
import utilidades.Log;
import visualizacao.principal.TelaLogin;

public class Main {

	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
		}
		
		FrontController frontController;
		try {
			frontController = new FrontController();
			frontController.dispatchRequest(Request.LOGIN_EXIBE_LOGIN);
		} catch (BaseDadosException e) {
			JOptionPane.showMessageDialog(null, "Erro ao inicializar o sistema.\n" + e.getMessage());
		}
	}

}
