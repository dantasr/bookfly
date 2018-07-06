package visualizacao;

import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTable;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.table.DefaultTableModel;

import dto.Livro;
import main.Contexto;
import negocio.NegocioException;
import utilidades.Log;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class TelaUsuarioResultadoPesquisa extends JFrame {

	private JPanel contentPane;
	private JTable table;
	private Contexto contexto;

	/**
	 * Create the frame.
	 */
	public TelaUsuarioResultadoPesquisa(Contexto contexto, String termo) {
		setTitle("BOOKFLY");
		this.contexto = contexto;
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel_1 = new JLabel("Resultados para: " + termo);
		lblNewLabel_1.setBounds(10, 50, 199, 14);
		contentPane.add(lblNewLabel_1);
		
		JButton btnVoltar = new JButton("Voltar");
		btnVoltar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				fecharPesquisa();
			}
		});
		btnVoltar.setBounds(335, 26, 89, 23);
		contentPane.add(btnVoltar);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(20, 75, 404, 130);
		contentPane.add(scrollPane);
		
		table = new JTable();
		table.addMouseListener(new MouseAdapter() {
			
			@Override
			public void mouseClicked(MouseEvent evt) {
				if (evt.getClickCount() != 2) return;
				
				int row = table.getSelectedRow();
				if (row == -1) return;
				
				int codigo = Integer.parseInt(table.getModel().getValueAt(row, 0).toString());
				
				try {
					Livro livro = contexto.getFachadaRegrasNegocio().buscaLivro(codigo);
					new TelaUsuarioLivro(contexto, livro).setVisible(true);
					setVisible(false);
				} catch (NegocioException e) {
					e.printStackTrace();
				}
			}
		});
		table.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"C\u00F3digo","T\u00EDtulo","Autor","Editora", "Data de lan\u00E7amento","Pre�o"
			}
				
		){
				  @Override
				    public boolean isCellEditable(int row, int column) {
				       //all cells false
				       return false;
				    }
				
		});
		scrollPane.setViewportView(table);
		realizaPesquisaProduto(termo);
	}
	
	private void clearTable(DefaultTableModel model) {
		while (model.getRowCount() > 0) {
			model.removeRow(0);
		}
	}
	
	private void montaTabelaLivros(List<Livro> livros) {
		DefaultTableModel model = (DefaultTableModel) table.getModel();
		clearTable(model);

		for (Livro livro : livros) {
			model.addRow(new Object[] { livro.getCodigo(), livro.getNome(),
					livro.getAutor(), livro.getEditora(),livro.getDataLancamento(),livro.getPreco()});
		}
	}
	
	private void realizaPesquisaProduto(String termo) {
		try {
			List<Livro> resultados = contexto.getFachadaRegrasNegocio().buscaLivrosPorNome(termo);
			if(resultados.size() != 0) {
				montaTabelaLivros(resultados);
				this.setVisible(true);
			}
			else JOptionPane.showMessageDialog(null, "N�o h� livros com esse nome");
		}
		catch (NegocioException e) {
			Log.gravaLog(e);
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
	}
	private void fecharPesquisa() {
		super.dispose();
	}
}
