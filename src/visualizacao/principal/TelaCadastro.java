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
import utilidades.ValidacaoException;
import utilidades.Validador;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.util.HashMap;
import java.awt.event.ActionEvent;
import javax.swing.JPasswordField;

public class TelaCadastro extends JFrame {

	private JPanel contentPane;
	private JTextField campoNome;
	private JTextField campoDataNascimento;
	private JTextField campoCpf;
	private JTextField campoTelefone;
	private FrontController frontController;
	private Validador validador = new Validador();
	private JPasswordField campoSenha;

	/**
	 * Create the frame.
	 */
	public TelaCadastro(FrontController frontController) {
		setTitle("CADASTRO");
		this.frontController = frontController;
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblCadastroDeUsurio = new JLabel("CADASTRO DE USU\u00C1RIO");
		lblCadastroDeUsurio.setBounds(10, 11, 156, 14);
		contentPane.add(lblCadastroDeUsurio);
		
		campoNome = new JTextField();
		campoNome.setBounds(121, 36, 86, 20);
		contentPane.add(campoNome);
		campoNome.setColumns(10);
		
		campoDataNascimento = new JTextField();
		campoDataNascimento.setToolTipText("");
		campoDataNascimento.setBounds(121, 98, 86, 20);
		contentPane.add(campoDataNascimento);
		campoDataNascimento.setColumns(10);
		
		campoCpf = new JTextField();
		campoCpf.setBounds(121, 129, 86, 20);
		contentPane.add(campoCpf);
		campoCpf.setColumns(10);
		
		JLabel lblNome = new JLabel("Nome:");
		lblNome.setBounds(75, 39, 46, 14);
		contentPane.add(lblNome);
		
		JLabel lblSenha = new JLabel("Senha:");
		lblSenha.setBounds(75, 70, 46, 14);
		contentPane.add(lblSenha);
		
		JLabel lblDataDeNascimento = new JLabel("Data de nascimento:");
		lblDataDeNascimento.setBounds(10, 101, 101, 14);
		contentPane.add(lblDataDeNascimento);
		
		JLabel lblCpf = new JLabel("CPF:");
		lblCpf.setBounds(87, 132, 46, 14);
		contentPane.add(lblCpf);
		
		JLabel lblTelefone = new JLabel("Telefone:");
		lblTelefone.setBounds(65, 163, 46, 14);
		contentPane.add(lblTelefone);
		
		campoTelefone = new JTextField();
		campoTelefone.setBounds(121, 160, 86, 20);
		contentPane.add(campoTelefone);
		campoTelefone.setColumns(10);
		
		JButton btnCadastrar = new JButton("Cadastrar");
		btnCadastrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				realizarCadastro();
			}
		});
		
		btnCadastrar.setBounds(32, 211, 89, 23);
		contentPane.add(btnCadastrar);
		
		JButton btnVoltar = new JButton("Cancelar");
		btnVoltar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				 fecharCadastro();
			}
		});
		btnVoltar.setBounds(131, 211, 89, 23);
		contentPane.add(btnVoltar);
		
		campoSenha = new JPasswordField();
		campoSenha.setBounds(121, 67, 86, 20);
		contentPane.add(campoSenha);
	}

	private void realizarCadastro() {
		try {
			
			String nome = validador.validaNaoVazio(campoNome.getText(), 50, "Nome");
			String senha = validador.validaNaoVazio(campoSenha.getText(), 50, "Senha");
			Date dataNascimento = validador.validaData(campoDataNascimento.getText(), "Data de Nascimento");
			String cpf = validador.validaNaoVazio(campoCpf.getText(), 15, "CPF");
			String telefone = validador.validaNaoVazio(campoTelefone.getText(), 50, "Telefone");
			
			HashMap<String, Object> hashMap = new HashMap<String, Object>();
			
			hashMap.put("nome", nome);
			hashMap.put("senha", senha);
			hashMap.put("dataNascimento", dataNascimento);
			hashMap.put("cpf", cpf);
			hashMap.put("telefone", telefone);
			frontController.dispatchRequest(Request.LOGIN_CADASTRA_USUARIO, hashMap);
			
			
		//	frontController.getGerenciadorRegrasNegocio().cadastraUsuario(0, nome, dataNascimento, telefone, cpf, senha);
		//	JOptionPane.showMessageDialog(null, "Cadastro realizado com sucesso!");
		//	this.setVisible(false);
		} catch (ValidacaoException e) {
			Log.gravaLog(e);
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
	}
	private void fecharCadastro() {
		super.dispose();
	}
	
}
