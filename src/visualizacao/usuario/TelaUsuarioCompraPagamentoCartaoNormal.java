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
import dto.Livro;
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
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.awt.event.ActionEvent;

public class TelaUsuarioCompraPagamentoCartaoNormal extends TelaBase {

	private JPanel contentPane;
	private JTextField campoNumeroCartao;
	private JTextField campoMes;
	private JTextField campoAno;
	private JTextField campoCvv;
	private JRadioButton radioVisa;
	private JRadioButton radioMaster;
	private JRadioButton radioElo;
	private FrontController frontController;
	private Livro livro;
	private Validador validador = new Validador();

	/**
	 * Launch the application.
	 */
	public TelaUsuarioCompraPagamentoCartaoNormal(FrontController frontController, Livro livro) {
		this.frontController = frontController;
		this.livro = livro;
		
		setTitle("BOOKFLY");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblCartoComum = new JLabel("Cart\u00E3o comum");
		lblCartoComum.setFont(new Font("Agency FB", Font.BOLD, 18));
		lblCartoComum.setBounds(36, 26, 147, 14);
		contentPane.add(lblCartoComum);
		
		campoNumeroCartao = new JTextField();
		campoNumeroCartao.setText("N\u00FAmero do cart\u00E3o");
		campoNumeroCartao.setForeground(Color.BLACK);
		campoNumeroCartao.setColumns(10);
		campoNumeroCartao.setBounds(36, 53, 115, 20);
		contentPane.add(campoNumeroCartao);
		
		campoMes = new JTextField();
		campoMes.setText("MM");
		campoMes.setColumns(10);
		campoMes.setBounds(35, 84, 28, 20);
		contentPane.add(campoMes);
		
		campoAno = new JTextField();
		campoAno.setText("YY");
		campoAno.setColumns(10);
		campoAno.setBounds(73, 84, 28, 20);
		contentPane.add(campoAno);
		
		campoCvv = new JTextField();
		campoCvv.setText("CVV");
		campoCvv.setColumns(10);
		campoCvv.setBounds(111, 84, 38, 20);
		contentPane.add(campoCvv);
		
		JLabel label = new JLabel("Forma de pagamento:");
		label.setBounds(213, 56, 158, 14);
		contentPane.add(label);
		
		radioVisa = new JRadioButton("VISA");
		radioVisa.setSelected(true);
		radioVisa.setBounds(223, 77, 109, 23);
		contentPane.add(radioVisa);
		
		radioMaster = new JRadioButton("MASTER CARD");
		radioMaster.setBounds(223, 108, 109, 23);
		contentPane.add(radioMaster);
		
		radioElo = new JRadioButton("ELO");
		radioElo.setBounds(223, 139, 109, 23);
		contentPane.add(radioElo);
		
		JButton btnComprar = new JButton("Comprar");
		btnComprar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				realizaCompra();
			}
		});
		btnComprar.setBounds(36, 197, 115, 53);
		contentPane.add(btnComprar);
		
		JLabel lblValorDoProduto = new JLabel("Valor do produto:");
		lblValorDoProduto.setBounds(36, 146, 115, 14);
		contentPane.add(lblValorDoProduto);
		
		JLabel lblValorproduto = new JLabel("" + livro.getPreco());
		lblValorproduto.setBounds(129, 148, 115, 14);
		contentPane.add(lblValorproduto);
		
		JButton btnVoltar = new JButton("Voltar");
		btnVoltar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				fecharTela();
			}
		});
		btnVoltar.setBounds(335, 227, 89, 23);
		contentPane.add(btnVoltar);
	}
	
	private String obtemGerenciadorCartoes() {
		if (radioMaster.isSelected())
			return "MasterCard";
		else if (radioVisa.isSelected())
			return "Visa";
		else if (radioElo.isSelected())
			return "Elo";
		
		return null;
	}
	
	private void realizaCompra() {
		int codigo, ano, mes, cvv;
		
		try {
			codigo = validador.validaFormataInteiro(campoNumeroCartao.getText(), "Numero do Cartao");
			ano = validador.validaFormataInteiro(campoAno.getText(), "Ano");
			mes = validador.validaFormataInteiro(campoMes.getText(), "Mes");
			cvv = validador.validaFormataInteiro(campoCvv.getText(), "CVV");
		} catch (ValidacaoException e) {
			Log.gravaLog(e);
			JOptionPane.showMessageDialog(null, e.getMessage());
			
			return;
		}
		
		String gerenciadorCartoes = obtemGerenciadorCartoes();
		
		HashMap<String, Object> hashMap = new HashMap<String, Object>();
		hashMap.put("usuario", usuario);
		hashMap.put("livro", livro);
		hashMap.put("cartaoCodigo", codigo);
		hashMap.put("cartaoAno", ano);
		hashMap.put("cartaoMes", mes);
		
		frontController.dispatchRequest(Request.USUARIO_REALIZA_COMPRA_CARTAO_NORMAL, hashMap);
		
		Cartao cartao = null;
		try {
			cartao = gerenciadorCartoes.buscaCartaoComCampos(codigo, ano, mes, cvv);
		} catch (BaseDadosException e) {
			Log.gravaLog(e);
			JOptionPane.showMessageDialog(null, e.getMessage());
			
			return;
		}
		
		Usuario atual = frontController.getUsuarioAtual();
		int precoLivro;
		try {
			precoLivro = frontController.getGerenciadorPreco().calcularPrecoParaLivro(livro, null, atual, false);
		} catch (NegocioException e) {
			Log.gravaLog(e);
			JOptionPane.showMessageDialog(null, e.getMessage());
			
			return;
		}
		
		if (cartao == null) {
			JOptionPane.showMessageDialog(null, "N�o existe cart�o com estes campos!");
			return;
		}
		
		try {
			frontController.getGerenciadorRegrasNegocio().vendeLivroCartaoNormal(gerenciadorCartoes, cartao, atual, livro, precoLivro);
			JOptionPane.showMessageDialog(null, "Livro comprado com sucesso!");
			setVisible(false);
		} catch (NegocioException e) {
			Log.gravaLog(e);
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
	}

	private void fecharTela() {
		super.dispose();
	}
}
