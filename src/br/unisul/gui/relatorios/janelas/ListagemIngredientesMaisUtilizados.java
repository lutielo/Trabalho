package br.unisul.gui.relatorios.janelas;

import java.awt.Font;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.TableColumn;

import br.unisul.dados.IngredientesMaisUtilizados;
import br.unisul.dao.DAOException;
import br.unisul.dao.IngredienteDAO;
import br.unisul.gui.relatorios.tablemodels.CellRenderer;
import br.unisul.gui.relatorios.tablemodels.IngredientesMaisUtilizadosTableModel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ListagemIngredientesMaisUtilizados extends JFrame {

	private static final long serialVersionUID = 1L;

	private List<IngredientesMaisUtilizados> listaIngredientesMaisUtilizados;
	private IngredientesMaisUtilizadosTableModel atm;
	private JTable tblIngredientes;
	private JScrollPane spListagemIngredientes;
	private JLabel lblListagemIngredientesMaisUtilizados;

	public ListagemIngredientesMaisUtilizados() {
		super("Listagem Ingredientes Mais Utilizados");
		this.setSize(443, 453);
		this.setResizable(false);
		this.setType(Type.UTILITY);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setLocationRelativeTo(null);
		getContentPane().setLayout(null);

		this.abreTela();
	}

	private void abreTela() {
		spListagemIngredientes = new JScrollPane(getTblIngredientes());
		spListagemIngredientes.setBounds(10, 63, 415, 328);
		getContentPane().add(spListagemIngredientes);
		this.addIngredientes();

		getTblIngredientes().setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		getTblIngredientes().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		lblListagemIngredientesMaisUtilizados = new JLabel("Listagem de Ingredientes Mais Utilizados");
		lblListagemIngredientesMaisUtilizados.setFont(new Font("Tahoma", Font.PLAIN, 19));
		lblListagemIngredientesMaisUtilizados.setBounds(36, 23, 389, 29);
		getContentPane().add(lblListagemIngredientesMaisUtilizados);
		
		JButton btnVisualizar = new JButton("Visualizar");
		btnVisualizar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
			}
		});
		btnVisualizar.setBounds(147, 395, 104, 23);
		getContentPane().add(btnVisualizar);

		TableColumn col0 = getTblIngredientes().getColumnModel().getColumn(0);
		col0.setPreferredWidth(90);

		TableColumn col1 = getTblIngredientes().getColumnModel().getColumn(1);
		col1.setPreferredWidth(200);
		
		TableColumn col2 = getTblIngredientes().getColumnModel().getColumn(2);
		col2.setPreferredWidth(100);

		getTblIngredientes().setDefaultRenderer(Object.class, new CellRenderer());
	}

	private JTable getTblIngredientes() {
		if (tblIngredientes == null) {
			tblIngredientes = new JTable();
			tblIngredientes.setModel(new IngredientesMaisUtilizadosTableModel());
		}
		return tblIngredientes;
	}

	private IngredientesMaisUtilizadosTableModel getModel() {
		if (atm == null) {
			atm = (IngredientesMaisUtilizadosTableModel) getTblIngredientes().getModel();
		}
		return atm;
	}

	private List<IngredientesMaisUtilizados> getIngredientes() {
		IngredienteDAO ingredienteDAO = new IngredienteDAO();
		listaIngredientesMaisUtilizados = new ArrayList<>();
		try {
			listaIngredientesMaisUtilizados = ingredienteDAO.listeIngredientesMaisUtilizados();
		} catch (DAOException e) {
			e.printStackTrace();
		}
		return listaIngredientesMaisUtilizados;
	}

	private void addIngredientes() {
		getModel().addListaDeIngredientes(getIngredientes());
	}

	public static void main(String[] args) {
		ListagemIngredientesMaisUtilizados listagemIngredientesMaisUtilizados = new ListagemIngredientesMaisUtilizados();
		listagemIngredientesMaisUtilizados.setVisible(true);
	}
}
