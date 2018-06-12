package visualizacao.admin;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import controller.FrontController;
import controller.IAcceptRequests;
import controller.Pedido;
import dto.Usuario;
import visualizacao.principal.TelaBase;
import controller.FrontController.Request;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class TelaAdmin extends TelaBase {

	private JPanel contentPane;
	private FrontController frontController;
	private JLabel lblNomeusuario;

	/**
	 * Create the frame.
	 */
	public TelaAdmin(FrontController frontController) {
		setTitle("ADMIN");
		this.frontController = frontController;
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblBemvindoDeVolta = new JLabel("Bem-vindo de volta,");
		lblBemvindoDeVolta.setBounds(298, 11, 102, 14);
		contentPane.add(lblBemvindoDeVolta);
		
		lblNomeusuario = new JLabel("nomeUsuario");
		lblNomeusuario.setBounds(298, 36, 116, 14);
		contentPane.add(lblNomeusuario);
		
		JButton btnPesquisar = new JButton("Pesquisar/Remover");
		btnPesquisar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				frontController.dispatchRequest(Request.ADMIN_ABRIR_PESQUISA, Pedido.criarNovoPedido(sessao));
			}
		});
		btnPesquisar.setBounds(36, 149, 188, 66);
		contentPane.add(btnPesquisar);
		
		JButton btnRegistrar = new JButton("Registrar");
		btnRegistrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				frontController.dispatchRequest(Request.ADMIN_ABRIR_TELA_REGISTRAR, Pedido.criarNovoPedido(sessao));
			}
		});
		btnRegistrar.setBounds(36, 80, 89, 66);
		contentPane.add(btnRegistrar);
		
		JButton btnNewButton_7 = new JButton("Sair");
		btnNewButton_7.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frontController.dispatchRequest(Request.ADMIN_SAIR, Pedido.criarNovoPedido(sessao));
				//fecharTela();
			}
		});
		btnNewButton_7.setBounds(335, 227, 89, 23);
		contentPane.add(btnNewButton_7);
		
		JPanel panel = new JPanel();
		panel.setBounds(274, 80, 140, 132);
		contentPane.add(panel);
		atualizarCampos();
	}

	private void atualizarCampos() {
		Usuario usuario = sessao.getUsuarioLogado();
		this.lblNomeusuario.setText(usuario.getNome());
	}
	
	private void fecharTela() {
		super.dispose();
	}

	@Override
	public void show(Pedido params) {
		atualizarCampos();
	}
}
