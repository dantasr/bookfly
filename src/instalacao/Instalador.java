package instalacao;

import javax.swing.JOptionPane;

import basedados.BaseDadosException;

public class Instalador {
	public static void main(String[] args) {
		try {
			new ScriptCriacaoDB().criaTabelasBD();
			new ScriptCriacaoElo().criaTabelasDB();
			new ScriptCriacaoMastercard().criaTabelasDB();
			new ScriptCriacaoVisa().criaTabelasDB();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Erro ao criar o banco de dados.\n" + e.getMessage());
			return;
		}
		
		JOptionPane.showMessageDialog(null, "Banco de dados criado com sucesso!");
	}
}
