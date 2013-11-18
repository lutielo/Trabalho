package br.unisul.gui.relatorios.janelas;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.TableColumn;

import br.unisul.dados.Ingrediente;
import br.unisul.dados.Receita_Ingrediente;
import br.unisul.dados.Unidade;
import br.unisul.dao.DAOException;
import br.unisul.dao.Receita_IngredienteDAO;
import br.unisul.gui.relatorios.tablemodels.CellRenderer;
import br.unisul.gui.relatorios.tablemodels.IngredientesMaisUtilizadosTableModel;

public class ListagemIngredientesMaisUtilizados extends JFrame {

	private static final long serialVersionUID = 1L;

	private List<Receita_Ingrediente> listaIngredientesMaisUtilizados;
	private IngredientesMaisUtilizadosTableModel imutm;
	private JTable tblIngredientes;
	private JScrollPane spListagemIngredientes;
	private JLabel lblListagemIngredientesMaisUtilizados;
	private JLabel lblCliqueParaVisualizar;

	public ListagemIngredientesMaisUtilizados() {
		super("Listagem ingredientes mais utilizados");
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
		spListagemIngredientes.setBounds(10, 63, 415, 305);
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
				if(getTblIngredientes().getSelectedRow() == -1){
					JOptionPane.showMessageDialog(null, "Selecione um ingrediente");
				} else {
					int selectedRow = getTblIngredientes().getSelectedRow();
					Receita_Ingrediente ri = listaIngredientesMaisUtilizados.get(selectedRow);
					Integer codUnidade = ri.getUnidade().getCodigo();
					String nomeUnidade = ri.getUnidade().getTipo();
					Unidade unidade = new Unidade(codUnidade, nomeUnidade);
					
					Integer codIngrediente = ri.getIngredientes().getCodigo();
					String nomeIngrediente = ri.getIngredientes().getNome();
					Ingrediente ingrediente = new Ingrediente(codIngrediente, nomeIngrediente);
					
					ListagemReceitasQueUsamIngrediente listagemReceitasQueUsamIngrediente = new ListagemReceitasQueUsamIngrediente(unidade, ingrediente);
					listagemReceitasQueUsamIngrediente.setVisible(true);
				}
			}
		});
		btnVisualizar.setBounds(147, 395, 104, 23);
		getContentPane().add(btnVisualizar);
		
		lblCliqueParaVisualizar = new JLabel("Clique para visualizar as receitas que usam o ingrediente selecionado");
		lblCliqueParaVisualizar.setBounds(20, 370, 405, 14);
		getContentPane().add(lblCliqueParaVisualizar);

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
		if (imutm == null) {
			imutm = (IngredientesMaisUtilizadosTableModel) getTblIngredientes().getModel();
		}
		return imutm;
	}

	private List<Receita_Ingrediente> getIngredientes() {
		Receita_IngredienteDAO receita_IngredienteDAO = new Receita_IngredienteDAO();
		listaIngredientesMaisUtilizados = new ArrayList<>();
		try {
			listaIngredientesMaisUtilizados = receita_IngredienteDAO.listeIngredientesMaisUtilizados();
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
