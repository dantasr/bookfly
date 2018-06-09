package visualizacao;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTable;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.table.DefaultTableModel;

import dto.Aluguel;
import dto.Usuario;
import dto.Venda;
import main.Contexto;
import negocio.NegocioException;

public class TelaUsuarioMeuHistorico extends JFrame {

	private JPanel contentPane;
	private JTable tableListaHistorico;
	private Contexto contexto;

	/**
	 * Create the frame.
	 */
	public TelaUsuarioMeuHistorico(Contexto contexto) {
		this.contexto = contexto;
		setTitle("BOOKFLY");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblMeuHistrico = new JLabel("Meu Hist\u00F3rico");
		lblMeuHistrico.setBounds(10, 11, 89, 14);
		contentPane.add(lblMeuHistrico);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(20, 36, 404, 214);
		contentPane.add(scrollPane);
		
		tableListaHistorico = new JTable();
		tableListaHistorico.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"Tipo", "Titulo", "Data"
			}
		));
		scrollPane.setViewportView(tableListaHistorico);
		
		preencheDados();
	}
	
	private void preencheDados() {
		Usuario atual = contexto.getUsuarioAtual();
		
		try {
			List<Venda> vendas = contexto.getGerenciadorRegrasNegocio().buscaVendasDoUsuario(atual.getCodigo());
			List<Aluguel> alugueis = contexto.getGerenciadorRegrasNegocio().buscaAlugueisDoUsuario(atual.getCodigo());
			
			clearTable((DefaultTableModel) tableListaHistorico.getModel());
			adicionaVendas(vendas);
			adicionaAlugueis(alugueis);
		} catch (NegocioException e) {
			JOptionPane.showMessageDialog(null, "Erro ao obter dados.\n" + e.getMessage());
		}
	}
	
	private String formataData(Timestamp data) {
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		return dateFormat.format(data);
	}
	
	private void clearTable(DefaultTableModel model) {
		while (model.getRowCount() > 0) {
			model.removeRow(0);
		}
	}
	
	private void adicionaVendas(List<Venda> vendas) {
		DefaultTableModel model = (DefaultTableModel) tableListaHistorico.getModel();

		for (Venda venda : vendas) {
			model.addRow(new Object[] { "Venda", venda.getLivro().getNome(), formataData(venda.getDataDaVenda()) });
		}
	}
	
	private void adicionaAlugueis(List<Aluguel> alugueis) {
		DefaultTableModel model = (DefaultTableModel) tableListaHistorico.getModel();

		for (Aluguel aluguel : alugueis) {
			model.addRow(new Object[] { "Aluguel", aluguel.getLivro().getNome(), formataData(aluguel.getDataDoAluguel()) });
		}
	}
}
