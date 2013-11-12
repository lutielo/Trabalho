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

import br.unisul.dados.Ingrediente;
import br.unisul.dados.Receita_Ingrediente;
import br.unisul.dados.Unidade;
import br.unisul.dao.DAOException;
import br.unisul.dao.Receita_IngredienteDAO;
import br.unisul.gui.relatorios.tablemodels.CellRenderer;
import br.unisul.gui.relatorios.tablemodels.ReceitasQueUsamIngredienteTableModel;

public class ListagemReceitasQueUsamIngrediente extends JFrame {

	private static final long serialVersionUID = 1L;

	private List<Receita_Ingrediente> listaIngredientesMaisUtilizados;
	private ReceitasQueUsamIngredienteTableModel atm;
	private JTable tblIngredientes;
	private JScrollPane spListagemIngredientes;
	private JLabel lblReceitasQueUsamIngrediente;

	public ListagemReceitasQueUsamIngrediente(Unidade unidade, Ingrediente ingrediente) {
		super("Receitas Que Usam o Ingrediente " + ingrediente.getNome() +" em "+ unidade.getTipo());
		this.setSize(443, 453);
		this.setResizable(false);
		this.setType(Type.UTILITY);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setLocationRelativeTo(null);
		getContentPane().setLayout(null);

		this.abreTela(unidade, ingrediente);
	}

	private void abreTela(Unidade unidade, Ingrediente ingrediente) {
		spListagemIngredientes = new JScrollPane(getTblIngredientes());
		spListagemIngredientes.setBounds(10, 75, 415, 328);
		getContentPane().add(spListagemIngredientes);
		this.addIngredientes(unidade, ingrediente);

		getTblIngredientes().setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		getTblIngredientes().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		lblReceitasQueUsamIngrediente = new JLabel("Receitas Que Usam o Ingrediente:");
		lblReceitasQueUsamIngrediente.setFont(new Font("Tahoma", Font.PLAIN, 19));
		lblReceitasQueUsamIngrediente.setBounds(69, 11, 331, 27);
		getContentPane().add(lblReceitasQueUsamIngrediente);
		
		JLabel lblIngrediente = new JLabel(ingrediente.getNome() +" em "+ unidade.getTipo());
		lblIngrediente.setBounds(92, 49, 253, 14);
		getContentPane().add(lblIngrediente);

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
			tblIngredientes.setModel(new ReceitasQueUsamIngredienteTableModel());
		}
		return tblIngredientes;
	}

	private ReceitasQueUsamIngredienteTableModel getModel() {
		if (atm == null) {
			atm = (ReceitasQueUsamIngredienteTableModel) getTblIngredientes().getModel();
		}
		return atm;
	}

	private List<Receita_Ingrediente> getIngredientes(Unidade unidade, Ingrediente ingrediente) {
		Receita_IngredienteDAO receita_IngredienteDAO = new Receita_IngredienteDAO();
		listaIngredientesMaisUtilizados = new ArrayList<>();
		try {
			listaIngredientesMaisUtilizados = receita_IngredienteDAO.listeReceitaQueUsamIngrediente(unidade, ingrediente);
		} catch (DAOException e) {
			e.printStackTrace();
		}
		return listaIngredientesMaisUtilizados;
	}

	private void addIngredientes(Unidade unidade, Ingrediente ingrediente) {
		getModel().addListaDeIngredientes(getIngredientes(unidade, ingrediente));
	}
}
