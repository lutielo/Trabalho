package br.unisul.gui.relatorios.janelas;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.table.TableColumn;

import br.unisul.dados.Ingrediente;
import br.unisul.dados.ReceitaIngrediente;
import br.unisul.dados.Unidade;
import br.unisul.dao.DAOException;
import br.unisul.dao.ReceitaIngredienteDAO;
import br.unisul.gui.relatorios.tablemodels.CellRenderer;
import br.unisul.gui.relatorios.tablemodels.ReceitasQueUsamIngredienteTableModel;

public class ListagemReceitasQueUsamIngrediente extends JFrame {

	private static final long serialVersionUID = 1L;
	
	private PrintWriter logErro;
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
		
		try {
			logErro = new PrintWriter(new FileOutputStream(new File("C:\\temp\\logAplicacaoTrabalhoProg2.txt"), true));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		this.abreTela(unidade, ingrediente);
	}

	private void abreTela(Unidade unidade, Ingrediente ingrediente) {
		spListagemIngredientes = new JScrollPane(getTblIngredientes());
		spListagemIngredientes.setBounds(10, 75, 415, 328);

		lblReceitasQueUsamIngrediente = new JLabel("Receitas Que Usam o Ingrediente:");
		lblReceitasQueUsamIngrediente.setFont(new Font("Tahoma", Font.PLAIN, 19));
		lblReceitasQueUsamIngrediente.setBounds(69, 11, 331, 27);

		tfNomeIngredienteUsado = new JTextField("");
		tfNomeIngredienteUsado.setEditable(false);
		tfNomeIngredienteUsado.setBounds(79, 44, 260, 20);
		tfNomeIngredienteUsado.setText(ingrediente.getNome() + " em " + unidade.getTipo().toLowerCase());
		tfNomeIngredienteUsado.setColumns(10);

		btnFechar = new JButton("Fechar");
		TrataEventoFechar trataEventoFechar = new TrataEventoFechar();
		btnFechar.addActionListener(trataEventoFechar);
		btnFechar.setBounds(159, 414, 104, 23);

		getContentPane().add(btnFechar);
		getContentPane().add(tfNomeIngredienteUsado);
		getContentPane().add(lblReceitasQueUsamIngrediente);
		getContentPane().add(spListagemIngredientes);

		configuraTable(unidade, ingrediente);
	}

	private void configuraTable(Unidade unidade, Ingrediente ingrediente) {
		TableColumn col0 = getTblIngredientes().getColumnModel().getColumn(0);
		col0.setPreferredWidth(150);

		TableColumn col1 = getTblIngredientes().getColumnModel().getColumn(1);
		col1.setPreferredWidth(200);

		TableColumn col2 = getTblIngredientes().getColumnModel().getColumn(2);
		col2.setPreferredWidth(80);

		getTblIngredientes().setDefaultRenderer(Object.class, new CellRenderer());
		getTblIngredientes().setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		getTblIngredientes().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		this.addIngredientes(unidade, ingrediente);
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
			JOptionPane.showMessageDialog(null, "Sua requisi��o n�o foi processada.", "Erro", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace(logErro);
			logErro.flush();
		}
		return listaIngredientesMaisUtilizados;
	}

	private void addIngredientes(Unidade unidade, Ingrediente ingrediente) {
		getModel().addListaDeIngredientes(getIngredientes(unidade, ingrediente));
	}

	public void fecharTela() {
		this.dispose();
	}

	private class TrataEventoFechar implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			fecharTela();
		}
	}
}
