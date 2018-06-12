package visualizacao.usuario;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import controller.FrontController;
import controller.FrontController.Request;
import dto.Livro;
import dto.Promocao;
import negocio.NegocioException;
import utilidades.Log;

import javax.swing.JScrollPane;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.List;

public class TelaUsuarioPromocao extends TelaBase {

	private JPanel contentPane;
	private JTable table;
	private FrontController frontController ;
	private Livro livro ;

	public TelaUsuarioPromocao(FrontController frontController, Livro livro) {
		this.frontController = frontController ;
		this.livro = livro;
		setTitle("BOOKFLY");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 374, 244);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 54, 338, 140);
		contentPane.add(scrollPane);

		table = new JTable();
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent evt) {
				if (evt.getClickCount() != 2) return;

				int row = table.getSelectedRow();
				if (row == -1) return;

				int codigo = (int) table.getModel().getValueAt(row, 0);
				Promocao promocao;
				try {
					HashMap<String, Object> hashMap = new HashMap<String, Object>();
					hashMap.put("usuario", usuario);
					hashMap.put("livro", livro);
					hashMap.put("codigoPromocao", codigo);
					
					frontController.dispatchRequest(Request.USUARIO_EXIBE_TELA_COMPRA, hashMap);
				} catch (NegocioException e) {
					Log.gravaLog(e);
					JOptionPane.showMessageDialog(null, "Erro ao carregar promocao");
				}
			}
		});
		table.setModel(new DefaultTableModel(
				new Object[][] {
				},
				new String[] {
						"C\u00F3digo ", "Pre\u00E7o"
				}
				){
			@Override
			public boolean isCellEditable(int row, int column) {
				//all cells false
				return false;
			}
		});

		scrollPane.setViewportView(table);
		JLabel lblPromoes = new JLabel("Promo\u00E7\u00F5es:");
		lblPromoes.setBounds(10, 29, 84, 14);
		contentPane.add(lblPromoes);
		realizaPesquisaPromocao();
	}

	private void clearTable(DefaultTableModel model) {
		while (model.getRowCount() > 0) {
			model.removeRow(0);
		}
	}

	private void montaTabelaPromocoes(List<Promocao> promos) {
		DefaultTableModel model = (DefaultTableModel) table.getModel();
		clearTable(model);

		for (Promocao promo: promos) {
			model.addRow(new Object[] { promo.getCodigo(),promo.getPreco(), promo});
		}
	}

	private void realizaPesquisaPromocao() {
		try {
			List<Promocao> resultados = frontController.getGerenciadorRegrasNegocio().buscaPromocaoLista(livro.getCodigo());
			
			if(resultados.size() != 0) {
				montaTabelaPromocoes(resultados);
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
