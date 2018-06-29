package visualizacao;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import dto.Livro;
import dto.Usuario;
import main.Contexto;
import negocio.NegocioException;
import utilidades.Log;

import javax.swing.JComboBox;

import javax.swing.JTextField;
import javax.swing.JTable;
import javax.swing.JButton;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import javax.swing.JScrollPane;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class TelaAdminPesquisaRemoveClienteProduto extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private JTextField campoPesquisaProduto;
	private JTable tableResultadosUsuario;
	private JTable tableResultadosProduto;
	private Contexto contexto;
	private int codigo = -1;

	/**
	 * Create the frame.
	 * @throws NegocioException 
	 */
	public TelaAdminPesquisaRemoveClienteProduto(Contexto contexto) throws NegocioException {
		setTitle("ADMIN");
		this.contexto = contexto;
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(10, 11, 414, 239);
		contentPane.add(tabbedPane);
		
		JPanel painelUsuario = new JPanel();
		tabbedPane.addTab("Usuario", null, painelUsuario, null);
		painelUsuario.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Pesquisar:");
		lblNewLabel.setBounds(10, 11, 92, 14);
		painelUsuario.add(lblNewLabel);
		
		textField = new JTextField();
		textField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent evt) {
				if(evt.getKeyCode() == KeyEvent.VK_ENTER){
					realizaPesquisaUsuario();
				}
			}
		});
		textField.setBounds(65, 8, 334, 20);
		painelUsuario.add(textField);
		textField.setColumns(10);
		
		JButton btnDesativaUsuario = new JButton("Desativar");
		btnDesativaUsuario.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				desativaUsuario();
			}
		});
		btnDesativaUsuario.setBounds(310, 177, 89, 23);
		painelUsuario.add(btnDesativaUsuario);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(10, 38, 389, 128);
		painelUsuario.add(scrollPane_1);
		
		tableResultadosUsuario = new JTable();
		tableResultadosUsuario.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"C\u00F3digo", "Nome", "Data de nascimento", "Ativo"
			}
		) {
			   @Override
			    public boolean isCellEditable(int row, int column) {
			       //all cells false
			       return false;
			    }
		});
		scrollPane_1.setViewportView(tableResultadosUsuario);
		
		JButton btnAtivar = new JButton("Ativar");
		btnAtivar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				ativaUsuario();
			}
		});
		btnAtivar.setBounds(210, 177, 89, 23);
		painelUsuario.add(btnAtivar);
		
		JPanel painelProduto = new JPanel();
		tabbedPane.addTab("Produto", null, painelProduto, null);
		painelProduto.setLayout(null);
		
		JLabel lblPesquisar = new JLabel("Pesquisar:");
		lblPesquisar.setBounds(10, 11, 92, 14);
		painelProduto.add(lblPesquisar);
		
		campoPesquisaProduto = new JTextField();
		campoPesquisaProduto.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent evt) {
				if(evt.getKeyCode() == KeyEvent.VK_ENTER){
					realizaPesquisaProduto();
				}
			}
		});
		
		campoPesquisaProduto.setColumns(10);
		campoPesquisaProduto.setBounds(65, 8, 334, 20);
		painelProduto.add(campoPesquisaProduto);
		
		JButton btnRemoveProduto = new JButton("Remover...");
		btnRemoveProduto.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				removeProduto();
			}
		});
		btnRemoveProduto.setBounds(310, 176, 89, 23);
		painelProduto.add(btnRemoveProduto);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 36, 389, 133);
		painelProduto.add(scrollPane);
		
		tableResultadosProduto = new JTable();
		tableResultadosProduto.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent evt) {
				if (evt.getClickCount() != 2) return;
				
				int row = tableResultadosProduto.getSelectedRow();
				if (row == -1) return;
				
				codigo = Integer.parseInt(tableResultadosProduto.getModel().getValueAt(row, 0).toString());
				
			}
		});
		scrollPane.setViewportView(tableResultadosProduto);
		tableResultadosProduto.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
					"Codigo","Autor", "Titulo", "Pre\u00E7o","Editora","Data de publica\u00E7\u00E3o "
			}
		) {
			   @Override
			    public boolean isCellEditable(int row, int column) {
			       //all cells false
			       return false;
			    }
		});
		try {
	
		JButton btnPromoo = new JButton("Promo\u00E7\u00E3o");
		btnPromoo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				cadastrarPromocao();
			}
		});
		btnPromoo.setBounds(208, 176, 89, 23);
		painelProduto.add(btnPromoo);
		
		JButton btnRemoverPromoo = new JButton("Remover promo\u00E7\u00E3o");
		btnRemoverPromoo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				removerPromocao();
			}	
		});
		btnRemoverPromoo.setBounds(10, 176, 188, 23);
		painelProduto.add(btnRemoverPromoo);
		tableResultadosProduto.getColumnModel().getColumn(4).setPreferredWidth(105);
	}catch(Exception e) {
		JOptionPane.showMessageDialog(null, "Codigo invalido");
	}
	
	
	}
	
	private void desativaUsuario() {
		int row = tableResultadosUsuario.getSelectedRow();
		if (row == -1) {
			JOptionPane.showMessageDialog(null, "Voce nao selecionou um usuario.");
			return;
		}
		
		DefaultTableModel model = (DefaultTableModel) tableResultadosUsuario.getModel();
		int codigo = (int) model.getValueAt(row, 0);
		
		try {
			contexto.getFachadaRegrasNegocio().desativaUsuario(codigo);
			realizaPesquisaUsuario();
			JOptionPane.showMessageDialog(null, "Usuario desativado com sucesso!");
		} catch (NegocioException e) {
			Log.gravaLog(e);
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
	}
	
	private void ativaUsuario() {
		int row = tableResultadosUsuario.getSelectedRow();
		if (row == -1) {
			JOptionPane.showMessageDialog(null, "Voce nao selecionou um usuario.");
			return;
		}
		DefaultTableModel model = (DefaultTableModel) tableResultadosUsuario.getModel();
		int codigo = (int) model.getValueAt(row, 0);
		
		try {
			contexto.getFachadaRegrasNegocio().ativaUsuario(codigo);
			realizaPesquisaUsuario();
			JOptionPane.showMessageDialog(null, "Usuario Ativado com sucesso!");
		} catch (NegocioException e) {
			Log.gravaLog(e);
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
	}

	private void removeProduto() {
		int row = tableResultadosProduto.getSelectedRow();
		if (row == -1) return;
		
		DefaultTableModel model = (DefaultTableModel) tableResultadosProduto.getModel();
		int codigo = (int) model.getValueAt(row, 0);
		
		try {
			contexto.getFachadaRegrasNegocio().removeLivro(codigo);
			realizaPesquisaProduto();
			JOptionPane.showMessageDialog(null, "Produto removido com sucesso!");
		} catch (NegocioException e) {
			Log.gravaLog(e);
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
	}
	
	private void cadastrarPromocao() {
		int row = tableResultadosProduto.getSelectedRow();
		if (row == -1) return;
		
		DefaultTableModel model = (DefaultTableModel) tableResultadosProduto.getModel();
		int codigo = (int) model.getValueAt(row, 0);
		
		new TelaAdminPromocao(contexto, codigo).setVisible(true);
	}
	
	private void removerPromocao() {
		int row = tableResultadosProduto.getSelectedRow();
		if (row == -1) return;
		
		DefaultTableModel model = (DefaultTableModel) tableResultadosProduto.getModel();
		int codigo = (int) model.getValueAt(row, 0);
		
		try {
			contexto.getFachadaRegrasNegocio().removePromocao(codigo);
			JOptionPane.showMessageDialog(null, "Deletado com sucesso!");
		} catch (NegocioException e) {
			JOptionPane.showMessageDialog(null, "Erro ao remover promoção:\n" + e.getMessage());
		}
	}

	private void clearTable(DefaultTableModel model) {
		while (model.getRowCount() > 0) {
			model.removeRow(0);
		}
	}
	
	private void montaTabelaLivros(List<Livro> livros) {
		DefaultTableModel model = (DefaultTableModel) tableResultadosProduto.getModel();
		clearTable(model);

		for (Livro livro : livros) {
			model.addRow(new Object[] { livro.getCodigo(), livro.getAutor(),
					livro.getNome(), livro.getPreco(), livro.getEditora() , livro.getDataLancamento()});
		}
	}
	
	private void montaTabelaUsuarios(List<Usuario> usuarios) {
		DefaultTableModel model = (DefaultTableModel) tableResultadosUsuario.getModel();
		clearTable(model);

		for (Usuario usuario : usuarios) {
			model.addRow(new Object[] {usuario.getCodigo(),usuario.getNome(),usuario.getDataNascimento(), usuario.isAtivado() ? "Sim" : "Nao"});
		}
	}

	private void realizaPesquisaProduto() {
		String termo = campoPesquisaProduto.getText();
		try {
			List<Livro> resultados = contexto.getFachadaRegrasNegocio().buscaLivrosPorNome(termo);
			montaTabelaLivros(resultados);
		} catch (NegocioException e) {
			Log.gravaLog(e);
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
	}
	
	private void realizaPesquisaUsuario() {
		String termo = campoPesquisaProduto.getText();
		try {
			List<Usuario> resultados = contexto.getFachadaRegrasNegocio().buscaUsuariosPorNome(termo);
			montaTabelaUsuarios(resultados);
		} catch (NegocioException e) {
			Log.gravaLog(e);
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
	}
}
