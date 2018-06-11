package visualizacao.principal;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import controller.FrontController;
import controller.FrontController.Request;
import dto.Usuario;
import negocio.NegocioException;
import utilidades.Log;

import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JRadioButton;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.HashMap;

import javax.swing.JPasswordField;

public class TelaLogin extends JFrame {
	/**
	 * 
	 */
	private FrontController frontController;
	private JPanel contentPane;
	private JTextField login;
	private JPasswordField senha;

	/**
	 * Create the frame.
	 */
	public TelaLogin(FrontController frontController) {
		this.frontController = frontController;

		setTitle("BOOKFLY");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 333, 214);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		login = new JTextField();
		login.setBounds(84, 59, 156, 20);
		contentPane.add(login);
		login.setColumns(10);

		senha = new JPasswordField();
		senha.setBounds(84, 90, 156, 20);
		contentPane.add(senha);
		senha.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent evt) {
				if(evt.getKeyCode() == KeyEvent.VK_ENTER){
					realizarLogin();
				}
			}
		});
		

		JLabel lblLogin = new JLabel("Login:");
		lblLogin.setBounds(48, 62, 46, 14);
		contentPane.add(lblLogin);

		JLabel lblSenha = new JLabel("Senha:");
		lblSenha.setBounds(48, 93, 46, 14);
		contentPane.add(lblSenha);

		JButton btnLogIn = new JButton("Log In");
		btnLogIn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				realizarLogin();
			}
		});
		btnLogIn.setBounds(48, 121, 89, 23);
		contentPane.add(btnLogIn);

		JLabel lblBookfly = new JLabel("BOOKFLY");
		lblBookfly.setFont(new Font("Agency FB", Font.PLAIN, 26));
		lblBookfly.setBounds(130, 11, 150, 40);
		contentPane.add(lblBookfly);

		JButton btnCadastrar = new JButton("Cadastrar");
		btnCadastrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				new TelaCadastro(frontController).setVisible(true);
			}
		});
		btnCadastrar.setBounds(151, 121, 89, 23);
		contentPane.add(btnCadastrar);
		
		
	}

	private void realizarLogin() {
		if (login.getText().isEmpty() || senha.getText().isEmpty()) {
			return;
		}
		
		HashMap<String, Object> hashMap = new HashMap<String, Object>();		
		hashMap.put("usuario", login.getText());
		hashMap.put("senha", senha.getText());
		frontController.dispatchRequest(Request.LOGIN_REALIZA_LOGIN, hashMap);
	}
}
