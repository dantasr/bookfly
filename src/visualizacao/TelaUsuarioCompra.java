package visualizacao;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import dto.Livro;
import main.Contexto;

import javax.swing.JLayeredPane;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextPane;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;
import java.awt.Canvas;

public class TelaUsuarioCompra extends JFrame {

	private JPanel contentPane;
	private final ButtonGroup buttonGroupFormaPagamento = new ButtonGroup();
	private Contexto contexto;
	private Livro livro;

	public TelaUsuarioCompra(Contexto contexto, Livro livro) {
		this.contexto = contexto;
		this.livro = livro;
		
		setTitle("BOOKFLY");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JLayeredPane layeredPane = new JLayeredPane();
		contentPane.add(layeredPane, BorderLayout.CENTER);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBounds(0, 0, 424, 251);
		layeredPane.add(panel_1);
		panel_1.setLayout(null);
		
		JLabel lblSelecioneAForma = new JLabel("Selecione a forma de pagamento:");
		lblSelecioneAForma.setBounds(22, 25, 186, 14);
		panel_1.add(lblSelecioneAForma);
		
		JLabel lblNewLabel = new JLabel("ou");
		lblNewLabel.setBounds(172, 108, 46, 14);
		panel_1.add(lblNewLabel);
		
		JLabel lblOCartoclubeTraz = new JLabel("Obs: o cart\u00E3o-clube traz descontos para os clientes");
		lblOCartoclubeTraz.setBounds(32, 166, 279, 14);
		panel_1.add(lblOCartoclubeTraz);
		
		JButton btnCartoClube = new JButton("Cart\u00E3o Clube");
		btnCartoClube.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				new TelaUsuarioCompraPagamentoCartaoClube(contexto, livro, null).setVisible(true);
				setVisible(false);
			}
		});
		btnCartoClube.setBounds(32, 76, 109, 79);
		panel_1.add(btnCartoClube);
		
		JButton btnCartoComum = new JButton("Cart\u00E3o comum");
		btnCartoComum.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new TelaUsuarioCompraPagamentoCartaoNormal(contexto, livro).setVisible(true);
				
				setVisible(false);
			}
		});
		btnCartoComum.setBounds(228, 76, 109, 79);
		panel_1.add(btnCartoComum);
		
		JButton btnVoltar = new JButton("Voltar");
		btnVoltar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				fecharTela();
			}
		});
		btnVoltar.setBounds(325, 217, 89, 23);
		panel_1.add(btnVoltar);
	}

	private void fecharTela() {
		super.dispose();
	}
}
