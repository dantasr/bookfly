package visualizacao.admin;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.net.MalformedURLException;
import java.sql.Date;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import arquivos.GerenciadorArquivos;
import controller.FrontController;
import controller.FrontController.Request;
import controller.IAcceptRequests;
import utilidades.IconeLabel;
import utilidades.Log;
import utilidades.SeletorDeArquivos;
import utilidades.ValidacaoException;
import utilidades.Validador;

import javax.swing.JTextField;

public class TelaAdminRegistro extends JFrame implements IAcceptRequests {

	private JPanel contentPane;
	private JTextField campoCodProduto;
	private JTextField campoTitulo;
	private JTextField campoPreco;
	private JTextField campoAutor;
	private JTextField campoEditora;
	private JTextField campoData;
	private File campoPdf;
	private File campoImagem;
	private JLabel lblImagemLivro;
	
	private FrontController frontController;
	private Validador validador = new Validador();
	private GerenciadorArquivos gerenciadorArquivos = new GerenciadorArquivos();

	/**
	 * Create the frame.
	 */
	public TelaAdminRegistro(FrontController frontController) {
		setTitle("ADMIN");
		this.frontController = frontController;

		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblNewLabel = new JLabel("Cod. Produto:");
		lblNewLabel.setBounds(10, 11, 92, 14);
		contentPane.add(lblNewLabel);

		JLabel lblTtulo = new JLabel("T\u00EDtulo:");
		lblTtulo.setBounds(10, 36, 92, 14);
		contentPane.add(lblTtulo);

		JLabel lblPreo = new JLabel("Pre\u00E7o:");
		lblPreo.setBounds(10, 61, 92, 14);
		contentPane.add(lblPreo);

		JLabel lblAutor = new JLabel("Autor:");
		lblAutor.setBounds(10, 86, 92, 14);
		contentPane.add(lblAutor);

		JLabel lblEditora = new JLabel("Editora:");
		lblEditora.setBounds(10, 111, 92, 14);
		contentPane.add(lblEditora);

		JLabel lblData = new JLabel("Data:");
		lblData.setBounds(10, 139, 92, 14);
		contentPane.add(lblData);

		campoCodProduto = new JTextField();
		campoCodProduto.setBounds(88, 8, 208, 20);
		contentPane.add(campoCodProduto);
		campoCodProduto.setColumns(10);

		campoTitulo = new JTextField();
		campoTitulo.setColumns(10);
		campoTitulo.setBounds(88, 36, 208, 20);
		contentPane.add(campoTitulo);

		campoPreco = new JTextField();
		campoPreco.setColumns(10);
		campoPreco.setBounds(88, 61, 208, 20);
		contentPane.add(campoPreco);

		campoAutor = new JTextField();
		campoAutor.setColumns(10);
		campoAutor.setBounds(88, 86, 208, 20);
		contentPane.add(campoAutor);

		campoEditora = new JTextField();
		campoEditora.setColumns(10);
		campoEditora.setBounds(88, 111, 208, 20);
		contentPane.add(campoEditora);

		campoData = new JTextField();
		campoData.setColumns(10);
		campoData.setBounds(88, 136, 208, 20);
		contentPane.add(campoData);

		JButton btnAddSinopse = new JButton("Add Sinopse");
		btnAddSinopse.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnAddSinopse.setBounds(10, 197, 127, 23);
		contentPane.add(btnAddSinopse);

		JButton btnAddPdf = new JButton("Add PDF");
		btnAddPdf.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				adicionarPdf();
			}
		});
		btnAddPdf.setBounds(10, 227, 127, 23);
		contentPane.add(btnAddPdf);

		JButton btnConfirmar = new JButton("Confirmar");
		btnConfirmar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				realizaCadastro();
			}
		});
		btnConfirmar.setBounds(297, 167, 127, 83);
		contentPane.add(btnConfirmar);

		JButton btnAddImagem = new JButton("add Imagem");
		btnAddImagem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				adicionarImagem();
			}
		});
		btnAddImagem.setBounds(160, 201, 127, 49);
		contentPane.add(btnAddImagem);

		lblImagemLivro = new JLabel("");
		lblImagemLivro.setBounds(306, 21, 123, 148);
		contentPane.add(lblImagemLivro);
	}

	private void adicionarPdf() {
		campoPdf = SeletorDeArquivos.selecionar(new String[] { "pdf" }, "Arquivo PDF");
		
	}

	private void adicionarImagem() {
		campoImagem = SeletorDeArquivos.selecionar(new String[] { "jpg", "png", "jpeg" }, "Imagem");

		// Carregar imagem no canvas da tela, se possível.
		if (campoImagem != null) {
			try {
				IconeLabel.colocarImagemNoLabel(lblImagemLivro, campoImagem);
			} catch (MalformedURLException e) {
				Log.gravaLog(e);
				JOptionPane.showMessageDialog(null, "Erro ao carregar preview da imagem.");
			}
		}
	}
	
	private void realizaCadastro() {
		try {
			int codigo = validador.validaFormataInteiro(campoCodProduto.getText(), "Cod. Produto");
			String titulo = validador.validaNaoVazio(campoTitulo.getText(), 50, "Titulo");
			String autor = validador.validaNaoVazio(campoAutor.getText(), 50, "Autor");
			int preco = validador.validaFormataInteiro(campoPreco.getText(), "Preco");
			String editora = validador.validaNaoVazio(campoEditora.getText(), 50, "Editora");
			Date data = validador.validaData(campoData.getText(), "Data");
			validador.validaArquivo(campoPdf, "pdf");
			validador.validaArquivo(campoImagem, "imagem");
			
			HashMap<String, Object> hashMap = new HashMap<String, Object>();
			
			hashMap.put("codigo", codigo);
			hashMap.put("titulo", titulo);
			hashMap.put("autor", autor);
			hashMap.put("preco", preco);
			hashMap.put("editora", editora);
			hashMap.put("data", data);
			hashMap.put("campoPdf", campoPdf);
			hashMap.put("campoImagem", campoImagem);
			frontController.dispatchRequest(Request.ADMIN_CADASTRA_LIVRO, hashMap);
		//	Livro livro = new Livro(codigo, titulo, autor, editora, preco, data);
		//	gerenciadorArquivos.salvarImagemDoLivro(livro, campoImagem);
		//	gerenciadorArquivos.salvarArquivoPdf(livro, campoPdf);
		//	JOptionPane.showMessageDialog(null, "Cadastro realizado com sucesso!");
		}
		catch (ValidacaoException e) {
			Log.gravaLog(e);
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
	}
}
