package visualizacao;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTable;
import javax.swing.JLabel;

public class TelaUsuarioMeuHistorico extends JFrame {

	private JPanel contentPane;
	private JTable tableListaHistorico;

	/**
	 * Create the frame.
	 */
	public TelaUsuarioMeuHistorico() {
		setTitle("BOOKFLY");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		tableListaHistorico = new JTable();
		tableListaHistorico.setBounds(10, 37, 414, 213);
		contentPane.add(tableListaHistorico);
		
		JLabel lblMeuHistrico = new JLabel("Meu Hist\u00F3rico");
		lblMeuHistrico.setBounds(10, 11, 89, 14);
		contentPane.add(lblMeuHistrico);
	}

}
