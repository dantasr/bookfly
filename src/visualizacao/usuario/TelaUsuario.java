package visualizacao.usuario;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import arquivos.GerenciadorArquivos;
import controller.FrontController;
import controller.FrontController.Request;
import controller.Pedido;
import dto.Livro;
import negocio.NegocioException;
import utilidades.IconeLabel;
import utilidades.Log;
import visualizacao.principal.TelaBase;
import visualizacao.principal.TelaLogin;

import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.List;

public class TelaUsuario extends TelaBase {

	/**
	 * 
	 */
	private JPanel contentPane;
	private JTextField textField;
	private JLabel lblNomeusuario;
	private FrontController frontController;
	private JLabel[] capasRecentes;
	private JLabel[] titulosRecentes;
	private GerenciadorArquivos gerenciadorArquivos = new GerenciadorArquivos();
	
	/**
	 * Create the frame.
	 */
	public TelaUsuario(FrontController frontController) {
		setTitle("BOOKFLY");
		this.frontController = frontController;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		textField = new JTextField();
		textField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent evt) {
				if(evt.getKeyCode() == KeyEvent.VK_ENTER){
					geraResultado();
				}
			}
		});
		textField.setBounds(96, 23, 179, 20);
		contentPane.add(textField);
		textField.setColumns(10);		
		
		JLabel lblPesquisar = new JLabel("Pesquisar:");
		lblPesquisar.setBounds(39, 26, 68, 14);
		contentPane.add(lblPesquisar);
		
		JLabel lblBemvindoDeVolta = new JLabel("Bem-vindo de volta,");
		lblBemvindoDeVolta.setBounds(304, 11, 112, 14);
		contentPane.add(lblBemvindoDeVolta);
		
		lblNomeusuario = new JLabel("nomeUsuario");
		lblNomeusuario.setBounds(304, 26, 123, 14);
		contentPane.add(lblNomeusuario);
		
		JButton btnLivros = new JButton("Livros");
		btnLivros.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frontController.dispatchRequest(Request.USUARIO_EXIBE_TELA_MEUS_LIVROS, Pedido.criarNovoPedido(sessao));
			}
		});
		btnLivros.setBounds(96, 44, 89, 23);
		contentPane.add(btnLivros);
		
		JButton btnHistorico = new JButton("Hist\u00F3rico");
		btnHistorico.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frontController.dispatchRequest(Request.USUARIO_EXIBE_TELA_MEU_HISTORICO, Pedido.criarNovoPedido(sessao));
			}
		});
		btnHistorico.setBounds(186, 44, 89, 23);
		contentPane.add(btnHistorico);
		
		JLabel lblRecentementeAdicionados = new JLabel("Recentemente adicionados");
		lblRecentementeAdicionados.setBounds(10, 84, 199, 14);
		contentPane.add(lblRecentementeAdicionados);
		
		JButton btnSair = new JButton("Sair");
		btnSair.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				fecharTela();
				
				frontController.dispatchRequest(Request.EXIBE_LOGIN);
			}
		});
		btnSair.setBounds(335, 227, 89, 23);
		contentPane.add(btnSair);
		
		JLabel lblCapa1 = new JLabel("");
		lblCapa1.setBounds(10, 109, 89, 119);
		contentPane.add(lblCapa1);
		
		JLabel lblTitulo1 = new JLabel("New label");
		lblTitulo1.setBounds(10, 231, 89, 14);
		contentPane.add(lblTitulo1);
		
		JLabel lblCapa2 = new JLabel("");
		lblCapa2.setBounds(107, 109, 89, 119);
		contentPane.add(lblCapa2);
		
		JLabel lblTitulo2 = new JLabel("New label");
		lblTitulo2.setBounds(107, 231, 89, 14);
		contentPane.add(lblTitulo2);
		
		JLabel lblCapa3 = new JLabel("");
		lblCapa3.setBounds(217, 109, 89, 119);
		contentPane.add(lblCapa3);
		
		JLabel lblTitulo3 = new JLabel("New label");
		lblTitulo3.setBounds(217, 231, 89, 14);
		contentPane.add(lblTitulo3);
		
		capasRecentes = new JLabel[] { lblCapa1, lblCapa2, lblCapa3 };
		titulosRecentes = new JLabel[] { lblTitulo1, lblTitulo2, lblTitulo3 };
		
	}

	private void atualizarCampos(List<Livro> recentes) {
		this.lblNomeusuario.setText(sessao.getUsuarioLogado().getNome());
		try {
			for (int i = 0; i < recentes.size(); i++) {
				Livro livro = recentes.get(i);
				
				titulosRecentes[i].setText(livro.getNome());
				IconeLabel.colocarImagemNoLabel(capasRecentes[i], gerenciadorArquivos.carregarImagemDoLivro(livro));
			}
			
			// Apagar os labels q sobraram
			for (int i = recentes.size(); i < 3; i++) {
				titulosRecentes[i].setText("");
			}
		} catch (MalformedURLException e) {
			Log.gravaLog(e);
			JOptionPane.showMessageDialog(null, "Erro ao carregar livros recentes");
		}
	}
	
	private void geraResultado() {
		String pesquisa = textField.getText();
		
		Pedido pedido = Pedido.criarNovoPedido(sessao);
		pedido.put("pesquisa", pesquisa);
		frontController.dispatchRequest(Request.USUARIO_REALIZA_PESQUISA, pedido);
	}
	private void fecharTela() {
		super.dispose();
	}

	@Override
	public void show(Pedido params) {
		List<Livro> recentes = (List<Livro>) params.get("livrosRecentes");
		atualizarCampos(recentes);
		
		this.setVisible(true);
	}
}
