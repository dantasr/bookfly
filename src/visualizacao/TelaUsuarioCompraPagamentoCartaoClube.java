package visualizacao;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import beans.Livro;
import beans.Promocao;
import beans.Usuario;
import beans.Venda;
import main.Contexto;
import negocio.NegocioException;
import utilidades.Log;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.Color;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class TelaUsuarioCompraPagamentoCartaoClube extends JFrame {

	private JPanel contentPane;
	private JLabel lblAvisoSemSaldo;
	private Livro livro;
	private Promocao promocao;
	private Contexto contexto;
	private JLabel lblValorsaldo;
	private JLabel lblValorproduto;
	private JLabel lblValorfinal;
	private JButton btnAdicionaSaldo;
	private JButton btnRealizaCompra;

	/**
	 * Create the frame.
	 */	
	public TelaUsuarioCompraPagamentoCartaoClube(Contexto contexto, Livro livro, Promocao promocao) {
		this.contexto = contexto;
		this.livro = livro;
		this.promocao = promocao;

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

		lblAvisoSemSaldo = new JLabel("Saldo insuficiente!");
		lblAvisoSemSaldo.setForeground(Color.RED);
		lblAvisoSemSaldo.setBounds(31, 159, 119, 14);
		contentPane.add(lblAvisoSemSaldo);

		JButton btnNewButton_1 = new JButton("Voltar");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				fecharTela();
			}
		});
		btnNewButton_1.setBounds(335, 227, 89, 23);
		contentPane.add(btnNewButton_1);

		lblValorsaldo = new JLabel();
		lblValorsaldo.setBounds(90, 67, 111, 14);
		contentPane.add(lblValorsaldo);

		Usuario atual = contexto.getUsuarioAtual();
		int preco = 0;
		try {
			preco = contexto.getGerenciadorPreco().calcularPrecoParaLivro(livro, promocao, atual, true);
		} catch (NegocioException e1) {
			JOptionPane.showMessageDialog(null, "Erro ao calcular preco");
		}
		
		lblValorproduto = new JLabel(preco + " R$");
		lblValorproduto.setBounds(130, 99, 95, 14);
		contentPane.add(lblValorproduto);

		
		lblValorfinal = new JLabel();
		lblValorfinal.setBounds(124, 134, 101, 14);
		contentPane.add(lblValorfinal);

		int valorFinalC = contexto.getUsuarioAtual().getSaldoCartaoClube() - preco;
		btnAdicionaSaldo = new JButton("Adicionar saldo...");
		btnAdicionaSaldo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				adicionaSaldo();
			}
		});
		btnAdicionaSaldo.setBounds(25, 173, 124, 23);
		contentPane.add(btnAdicionaSaldo);
		
		btnRealizaCompra = new JButton("Comprar");
		btnRealizaCompra.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				realizaCompra();
			}
		});
		btnRealizaCompra.setBounds(25, 203, 124, 47);
		contentPane.add(btnRealizaCompra);
		
		if (valorFinalC < 0) {
			btnRealizaCompra.setEnabled(false);
		}

		atualizarCampos();
	}


	private void adicionaSaldo() {
		new TelaUsuarioInsereCredito(contexto, () -> this.atualizarCampos()).setVisible(true);;
	}

	private void realizaCompra() {
		Usuario atual = contexto.getUsuarioAtual();
		int precoLivro = 0;
		try {
			precoLivro = contexto.getGerenciadorPreco().calcularPrecoParaLivro(livro, promocao, atual, true);
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
			contexto.getGerenciadorRegrasNegocio().vendeLivroCartaoClube(atual, livro, precoLivro);
			JOptionPane.showMessageDialog(null, "Comprado com sucesso!");
			setVisible(false);
		} catch (NegocioException e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
	}

	private void atualizarCampos() {
		Usuario atual = contexto.getUsuarioAtual();
		int precoLivro = 0;
		try {
			precoLivro = contexto.getGerenciadorPreco().calcularPrecoParaLivro(livro, promocao, atual, true);
		} catch (NegocioException e) {
			Log.gravaLog(e);
			JOptionPane.showMessageDialog(null, e.getMessage());
			return;
		}
		
		String saldoAtual = "" + contexto.getUsuarioAtual().getSaldoCartaoClube();
		lblValorsaldo.setText(saldoAtual + " R$");
		int valorF = contexto.getUsuarioAtual().getSaldoCartaoClube() - precoLivro;
		lblValorfinal.setText(valorF + " R$");
		
		if (atual.getSaldoCartaoClube() - precoLivro < 0) {
			lblAvisoSemSaldo.setVisible(true);
			btnRealizaCompra.setEnabled(false);
		} else {
			lblAvisoSemSaldo.setVisible(false);
			btnRealizaCompra.setEnabled(true);
		}
	}

	private void fecharTela() {
		super.dispose();
	}
}
