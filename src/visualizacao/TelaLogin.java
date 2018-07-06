package visualizacao;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import dto.Usuario;
import main.Contexto;
import negocio.NegocioException;
import utilidades.Log;

import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.Timestamp;
import java.time.Duration;
import java.time.Instant;
import javax.swing.JPasswordField;

public class TelaLogin extends JFrame {
	/**
	 * 
	 */
	private Contexto contexto;
	private JPanel contentPane;
	private JTextField login;
	private JPasswordField senha;

	/**
	 * Create the frame.
	 */
	public TelaLogin(Contexto contexto) {
		this.contexto = contexto;

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
				new TelaCadastro(contexto).setVisible(true);
			}
		});
		btnCadastrar.setBounds(151, 121, 89, 23);
		contentPane.add(btnCadastrar);
		
		
	}

	private void realizarLogin() {
		if (login.getText().isEmpty() || senha.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha os campos!");
			return;
		}
		
		try {
			Usuario usuario = contexto.getFachadaRegrasNegocio().buscaUsuarioPorNomeExato(login.getText());
			if (usuario == null) {
				JOptionPane.showMessageDialog(null, "Usuario não existe!");
				return;
			}
			
			Timestamp umMesAtras = Timestamp.from(Instant.now().minus(Duration.ofDays(30)));
			Timestamp ultimoLogin = usuario.getUltimoLogin();

			// Se for < 0, o ultimo login foi antes de um mes atras.
			if (!usuario.isAtivado() || ultimoLogin.compareTo(umMesAtras) < 0) {
				JOptionPane.showMessageDialog(null, "Usuario estava desativado! 20% do seu saldo foi removido!");
				
				contexto.getFachadaRegrasNegocio().reativaUsuarioNoLogin(usuario);
			}
			
			if (!senha.getText().equals(usuario.getSenha())) {
				JOptionPane.showMessageDialog(null, "Senha incorreta!");
				return;
			}

			// Login feito com sucesso, trocar para tela de administrador ou cliente, dependendo do
			// tipo do usuário.
			contexto.getFachadaRegrasNegocio().atualizaTempoDeUltimoLogin(usuario);
			contexto.setUsuarioAtual(usuario);
			this.setVisible(false);

			if (usuario.isAdministrador()) {
				new TelaAdmin(contexto).setVisible(true);
			} else {
				new TelaUsuario(contexto).setVisible(true);
			}
		} catch (NegocioException e) {
			Log.gravaLog(e);
			JOptionPane.showMessageDialog(null, "Erro no banco de dados.");
		}
	}
}
