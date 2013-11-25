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
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.table.TableColumn;

import br.unisul.dados.ReceitaIngrediente;
import br.unisul.dao.DAOException;
import br.unisul.dao.ReceitaIngredienteDAO;
import br.unisul.gui.alteracoes.EditaReceitaIngrediente;
import br.unisul.gui.cadastros.CadastraNovoIngredienteReceita;
import br.unisul.gui.relatorios.tablemodels.CellRenderer;
import br.unisul.gui.relatorios.tablemodels.ReceitaIngredienteTableModel;

public class ListagemIngredientesDaReceita extends JFrame {

	private static final long serialVersionUID = 1L;

	private List<ReceitaIngrediente> listaIngredientesDaReceita;
	private ReceitaIngredienteTableModel ritm;
	private JTable tblReceitaIngredientes;
	private JScrollPane spListagemIngredientes;
	private JLabel lblIngredientesDaReceita;
	private JTextField tfNomeIngredienteUsado;
	private JButton btnEditar;
	private JButton btnExcluir;
	private JButton btnCancelar;
	private JButton btnNovo;

	public ListagemIngredientesDaReceita(ReceitaIngrediente receitaIngrediente) {
		super("Ingredientes da receita " + receitaIngrediente.getReceita().getNome());
		this.setSize(442, 480);
		this.setResizable(false);
		this.setType(Type.UTILITY);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setLocationRelativeTo(null);
		getContentPane().setLayout(null);

		this.abreTela(receitaIngrediente);
	}

	private void abreTela(ReceitaIngrediente receitaIngrediente) {
		spListagemIngredientes = new JScrollPane(getTblReceitaIngredientes());
		spListagemIngredientes.setBounds(10, 75, 415, 328);

		lblIngredientesDaReceita = new JLabel("Ingredientes da Receita:");
		lblIngredientesDaReceita.setFont(new Font("Tahoma", Font.PLAIN, 19));
		lblIngredientesDaReceita.setBounds(69, 11, 331, 27);

		tfNomeIngredienteUsado = new JTextField("");
		tfNomeIngredienteUsado.setEditable(false);
		tfNomeIngredienteUsado.setBounds(79, 44, 260, 20);
		tfNomeIngredienteUsado.setText(receitaIngrediente.getReceita().getNome());
		tfNomeIngredienteUsado.setColumns(10);

		btnEditar = new JButton("Editar");
		btnEditar.setBounds(119, 414, 89, 23);
		TrataEventoEditar trataEventoEditar = new TrataEventoEditar();
		btnEditar.addActionListener(trataEventoEditar);

		btnExcluir = new JButton("Excluir");
		btnExcluir.setBounds(218, 414, 89, 23);
		TrataEventoExcluir trataEventoExcluir = new TrataEventoExcluir();
		btnExcluir.addActionListener(trataEventoExcluir);

		btnCancelar = new JButton("Cancelar");
		btnCancelar.setBounds(317, 414, 89, 23);
		TrataEventoCancelar trataEventoCancelar = new TrataEventoCancelar();
		btnCancelar.addActionListener(trataEventoCancelar);
		
		
		btnNovo = new JButton("Novo");
		btnNovo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				ReceitaIngrediente receitaIngrediente = new ReceitaIngrediente();
				CadastraNovoIngredienteReceita cadastraNovoIngredienteReceita = new CadastraNovoIngredienteReceita(receitaIngrediente);
				cadastraNovoIngredienteReceita.setVisible(true);
			}
		});
		btnNovo.setBounds(20, 414, 89, 23);

		getContentPane().add(btnCancelar);
		getContentPane().add(spListagemIngredientes);
		getContentPane().add(lblIngredientesDaReceita);
		getContentPane().add(tfNomeIngredienteUsado);
		getContentPane().add(btnEditar);
		getContentPane().add(btnExcluir);
		getContentPane().add(btnNovo);


		configuraTable(receitaIngrediente);
	}

	private void configuraTable(ReceitaIngrediente receitaIngrediente) {
		TableColumn col0 = getTblReceitaIngredientes().getColumnModel().getColumn(0);
		col0.setPreferredWidth(150);

		TableColumn col1 = getTblReceitaIngredientes().getColumnModel().getColumn(1);
		col1.setPreferredWidth(200);

		TableColumn col2 = getTblReceitaIngredientes().getColumnModel().getColumn(2);
		col2.setPreferredWidth(80);

		getTblReceitaIngredientes().setDefaultRenderer(Object.class, new CellRenderer());

		getTblReceitaIngredientes().setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		getTblReceitaIngredientes().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		this.addIngredientes(receitaIngrediente);
	}

	private JTable getTblReceitaIngredientes() {
		if (tblReceitaIngredientes == null) {
			tblReceitaIngredientes = new JTable();
			tblReceitaIngredientes.setModel(new ReceitaIngredienteTableModel());
		}
		return tblReceitaIngredientes;
	}

	private ReceitaIngredienteTableModel getModel() {
		if (ritm == null) {
			ritm = (ReceitaIngredienteTableModel) getTblReceitaIngredientes().getModel();
		}
		return ritm;
	}

	private void addIngredientes(ReceitaIngrediente receitaIngrediente) {
		getModel().addListaDeIngredientes(getIngredientes(receitaIngrediente));
	}

	private List<ReceitaIngrediente> getIngredientes(ReceitaIngrediente receitaIngrediente) {
		ReceitaIngredienteDAO receitaIngredienteDAO = new ReceitaIngredienteDAO();
		listaIngredientesDaReceita = new ArrayList<>();
		try {
			listaIngredientesDaReceita = receitaIngredienteDAO.listarIngredientesDaReceita(receitaIngrediente);
		} catch (DAOException e) {
			e.printStackTrace();
		}
		return listaIngredientesDaReceita;
	}

	public void fecharTela() {
		this.dispose();
	}

	private class TrataEventoEditar implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			int selectedRow = getTblReceitaIngredientes().getSelectedRow();
			editaIngrediente(selectedRow);
		}

		private void editaIngrediente(int selectedRow) {
			try {
				EditaReceitaIngrediente editaReceitaIngrediente = new EditaReceitaIngrediente(listaIngredientesDaReceita.get(selectedRow));
				editaReceitaIngrediente.setVisible(true);
			} catch (IndexOutOfBoundsException e1) {
				JOptionPane.showMessageDialog(null, "Para editar selecione um ingrediente");
			}
		}
	}

	private class TrataEventoExcluir implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			int selectedRow = getTblReceitaIngredientes().getSelectedRow();
			deletarIngredienteDaReceita(selectedRow);
		}

		private void deletarIngredienteDaReceita(int selectedRow) {
			try {
				String nomeIngrediente = getTblReceitaIngredientes().getValueAt(selectedRow, 0).toString();
				String nomeReceita = listaIngredientesDaReceita.get(selectedRow).getReceita().getNome();
				ReceitaIngredienteDAO receitaIngredienteDAO = new ReceitaIngredienteDAO();
				try {
					int dialogButton = JOptionPane.showConfirmDialog(null, "Deseja excluir " + nomeIngrediente + " da receita" + nomeReceita
						+ "?", "Atenção", JOptionPane.YES_NO_OPTION);
					if (dialogButton == JOptionPane.YES_OPTION) {
						receitaIngredienteDAO.deletaReceitaIngrediente(listaIngredientesDaReceita.get(selectedRow));
						JOptionPane.showMessageDialog(null, "Ingrediente deletado com sucesso.");
						getModel().limpar();
						// configuraTable();
					} else if (dialogButton == JOptionPane.NO_OPTION) {
						JOptionPane.showMessageDialog(null, "Operação cancelada.");
					}
				} catch (DAOException e) {
					e.printStackTrace();
				}
			} catch (IndexOutOfBoundsException e) {
				JOptionPane.showMessageDialog(null, "Para remover selecione um autor");
			}
		}
	}

	private class TrataEventoCancelar implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent event) {
			fecharTela();
		}
	}
}
