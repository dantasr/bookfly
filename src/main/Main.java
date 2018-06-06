package main;

import javax.swing.JOptionPane;
import javax.swing.UIManager;

import basedados.BaseDadosException;
import negocio.NegocioException;
import utilidades.Log;
import visualizacao.TelaLogin;

public class Main {

	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
		}
		
		Contexto contexto;
		try {
			contexto = new Contexto();
		} catch (BaseDadosException ex) {
			Log.gravaLog(ex);

			JOptionPane.showMessageDialog(null, "Erro na inicialização.");
			return;
		}

		TelaLogin telaLogin = new TelaLogin(contexto);
		telaLogin.setVisible(true);
	}

}
