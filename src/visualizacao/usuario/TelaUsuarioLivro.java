package visualizacao.usuario;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import arquivos.GerenciadorArquivos;
import controller.FrontController;
import controller.FrontController.Request;
import dto.Livro;
import negocio.NegocioException;
import utilidades.IconeLabel;
import utilidades.Log;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.net.MalformedURLException;
import java.util.Date;
import java.util.HashMap;
import java.awt.event.ActionEvent;

public class TelaUsuarioLivro extends TelaBase {

	private JPanel contentPane;
	private FrontController frontController;
	private JLabel labelTitulo;
	private JLabel lblAutor;
	private JLabel lblEditora;
	private Livro livro;
	private GerenciadorArquivos gerenciadorArquivos = new GerenciadorArquivos();
//	private JLabel lblData;
	
	/**
	 * Create the frame.
	 * @throws NegocioException 
	 */
	public TelaUsuarioLivro(FrontController frontController, Livro livro) throws NegocioException {
		frontController.getGerenciadorRegrasNegocio();
		setTitle("BOOKFLY");
		this.livro = livro;
		this.frontController=frontController;
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("T\u00EDtulo:");
		lblNewLabel.setBounds(148, 11, 46, 24);
		contentPane.add(lblNewLabel);
		
		JLabel labelTitulo = new JLabel(livro.getNome());
		labelTitulo.setBounds(190, 16, 102, 14);
		contentPane.add(labelTitulo);
		
		JLabel lblAutor = new JLabel("Autor:");
		lblAutor.setBounds(148, 46, 46, 24);
		contentPane.add(lblAutor);
		
		JLabel lblEditora = new JLabel("Editora:");
		lblEditora.setBounds(148, 81, 46, 24);
		contentPane.add(lblEditora);
		
		JLabel lblData = new JLabel("Data:");
		lblData.setBounds(148, 116, 46, 24);
		contentPane.add(lblData);
		
		JLabel lblSinopse = new JLabel("Sinopse:");
		lblSinopse.setBounds(148, 151, 46, 24);
		contentPane.add(lblSinopse);
		
		JLabel labelAutor = new JLabel(livro.getAutor());
		labelAutor.setBounds(190, 51, 102, 14);
		contentPane.add(labelAutor);
		
		JLabel labelEditora = new JLabel(livro.getEditora());
		labelEditora.setBounds(190, 86, 102, 14);
		contentPane.add(labelEditora);
		
		JLabel labelData = new JLabel(livro.getDataLancamento().toString());
		labelData.setBounds(190, 121, 102, 14);
		contentPane.add(labelData);
		
		JLabel labelSinopse = new JLabel("Sinopse");
		labelSinopse.setBounds(177, 167, 234, 94);
		contentPane.add(labelSinopse);
		
		JLabel labelPreco = new JLabel(livro.getPreco() + " R$");
		labelPreco.setFont(new Font("Tahoma", Font.BOLD, 38));
		labelPreco.setBounds(8, 167, 186, 94);
		contentPane.add(labelPreco);
		
		JButton btnAlugar = new JButton("Alugar");
		btnAlugar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				HashMap<String, Object> hashMap = new HashMap<String, Object>();
				hashMap.put("usuario", usuario);
				hashMap.put("livro", livro);
				
				frontController.dispatchRequest(Request.USUARIO_EXIBE_TELA_ALUGUEL, hashMap);
			}
		});
		btnAlugar.setBounds(335, 7, 89, 44);
		contentPane.add(btnAlugar);
		
		JButton btnComprar = new JButton("Comprar");
		btnComprar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				HashMap<String, Object> hashMap = new HashMap<String, Object>();
				hashMap.put("usuario", usuario);
				hashMap.put("livro", livro);
				
				frontController.dispatchRequest(Request.USUARIO_EXIBE_TELA_COMPRA, hashMap);
			}
		});
		btnComprar.setBounds(335, 62, 89, 43);
		contentPane.add(btnComprar);
		
		JButton btnVoltar = new JButton("Voltar");
		btnVoltar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				fecharTela();
			}
		});
		btnVoltar.setBounds(335, 139, 89, 23);
		contentPane.add(btnVoltar);
		
		JLabel lblCapa = new JLabel("");
		lblCapa.setBounds(10, 11, 119, 164);
		contentPane.add(lblCapa);
		try {
			IconeLabel.colocarImagemNoLabel(lblCapa, gerenciadorArquivos.carregarImagemDoLivro(livro));
			
			JButton btnNewButton = new JButton("Promo\u00E7\u00F5es");
			btnNewButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					new TelaUsuarioPromocao(frontController, livro).setVisible(true);
				}
			});
			btnNewButton.setBounds(335, 112, 89, 23);
			contentPane.add(btnNewButton);
		} catch (MalformedURLException e) {
			Log.gravaLog(e);
			JOptionPane.showMessageDialog(null, "Erro ao carregar capa do livro");
		}
	}
	
	private void fecharTela() {
		super.dispose();
	}
}
