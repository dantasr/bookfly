package visualizacao;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import main.Contexto;
import negocio.NegocioException;
import utilidades.LeitorPDF;
import utilidades.Log;

import javax.swing.JTable;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.table.DefaultTableModel;

import arquivos.GerenciadorArquivos;
import dto.Aluguel;
import dto.Livro;
import dto.Usuario;
import dto.Venda;

public class TelaUsuarioMeusLivros extends JFrame {

	private JPanel contentPane;
	private JTable tableListaLivros;
	private Contexto contexto;
	private GerenciadorArquivos gerenciadorArquivos = new GerenciadorArquivos();

	/**
	 * Create the frame.
	 * @param contexto 
	 */
	public TelaUsuarioMeusLivros(Contexto contexto) {
		this.contexto = contexto;
		
		setTitle("BOOKFLY");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblTitulos = new JLabel("Meus Livros");
		lblTitulos.setBounds(10, 12, 89, 14);
		contentPane.add(lblTitulos);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 37, 414, 213);
		contentPane.add(scrollPane);
		
		tableListaLivros = new JTable();
		tableListaLivros.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"Tipo", "Titulo", "Autor", "Objeto"
			}
		)  {
			   @Override
			    public boolean isCellEditable(int row, int column) {
			       //all cells false
			       return false;
			    }
		});
		tableListaLivros.removeColumn(tableListaLivros.getColumnModel().getColumn(3));
		tableListaLivros.addMouseListener(new MouseAdapter() {
			
			@Override
			public void mouseClicked(MouseEvent evt) {
				if (evt.getClickCount() != 2) return;
				
				lerLivro();
			}
		});
		scrollPane.setViewportView(tableListaLivros);
		
		preencherTabela();
	}
	
	private void lerLivro() {
		int row = tableListaLivros.getSelectedRow();
		if (row == -1) return;
		
		DefaultTableModel model = (DefaultTableModel) tableListaLivros.getModel();
		Object vendaOuAluguel = model.getValueAt(row, 3);
		Livro livro;
		if (vendaOuAluguel instanceof Venda) {
			livro = ((Venda) vendaOuAluguel).getLivro();
		} else {
			livro = ((Aluguel) vendaOuAluguel).getLivro();
		}
		
		File arquivo = gerenciadorArquivos.obterArquivoPdf(livro);
		if (arquivo == null) {
			JOptionPane.showMessageDialog(null, "Arquivo do livro não encontrado.");
			return;
		}
		
		LeitorPDF leitor = new LeitorPDF(arquivo);
		try {
			leitor.exibir();
		} catch (IOException e) {
			Log.gravaLog(e);
			JOptionPane.showMessageDialog(null, "Erro ao exibir o livro");
		}
	}

	private void clearTable(DefaultTableModel model) {
		while (model.getRowCount() > 0) {
			model.removeRow(0);
		}
	}
	
	private void limpaTabela() {
		DefaultTableModel model = (DefaultTableModel) tableListaLivros.getModel();
		clearTable(model);
	}
	
	private void adicionaVendas(List<Venda> vendas) {
		DefaultTableModel model = (DefaultTableModel) tableListaLivros.getModel();

		for (Venda venda : vendas) {
			model.addRow(new Object[] { "Venda", venda.getLivro().getNome(), venda.getLivro().getAutor(), venda });
		}
	}
	
	private void adicionaAlugueis(List<Aluguel> alugueis) {
		DefaultTableModel model = (DefaultTableModel) tableListaLivros.getModel();

		for (Aluguel aluguel : alugueis) {
			model.addRow(new Object[] { "Aluguel", aluguel.getLivro().getNome(), aluguel.getLivro().getAutor(), aluguel });
		}
	}

	private void preencherTabela() {
		limpaTabela();
		
		Usuario atual = contexto.getUsuarioAtual();
		try {
			List<Venda> vendas = contexto.getFachadaRegrasNegocio().buscaVendasDoUsuario(atual.getCodigo());
			adicionaVendas(vendas);
			List<Aluguel> alugueis = contexto.getFachadaRegrasNegocio().buscaAlugueisDoUsuario(atual.getCodigo());
			adicionaAlugueis(alugueis);
		} catch (NegocioException e) {
			Log.gravaLog(e);
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
	}
}
