package visualizacao.usuario;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import controller.FrontController;
import controller.FrontController.Request;
import controller.Pedido;
import dto.Livro;

import javax.swing.JLabel;

import java.awt.Color;

import javax.swing.JButton;

import visualizacao.principal.TelaBase;

import java.awt.Font;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.awt.event.ActionEvent;

public class TelaUsuarioAluga extends TelaBase {

	private JPanel contentPane;
	private Livro livro;
	private FrontController frontController;
	
	public TelaUsuarioAluga(FrontController frontController) {
		this.frontController = frontController;
		
		setTitle("BOOKFLY");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		JLabel lblAoAlugarUm_1 = new JLabel("Ao alugar um dos nossos produtos, \r\nvoc\u00EA ter\u00E1 direito de us\u00E1-lo livremente ");
		lblAoAlugarUm_1.setBounds(10, 35, 437, 14);
		contentPane.add(lblAoAlugarUm_1);
		int saldo = frontController.getUsuarioAtual().getSaldoCartaoClube();
		// TODO PRECO DO ALUGUEL
		
		JLabel lblNewLabel = new JLabel("durante um per\u00EDodo de 3 dias. \r\n Ap\u00F3s esse prazo,\r\n o produto ser\u00E1 automaticamente");
		lblNewLabel.setBounds(10, 49, 414, 14);
		contentPane.add(lblNewLabel);
		
		JLabel lblRemovidoDeSeu = new JLabel("removido de seu estoque.");
		lblRemovidoDeSeu.setBounds(10, 60, 152, 20);
		contentPane.add(lblRemovidoDeSeu);
		
		JLabel lblOPagamentoPoder = new JLabel("O pagamento poder\u00E1 ser feito apenas com cart\u00E3o-clube.");
		lblOPagamentoPoder.setBounds(137, 63, 287, 14);
		contentPane.add(lblOPagamentoPoder);
		
		JLabel lblPreoDoAluguel = new JLabel("Pre\u00E7o do aluguel = ");
		lblPreoDoAluguel.setBounds(10, 116, 106, 14);
		contentPane.add(lblPreoDoAluguel);
		
		JLabel lblValoraluguel = new JLabel(precoParcela + " R$/por dia");
		lblValoraluguel.setBounds(111, 116, 111, 14);
		contentPane.add(lblValoraluguel);
		
		JButton button = new JButton("Comprar");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				Pedido hashMap = Pedido.criarNovoPedido(sessao);
				hashMap.put("livro",livro);
				frontController.dispatchRequest(Request.USUARIO_EXIBE_TELA_PAGAMENTO_ALUGUEL, hashMap);				
			}
		});
		button.setBounds(10, 203, 119, 47);
		contentPane.add(button);
		
		JLabel lblAluguel = new JLabel("Aluguel");
		lblAluguel.setFont(new Font("Agency FB", Font.BOLD, 18));
		lblAluguel.setBounds(10, 0, 147, 27);
		contentPane.add(lblAluguel);
		
		JButton btnVoltar = new JButton("Voltar");
		btnVoltar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				fecharAluguel();
			}
		});
		btnVoltar.setBounds(322, 227, 89, 23);
		contentPane.add(btnVoltar);
	}	
	private void fecharAluguel() {
		super.dispose();
	}
	
	private void atualizaTela() {
		
	}
	
	@Override
	public void show(Pedido params) {
		this.livro = (Livro) params.get("livro");
		this.setVisible(true);
	}
}
