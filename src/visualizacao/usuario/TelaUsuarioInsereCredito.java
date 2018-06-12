package visualizacao.usuario;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import basedados.BaseDadosException;
import cartoes.Cartao;
import cartoes.GerenciadorCartoes;
import controller.FrontController;
import controller.FrontController.Request;
import dto.Usuario;
import negocio.NegocioException;
import utilidades.Log;
import utilidades.ValidacaoException;
import utilidades.Validador;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JTextField;
import java.awt.Color;
import javax.swing.JRadioButton;
import javax.swing.JButton;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.ButtonGroup;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.function.Function;
import java.awt.event.ActionEvent;

public class TelaUsuarioInsereCredito extends TelaBase {

	private JPanel contentPane;
	private JTextField txtNmeroDoCarto;
	private JTextField txtMm;
	private JTextField txtYy;
	private JTextField txtCvv;
	private JTextField txtQuantidade;
	private JRadioButton rdbtnVisa;
	private JRadioButton rdbtnMasterCard;
	private JRadioButton rdbtnElo;
	private final ButtonGroup buttonGroupFormaPagamento = new ButtonGroup();
	private FrontController frontController;
	private Validador validador = new Validador();
	private Runnable callbackCompra;
	private JLabel lblValorsaldo;

	/**
	 * Create the frame.
	 */
	public TelaUsuarioInsereCredito(FrontController frontController, Runnable callbackCompra) {
		this.frontController = frontController;
		this.callbackCompra = callbackCompra;
		
		setTitle("BOOKFLY");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel label = new JLabel("Cart\u00E3o-clube");
		label.setFont(new Font("Agency FB", Font.BOLD, 18));
		label.setBounds(31, 31, 147, 14);
		contentPane.add(label);
		
		JLabel lblSeuSaldo = new JLabel("Seu saldo = ");
		lblSeuSaldo.setBounds(31, 60, 77, 14);
		contentPane.add(lblSeuSaldo);
		
		lblValorsaldo = new JLabel("valorSaldo");
		lblValorsaldo.setBounds(96, 60, 82, 14);
		contentPane.add(lblValorsaldo);
		
		txtNmeroDoCarto = new JTextField();
		txtNmeroDoCarto.setForeground(Color.BLACK);
		txtNmeroDoCarto.setText("N\u00FAmero do cart\u00E3o");
		txtNmeroDoCarto.setBounds(31, 81, 115, 20);
		contentPane.add(txtNmeroDoCarto);
		txtNmeroDoCarto.setColumns(10);
		
		txtMm = new JTextField();
		txtMm.setText("MM");
		txtMm.setBounds(31, 111, 28, 20);
		contentPane.add(txtMm);
		txtMm.setColumns(10);
		
		txtYy = new JTextField();
		txtYy.setText("YY");
		txtYy.setColumns(10);
		txtYy.setBounds(69, 111, 28, 20);
		contentPane.add(txtYy);
		
		txtCvv = new JTextField();
		txtCvv.setText("CVV");
		txtCvv.setColumns(10);
		txtCvv.setBounds(108, 111, 38, 20);
		contentPane.add(txtCvv);
		
		JLabel lblQuantidadeDesejada = new JLabel("Valor desejado: ");
		lblQuantidadeDesejada.setBounds(31, 174, 115, 14);
		contentPane.add(lblQuantidadeDesejada);
		
		txtQuantidade = new JTextField();
		txtQuantidade.setBounds(115, 171, 86, 20);
		contentPane.add(txtQuantidade);
		txtQuantidade.setColumns(10);
		
		JLabel lblFormaDePagamento = new JLabel("Forma de pagamento:");
		lblFormaDePagamento.setBounds(250, 60, 158, 14);
		contentPane.add(lblFormaDePagamento);
		
		rdbtnVisa = new JRadioButton("VISA");
		rdbtnVisa.setSelected(true);
		buttonGroupFormaPagamento.add(rdbtnVisa);
		rdbtnVisa.setBounds(260, 80, 109, 23);
		contentPane.add(rdbtnVisa);
		
		rdbtnMasterCard = new JRadioButton("MASTER CARD");
		buttonGroupFormaPagamento.add(rdbtnMasterCard);
		rdbtnMasterCard.setBounds(260, 110, 109, 23);
		contentPane.add(rdbtnMasterCard);
		
		rdbtnElo = new JRadioButton("ELO");
		buttonGroupFormaPagamento.add(rdbtnElo);
		rdbtnElo.setBounds(260, 143, 109, 23);
		contentPane.add(rdbtnElo);
		
		JButton btnConfirmar = new JButton("Confirmar");
		btnConfirmar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				insereCredito();
			}
		});
		btnConfirmar.setBounds(31, 199, 89, 23);
		contentPane.add(btnConfirmar);
		atualizaCampos();
	}

	private void atualizaCampos() {
		lblValorsaldo.setText("" + frontController.getUsuarioAtual().getSaldoCartaoClube());
	}

	private String obtemGerenciadorCartoes() {
		if (rdbtnMasterCard.isSelected())
			return "MasterCard";
		else if (rdbtnVisa.isSelected())
			return "Visa";
		else if (rdbtnElo.isSelected())
			return "Elo";
		
		return null;
	}
	
	private void insereCredito() {
		int codigo, ano, mes, cvv, valor;
		
		try {
			codigo = validador.validaFormataInteiro(txtNmeroDoCarto.getText(), "Numero do Cartao");
			ano = validador.validaFormataInteiro(txtYy.getText(), "Ano");
			mes = validador.validaFormataInteiro(txtMm.getText(), "Mes");
			cvv = validador.validaFormataInteiro(txtCvv.getText(), "CVV");
			valor = validador.validaFormataInteiro(txtQuantidade.getText(), "Quantidade");
		} catch (ValidacaoException e) {
			Log.gravaLog(e);
			JOptionPane.showMessageDialog(null, e.getMessage());
			
			return;
		}
		
		String gerenciadorCartoes = obtemGerenciadorCartoes();
		
		HashMap<String, Object> hashMap = new HashMap<String, Object>();
		hashMap.put("usuario", usuario);
		hashMap.put("cartaoCodigo", codigo);
		hashMap.put("cartaoAno", ano);
		hashMap.put("cartaoMes", mes);
		hashMap.put("cartaoOperadora", gerenciadorCartoes);
		
		frontController.dispatchRequest(Request.USUARIO_INSERE_CREDITO, hashMap);
		Cartao cartao = null;
		try {
			cartao = gerenciadorCartoes.buscaCartaoComCampos(codigo, ano, mes, cvv);
		} catch (BaseDadosException e) {
			Log.gravaLog(e);
			JOptionPane.showMessageDialog(null, e.getMessage());
			
			return;
		}
		
		Usuario atual = frontController.getUsuarioAtual();
		try {
			frontController.getGerenciadorRegrasNegocio().insereCreditoCartaoClube(gerenciadorCartoes, cartao, atual, valor);
			JOptionPane.showMessageDialog(null, "Cr�dito inserido com sucesso!");
			this.setVisible(false);
			this.callbackCompra.run();
		} catch (NegocioException e) {
			Log.gravaLog(e);
			JOptionPane.showMessageDialog(null, e.getMessage());
			
			return;
		}
	}
}
