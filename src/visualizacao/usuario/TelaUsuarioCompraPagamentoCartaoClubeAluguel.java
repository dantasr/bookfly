package visualizacao.usuario;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import controller.FrontController;
import controller.FrontController.Request;
import dto.Livro;
import dto.Usuario;
import dto.Venda;
import negocio.NegocioException;
import utilidades.Log;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.Color;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.awt.event.ActionEvent;

public class TelaUsuarioCompraPagamentoCartaoClubeAluguel extends JFrame {

	private JPanel contentPane;
	private JLabel lblAvisoSemSaldo;
	private Livro livro;
	private FrontController frontController;

	/**
	 * Create the frame.
	 */
	public TelaUsuarioCompraPagamentoCartaoClubeAluguel(FrontController frontController, Livro livro, int saldo, int valorParcela, int valorFinal) {
		this.frontController = frontController;
		this.livro = livro;
		
		setTitle("BOOKFLY");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblCartoclube = new JLabel("Cart\u00E3o-clube");
		lblCartoclube.setFont(new Font("Agency FB", Font.BOLD, 18));
		lblCartoclube.setBounds(31, 31, 158, 14);
		contentPane.add(lblCartoclube);
		
		JLabel lblSeuSaldo = new JLabel("Seu saldo = ");
		lblSeuSaldo.setBounds(31, 67, 65, 14);
		contentPane.add(lblSeuSaldo);
		
		JLabel lblPreoDoProduto = new JLabel("Pre\u00E7o do produto = ");
		lblPreoDoProduto.setBounds(30, 99, 106, 14);
		contentPane.add(lblPreoDoProduto);
		
		JLabel lblSeuSaldoFinal = new JLabel("Seu saldo final = ");
		lblSeuSaldoFinal.setBounds(31, 134, 83, 14);
		contentPane.add(lblSeuSaldoFinal);
		
		if(valorFinal < 0) {
			lblAvisoSemSaldo = new JLabel("Saldo insuficiente!");
			lblAvisoSemSaldo.setForeground(Color.RED);
			lblAvisoSemSaldo.setBounds(31, 159, 119, 14);
			contentPane.add(lblAvisoSemSaldo);

			JButton btnNewButton = new JButton("Adicionar saldo...");
			btnNewButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					adicionaSaldo();
				}
			});
			btnNewButton.setBounds(25, 173, 124, 23);
			contentPane.add(btnNewButton);
		}
		else {
			JButton button = new JButton("Comprar");
			button.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					realizaCompra();
				}
			});
			button.setBounds(25, 203, 124, 47);
			contentPane.add(button);
		}
		
		JButton btnNewButton_1 = new JButton("Voltar");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				fecharTela();
			}
		});
		btnNewButton_1.setBounds(335, 227, 89, 23);
		contentPane.add(btnNewButton_1);
		
		JLabel lblValorsaldo = new JLabel(saldo + " R$");
		lblValorsaldo.setBounds(90, 67, 140, 14);
		contentPane.add(lblValorsaldo);
		
		JLabel lblValorparcela = new JLabel(valorParcela + " R$/por dia");
		lblValorparcela.setBounds(128, 99, 119, 14);
		contentPane.add(lblValorparcela);
		
		JLabel lblValorfinal = new JLabel(valorFinal + " R$");
		lblValorfinal.setBounds(113, 134, 100, 14);
		contentPane.add(lblValorfinal);
		
		atualizarCampos();
	}
	
	private void adicionaSaldo() {
		new TelaUsuarioInsereCredito(frontController, () -> this.atualizarCampos()).setVisible(true);;
	}

	private void realizaCompra() {
		HashMap<String, Object> hashMap = new HashMap<String, Object>();
		hashMap.put("usuario", usuario);
		hashMap.put("livro", livro);
		frontController.dispatchRequest(Request.USUARIO_REALIZA_ALUGUEL_CARTAO_CLUBE, hashMap);
		
		Usuario atual = frontController.getUsuarioAtual();
		int precoLivro = 0;
		try {
			precoLivro = frontController.getGerenciadorPreco().calcularPrecoParaLivro(livro, null, atual, true);
		} catch (NegocioException e) {
			Log.gravaLog(e);
			JOptionPane.showMessageDialog(null, e.getMessage());
			return;
		}
		
		if (precoLivro > atual.getSaldoCartaoClube()) {
			JOptionPane.showMessageDialog(null, "Você não possui saldo!");
			return;
		}
		
		try {
			frontController.getGerenciadorRegrasNegocio().vendeLivroCartaoClube(atual, livro, precoLivro);
			JOptionPane.showMessageDialog(null, "Comprado com sucesso!");
			setVisible(false);
		} catch (NegocioException e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
	}

	private void atualizarCampos() {
		Usuario atual = frontController.getUsuarioAtual();
		int precoLivro = 0;
		try {
			precoLivro = frontController.getGerenciadorPreco().calcularPrecoParaLivro(livro, null, atual, true);
		} catch (NegocioException e) {
			Log.gravaLog(e);
			JOptionPane.showMessageDialog(null, e.getMessage());
			return;
		}
		if (atual.getSaldoCartaoClube() - precoLivro < 0) {
			lblAvisoSemSaldo.setVisible(true);
		}
	}

	private void fecharTela() {
		super.dispose();
	}
}
