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
import br.unisul.dados.ReceitaIngrediente;
import br.unisul.dados.Unidade;
import br.unisul.dao.DAOException;
import br.unisul.dao.ReceitaIngredienteDAO;
import br.unisul.gui.relatorios.tablemodels.CellRenderer;
import br.unisul.gui.relatorios.tablemodels.ReceitasQueUsamIngredienteTableModel;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ListagemReceitasQueUsamIngrediente extends JFrame {

	private static final long serialVersionUID = 1L;

	private List<ReceitaIngrediente> listaIngredientesMaisUtilizados;
	private ReceitasQueUsamIngredienteTableModel rquitm;
	private JTable tblIngredientes;
	private JScrollPane spListagemIngredientes;
	private JLabel lblReceitasQueUsamIngrediente;
	private JTextField tfNomeIngredienteUsado;
	private JButton btnFechar;

	public ListagemReceitasQueUsamIngrediente(Unidade unidade, Ingrediente ingrediente) {
		super("Receitas que usam " + ingrediente.getNome().toLowerCase() + " em " + unidade.getTipo().toLowerCase());
		this.setSize(443, 465);
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

		tfNomeIngredienteUsado = new JTextField("");
		tfNomeIngredienteUsado.setEditable(false);
		tfNomeIngredienteUsado.setBounds(79, 44, 260, 20);
		tfNomeIngredienteUsado.setText(ingrediente.getNome() + " em " + unidade.getTipo().toLowerCase());
		getContentPane().add(tfNomeIngredienteUsado);
		tfNomeIngredienteUsado.setColumns(10);
		
		btnFechar = new JButton("Fechar");
		btnFechar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//TODO - LUTIELO arrumar action do botao fechar
			}
		});
		btnFechar.setBounds(159, 414, 104, 23);
		getContentPane().add(btnFechar);

		configuraTable();
	}

	private void configuraTable() {
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
			tblIngredientes.setModel(new ReceitasQueUsamIngredienteTableModel());
		}
		return tblIngredientes;
	}

	private ReceitasQueUsamIngredienteTableModel getModel() {
		if (rquitm == null) {
			rquitm = (ReceitasQueUsamIngredienteTableModel) getTblIngredientes().getModel();
		}
		return rquitm;
	}

	private List<ReceitaIngrediente> getIngredientes(Unidade unidade, Ingrediente ingrediente) {
		ReceitaIngredienteDAO receitaIngredienteDAO = new ReceitaIngredienteDAO();
		listaIngredientesMaisUtilizados = new ArrayList<>();
		try {
			listaIngredientesMaisUtilizados = receitaIngredienteDAO.listarReceitaQueUsamIngrediente(unidade, ingrediente);
		} catch (DAOException e) {
			e.printStackTrace();
		}
		return listaIngredientesMaisUtilizados;
	}

	private void addIngredientes(Unidade unidade, Ingrediente ingrediente) {
		getModel().addListaDeIngredientes(getIngredientes(unidade, ingrediente));
	}
}
