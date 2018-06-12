package visualizacao.admin;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import controller.FrontController;
import controller.IAcceptRequests;
import controller.Pedido;
import controller.FrontController.Request;
import dto.Usuario;
import visualizacao.principal.TelaBase;

import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.awt.event.ActionEvent;

public class TelaAdminPromocao extends TelaBase {

	private JPanel contentPane;
	private JTextField textField;
	int codigoLivro;
	
	public TelaAdminPromocao(FrontController frontController) {
		setTitle("ADMIN");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 275, 172);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblPromoo = new JLabel("Promo\u00E7\u00E3o:");
		lblPromoo.setBounds(10, 40, 73, 14);
		contentPane.add(lblPromoo);
		
		textField = new JTextField();
		textField.setBounds(68, 37, 86, 20);
		contentPane.add(textField);
		textField.setColumns(10);
		
		JButton btnNewButton = new JButton("Confirmar");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String promo = textField.getText();
				int valorF = Integer.parseInt(promo);

				Pedido pedido = Pedido.criarNovoPedido(sessao);
				pedido.put("codigoLivro", codigoLivro);
				pedido.put("valorPromo", valorF);
				frontController.dispatchRequest(Request.ADMIN_CADASTRA_PROMO, pedido);
			}
		});
		btnNewButton.setBounds(136, 79, 113, 43);
		contentPane.add(btnNewButton);
		
		JButton btnVoltar = new JButton("Voltar");
		btnVoltar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//fecharPromocao();
			}
		});
		btnVoltar.setBounds(10, 99, 89, 23);
		contentPane.add(btnVoltar);
	}
	public void fecharPromocao() {
		super.dispose();
	}
	
	@Override
	public void show(Pedido params) {
		codigoLivro = (int) params.get("codigoLivro");
		this.setVisible(true);
	}
}