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
import javax.swing.ListSelectionModel;
import javax.swing.table.TableColumn;

import br.unisul.dados.Ingrediente;
import br.unisul.dados.ReceitaIngrediente;
import br.unisul.dados.Unidade;
import br.unisul.dao.DAOException;
import br.unisul.dao.ReceitaIngredienteDAO;
import br.unisul.gui.relatorios.tablemodels.CellRenderer;
import br.unisul.gui.relatorios.tablemodels.IngredientesMaisUtilizadosTableModel;

public class ListagemIngredientesMaisUtilizados extends JFrame {

	private static final long serialVersionUID = 1L;
	
	private PrintWriter logErro;
	private List<ReceitaIngrediente> listaIngredientesMaisUtilizados;
	private IngredientesMaisUtilizadosTableModel imutm;
	private JTable tblIngredientes;
	private JScrollPane spListagemIngredientes;
	private JLabel lblListagemIngredientesMaisUtilizados;
	private JLabel lblCliqueParaVisualizar;
	private JButton btnVisualizar;
	private JButton btnFechar; 

	public ListagemIngredientesMaisUtilizados() {
		super("Listagem ingredientes mais utilizados");
		this.setSize(443, 453);
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
		
		this.abreTela();
	}

	private void abreTela() {
		spListagemIngredientes = new JScrollPane(getTblIngredientes());
		spListagemIngredientes.setBounds(10, 63, 415, 305);

		lblListagemIngredientesMaisUtilizados = new JLabel("Listagem de Ingredientes Mais Utilizados");
		lblListagemIngredientesMaisUtilizados.setFont(new Font("Tahoma", Font.PLAIN, 19));
		lblListagemIngredientesMaisUtilizados.setBounds(36, 23, 389, 29);

		btnVisualizar = new JButton("Visualizar");
		TrataEventoVisualizar trataEventoVisualizar = new TrataEventoVisualizar();
		btnVisualizar.addActionListener(trataEventoVisualizar);
		btnVisualizar.setBounds(90, 395, 104, 23);

		lblCliqueParaVisualizar = new JLabel("Clique para visualizar as receitas que usam o ingrediente selecionado");
		lblCliqueParaVisualizar.setBounds(20, 370, 405, 14);

		btnFechar = new JButton("Fechar");
		TrataEventoFechar trataEventoFechar = new TrataEventoFechar();
		btnFechar.addActionListener(trataEventoFechar);
		btnFechar.setBounds(219, 395, 104, 23);

		getContentPane().add(btnFechar);
		getContentPane().add(lblCliqueParaVisualizar);
		getContentPane().add(btnVisualizar);
		getContentPane().add(spListagemIngredientes);
		getContentPane().add(lblListagemIngredientesMaisUtilizados);

		configuraTable();
	}

	private void configuraTable() {
		TableColumn col0 = getTblIngredientes().getColumnModel().getColumn(0);
		col0.setPreferredWidth(90);

		TableColumn col1 = getTblIngredientes().getColumnModel().getColumn(1);
		col1.setPreferredWidth(200);

		TableColumn col2 = getTblIngredientes().getColumnModel().getColumn(2);
		col2.setPreferredWidth(100);

		getTblIngredientes().setDefaultRenderer(Object.class, new CellRenderer());

		getTblIngredientes().setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		getTblIngredientes().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		this.addIngredientes();
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

	private void addIngredientes() {
		getModel().addListaDeIngredientes(getIngredientes());
	}

	private List<ReceitaIngrediente> getIngredientes() {
		ReceitaIngredienteDAO receitaIngredienteDAO = new ReceitaIngredienteDAO();
		listaIngredientesMaisUtilizados = new ArrayList<>();
		try {
			listaIngredientesMaisUtilizados = receitaIngredienteDAO.listarIngredientesMaisUtilizados();
		} catch (DAOException e) {
			JOptionPane.showMessageDialog(null, "Sua requisi��o n�o foi processada.", "Erro", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace(logErro);
			logErro.flush();
		}
		return listaIngredientesMaisUtilizados;
	}

	public void fecharTela() {
		this.dispose();
	}

	private class TrataEventoVisualizar implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			if (getTblIngredientes().getSelectedRow() == -1) {
				JOptionPane.showMessageDialog(null, "Selecione um ingrediente");
			} else {
				int selectedRow = getTblIngredientes().getSelectedRow();
				ReceitaIngrediente ri = listaIngredientesMaisUtilizados.get(selectedRow);
				Integer codUnidade = ri.getUnidade().getCodigo();
				String nomeUnidade = ri.getUnidade().getTipo();
				Unidade unidade = new Unidade(codUnidade, nomeUnidade);

				Integer codIngrediente = ri.getIngrediente().getCodigo();
				String nomeIngrediente = ri.getIngrediente().getNome();
				Ingrediente ingrediente = new Ingrediente(codIngrediente, nomeIngrediente);

				ListagemReceitasQueUsamIngrediente listagemReceitasQueUsamIngrediente = new ListagemReceitasQueUsamIngrediente(unidade,
					ingrediente);
				listagemReceitasQueUsamIngrediente.setVisible(true);
			}
		}
	}

	private class TrataEventoFechar implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			fecharTela();
		}

	}

	public static void main(String[] args) {
		ListagemIngredientesMaisUtilizados listagemIngredientesMaisUtilizados = new ListagemIngredientesMaisUtilizados();
		listagemIngredientesMaisUtilizados.setVisible(true);
	}
}
