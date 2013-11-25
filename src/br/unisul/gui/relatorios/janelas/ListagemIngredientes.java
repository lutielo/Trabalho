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

import br.unisul.dados.Ingrediente;
import br.unisul.dao.DAOException;
import br.unisul.dao.IngredienteDAO;
import br.unisul.gui.alteracoes.EditaIngrediente;
import br.unisul.gui.relatorios.tablemodels.CellRenderer;
import br.unisul.gui.relatorios.tablemodels.IngredienteTableModel;
import br.unisul.util.StringUtils;

public class ListagemIngredientes extends JFrame {

	private static final long serialVersionUID = 1L;

	private List<Ingrediente> listaIngredientes;
	private IngredienteTableModel itm;
	private JTable tblIngredientes;
	private JTextField tfCodigo;
	private JTextField tfNome;
	private JScrollPane spListagemIngredientes;
	private JButton btnExcluir;
	private JButton btnEditar;
	private JButton btnCancelar;
	private JButton btnPesquisar;
	private JLabel lblCodigo;
	private JLabel lblNome;
	private JLabel lblListagemIngredientes;
	private JLabel lblPesquisa;

	public ListagemIngredientes() {
		super("Listagem Ingredientes");
		this.setSize(500, 560);
		this.setResizable(false);
		this.setType(Type.UTILITY);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setLocationRelativeTo(null);
		getContentPane().setLayout(null);

		this.abreTela();
	}

	private void abreTela() {
		spListagemIngredientes = new JScrollPane(getTblIngredientes());
		spListagemIngredientes.setBounds(40, 139, 415, 328);

		btnExcluir = new JButton("Excluir");
		TrataEventoExcluir trataEventoExcluir = new TrataEventoExcluir();
		btnExcluir.addActionListener(trataEventoExcluir);
		btnExcluir.setBounds(195, 478, 89, 23);

		btnEditar = new JButton("Editar");
		TrataEventoEditar trataEventoEditar = new TrataEventoEditar();
		btnEditar.addActionListener(trataEventoEditar);
		btnEditar.setBounds(96, 478, 89, 23);

		btnCancelar = new JButton("Cancelar");
		TrataEventoCancelar trataEventoCancelar = new TrataEventoCancelar();
		btnCancelar.addActionListener(trataEventoCancelar);
		btnCancelar.setBounds(296, 478, 89, 23);

		lblCodigo = new JLabel("C\u00F3digo:");
		lblCodigo.setBounds(30, 98, 46, 23);

		tfCodigo = new JTextField();
		tfCodigo.setBounds(75, 99, 46, 20);
		tfCodigo.setColumns(10);

		lblNome = new JLabel("Nome:");
		lblNome.setBounds(138, 102, 46, 14);

		tfNome = new JTextField();
		tfNome.setColumns(10);
		tfNome.setBounds(185, 99, 150, 20);

		btnPesquisar = new JButton("Pesquisar");
		TrataEventoPesquisar trataEventoPesquisar = new TrataEventoPesquisar();
		btnPesquisar.addActionListener(trataEventoPesquisar);
		btnPesquisar.setBounds(353, 98, 102, 23);

		lblListagemIngredientes = new JLabel("Listagem de  Ingredientes");
		lblListagemIngredientes.setFont(new Font("Tahoma", Font.PLAIN, 19));
		lblListagemIngredientes.setBounds(138, 23, 239, 29);

		lblPesquisa = new JLabel("Para uma pesquisa mais avan\u00E7adas digite algum filtro:");
		lblPesquisa.setBounds(30, 73, 327, 14);

		getContentPane().add(lblPesquisa);
		getContentPane().add(lblCodigo);
		getContentPane().add(lblNome);
		getContentPane().add(lblListagemIngredientes);
		getContentPane().add(tfNome);
		getContentPane().add(tfCodigo);
		getContentPane().add(btnPesquisar);
		getContentPane().add(btnCancelar);
		getContentPane().add(btnEditar);
		getContentPane().add(btnExcluir);
		getContentPane().add(spListagemIngredientes);

		configuraTable();
	}

	private void configuraTable() {
		TableColumn col0 = getTblIngredientes().getColumnModel().getColumn(0);
		col0.setPreferredWidth(70);

		TableColumn col1 = getTblIngredientes().getColumnModel().getColumn(1);
		col1.setPreferredWidth(290);
		getTblIngredientes().setDefaultRenderer(Object.class, new CellRenderer());
		
		getTblIngredientes().setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		getTblIngredientes().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		this.addIngredientes();
	}

	private void addIngredientes() {
		getModel().addListaDeIngredientes(getIngredientes());
	}

	private IngredienteTableModel getModel() {
		if (itm == null) {
			itm = (IngredienteTableModel) getTblIngredientes().getModel();
		}
		return itm;
	}

	private JTable getTblIngredientes() {
		if (tblIngredientes == null) {
			tblIngredientes = new JTable();
			tblIngredientes.setModel(new IngredienteTableModel());
		}
		return tblIngredientes;
	}

	private List<Ingrediente> getIngredientes() {
		IngredienteDAO ingredienteDAO = new IngredienteDAO();
		listaIngredientes = new ArrayList<>();
		try {
			listaIngredientes = ingredienteDAO.listeTodosIngredientes();
		} catch (DAOException e) {
			e.printStackTrace();
		}
		return listaIngredientes;
	}

	public void fecharTela() {
		this.dispose();
	}

	private class TrataEventoPesquisar implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if (!StringUtils.isNuloOuBranco(tfCodigo.getText())) {
				if (validarValorCodigo()) {
					boolean numeroInvalido = true;
					for (Ingrediente ingrediente : listaIngredientes) {
						Integer codigo = Integer.parseInt(tfCodigo.getText());
						if (ingrediente.getCodigo() == codigo) {
							listarIngredienteCodigo();
							numeroInvalido = false;
						}
					}
					if (numeroInvalido) {
						JOptionPane.showMessageDialog(null, "Digite um código existente");
					}
				}
			} else if (!StringUtils.isNuloOuBranco(tfNome.getText())) {
				listarIngredientesNome();
			}
		}

		private boolean validarValorCodigo() {
			try {
				Integer.parseInt(tfCodigo.getText());
				return true;
			} catch (NumberFormatException e) {
				JOptionPane.showMessageDialog(null, "Insira um valor válido");
				return false;
			}
		}

		private void listarIngredientesNome() {
			String nomeIngrediente = tfNome.getText().toString();
			try {
				IngredienteDAO ingredienteDAO = new IngredienteDAO();
				List<Ingrediente> ingredientes = ingredienteDAO.listeTodosIngredientesPeloNome(nomeIngrediente);
				if (!ingredientes.isEmpty()) {
					getModel().limpar();
					getModel().addListaDeIngredientes(ingredientes);
				} else {
					JOptionPane.showMessageDialog(null, "Nenhum resultado encontrado");
				}
			} catch (DAOException e) {
				JOptionPane.showMessageDialog(null, "Sua requisição não foi processada");
			}
		}

		private void listarIngredienteCodigo() {
			int codigoIngrediente = Integer.parseInt(tfCodigo.getText().toString());
			try {
				IngredienteDAO ingredienteDAO = new IngredienteDAO();
				Ingrediente ingrediente = ingredienteDAO.listeDadosDoIngredientePeloCodigo(codigoIngrediente);
				getModel().limpar();
				getModel().addIngrediente(ingrediente);
			} catch (DAOException e) {
				JOptionPane.showMessageDialog(null, "Sua requisição não foi processada");
			}
		}
	}

	private class TrataEventoEditar implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			int selectedRow = getTblIngredientes().getSelectedRow();
			editaIngrediente(selectedRow);
		}

		private void editaIngrediente(int selectedRow) {
			try {
				int codigoIngrediente = Integer.parseInt(getTblIngredientes().getValueAt(selectedRow, 0).toString());
				IngredienteDAO ingredienteDAO = new IngredienteDAO();
				try {
					Ingrediente ingrediente = ingredienteDAO.listeDadosDoIngredientePeloCodigo(codigoIngrediente);
					EditaIngrediente editaIngrediente = new EditaIngrediente(ingrediente);
					editaIngrediente.setVisible(true);
				} catch (DAOException e) {
					e.printStackTrace();
				}
			} catch (IndexOutOfBoundsException e1) {
				JOptionPane.showMessageDialog(null, "Para editar selecione um ingrediente");
			}
		}
	}

	private class TrataEventoExcluir implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			int selectedRow = getTblIngredientes().getSelectedRow();
			deletarIngrediente(selectedRow);
		}

		private void deletarIngrediente(int selectedRow) {
			try {
				int codigoIngrediente = Integer.parseInt(getTblIngredientes().getValueAt(selectedRow, 0).toString());
				String nomeIngrediente = getTblIngredientes().getValueAt(selectedRow, 1).toString();
				IngredienteDAO ingredienteDAO = new IngredienteDAO();
				try {
					int dialogButton = JOptionPane.showConfirmDialog(null, "Deseja excluir " + nomeIngrediente + "?", "Atenção",
						JOptionPane.YES_NO_OPTION);
					if (dialogButton == JOptionPane.YES_OPTION) {
						ingredienteDAO.deletaIngrediente(new Ingrediente(codigoIngrediente, null));
						JOptionPane.showMessageDialog(null, "Ingrediente deletado com sucesso.");
						getModel().limpar();
						configuraTable();
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

	public static void main(String[] args) {
		ListagemIngredientes listagemIngredientes = new ListagemIngredientes();
		listagemIngredientes.setVisible(true);
	}
}
