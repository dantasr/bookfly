package main;

import javax.swing.JOptionPane;
import javax.swing.UIManager;

import basedados.BaseDadosException;
import controller.FrontController;
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
		} catch (BaseDadosException ex) {
			Log.gravaLog(ex);

			JOptionPane.showMessageDialog(null, "Erro na inicialização.");
			return;
		}

		TelaLogin telaLogin = new TelaLogin(frontController);
		telaLogin.setVisible(true);
	}

}
