package br.unisul.gui.relatorios.janelas;

import java.awt.Font;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.table.TableColumn;

import br.unisul.dados.Receita_Ingrediente;
import br.unisul.dao.DAOException;
import br.unisul.dao.Receita_IngredienteDAO;
import br.unisul.gui.relatorios.tablemodels.CellRenderer;
import br.unisul.gui.relatorios.tablemodels.ReceitaIngredienteTableModel;

public class ListagemIngredientesDaReceita extends JFrame {

	private static final long serialVersionUID = 1L;

	private List<Receita_Ingrediente> listaIngredientesDaReceita;
	private ReceitaIngredienteTableModel ritm;
	private JTable tblIngredientes;
	private JScrollPane spListagemIngredientes;
	private JLabel lblIngredientesDaReceita;
	private JTextField tfNomeIngredienteUsado;

	public ListagemIngredientesDaReceita(Receita_Ingrediente receitaIngrediente) {
		super("Ingredientes da receita " + receitaIngrediente.getReceita().getNome());
		this.setSize(443, 453);
		this.setResizable(false);
		this.setType(Type.UTILITY);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setLocationRelativeTo(null);
		getContentPane().setLayout(null);

		this.abreTela(receitaIngrediente);
	}

	private void abreTela(Receita_Ingrediente receitaIngrediente) {
		spListagemIngredientes = new JScrollPane(getTblIngredientes());
		spListagemIngredientes.setBounds(10, 75, 415, 328);
		getContentPane().add(spListagemIngredientes);
		this.addIngredientes(receitaIngrediente);

		getTblIngredientes().setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		getTblIngredientes().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		lblIngredientesDaReceita = new JLabel("Ingredientes da Receita:");
		lblIngredientesDaReceita.setFont(new Font("Tahoma", Font.PLAIN, 19));
		lblIngredientesDaReceita.setBounds(69, 11, 331, 27);
		getContentPane().add(lblIngredientesDaReceita);
		
		tfNomeIngredienteUsado = new JTextField("");
		tfNomeIngredienteUsado.setEditable(false);
		tfNomeIngredienteUsado.setBounds(79, 44, 260, 20);
		tfNomeIngredienteUsado.setText(receitaIngrediente.getReceita().getNome());
		getContentPane().add(tfNomeIngredienteUsado);
		tfNomeIngredienteUsado.setColumns(10);

		TableColumn col0 = getTblIngredientes().getColumnModel().getColumn(0);
		col0.setPreferredWidth(150);

		TableColumn col1 = getTblIngredientes().getColumnModel().getColumn(1);
		col1.setPreferredWidth(200);
		
		TableColumn col2 = getTblIngredientes().getColumnModel().getColumn(2);
		col2.setPreferredWidth(80);

		getTblIngredientes().setDefaultRenderer(Object.class, new CellRenderer());
	}

	private JTable getTblIngredientes() {
		if (tblIngredientes == null) {
			tblIngredientes = new JTable();
			tblIngredientes.setModel(new ReceitaIngredienteTableModel());
		}
		return tblIngredientes;
	}

	private ReceitaIngredienteTableModel getModel() {
		if (ritm == null) {
			ritm = (ReceitaIngredienteTableModel) getTblIngredientes().getModel();
		}
		return ritm;
	}

	private List<Receita_Ingrediente> getIngredientes(Receita_Ingrediente receitaIngrediente) {
		Receita_IngredienteDAO receita_IngredienteDAO = new Receita_IngredienteDAO();
		listaIngredientesDaReceita = new ArrayList<>();
		try {
			listaIngredientesDaReceita = receita_IngredienteDAO.listarIngredientesDaReceita(receitaIngrediente);
		} catch (DAOException e) {
			e.printStackTrace();
		}
		return listaIngredientesDaReceita;
	}

	private void addIngredientes(Receita_Ingrediente receitaIngrediente) {
		getModel().addListaDeIngredientes(getIngredientes(receitaIngrediente));
	}
}
