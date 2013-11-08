package br.unisul.gui.relatorios.janelas;

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
import br.unisul.dao.IngredienteDAO;
import br.unisul.dao.DAOException;
import br.unisul.gui.relatorios.tablemodels.IngredienteTableModel;
import br.unisul.gui.relatorios.tablemodels.CellRenderer;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;

public class ListagemIngredientes extends JFrame {

	private static final long serialVersionUID = 1L;

	private List<Ingrediente> listaIngredientes;
	private IngredienteTableModel atm;
	private JTable tblIngredientes;
	private JTextField tfCodigo;
	private JTextField tfNome;
	private JScrollPane spListagemIngredientes;
	private JButton btnExcluir;
	private JButton btnEditar;
	private JButton btnFechar;
	private JButton btnPesquisar;
	private JLabel lblCodigo;
	private JLabel lblNome;
	private JLabel lblListagemIngredientes;

	public ListagemIngredientes() {
		super("Listagem Ingredientes");
		this.setSize(440, 580);
		this.setResizable(false);
		this.setType(Type.UTILITY);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setLocationRelativeTo(null);
		getContentPane().setLayout(null);

		this.abreTela();
	}

	private void abreTela() {
		spListagemIngredientes = new JScrollPane(getTblIngredientes());
		spListagemIngredientes.setBounds(30, 183, 370, 328);
		getContentPane().add(spListagemIngredientes);
		this.addIngredientes();

		getTblIngredientes().setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		getTblIngredientes().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		btnExcluir = new JButton("Excluir");
		btnExcluir.setBounds(176, 522, 89, 23);
		getContentPane().add(btnExcluir);

		btnEditar = new JButton("Editar");
		btnEditar.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				// RESGATANDO CODIGO DA ROW SELECIONADA
				int i = getTblIngredientes().getSelectedRow();
				try {
					String codigo = getTblIngredientes().getValueAt(i, 0).toString();
					// TODO LUTIELO - ABRE TELA DE IGUAL A DE CADASTRO COM OS CAMPOS PREENCHIDOS JÁ
					JOptionPane.showMessageDialog(null, "Codigo do ingrediente da linha selecionada : " + codigo);
				} catch (IndexOutOfBoundsException e1) {
					JOptionPane.showMessageDialog(null, "Para editar selecione um ingrediente");
				}
			}
		});

		btnEditar.setBounds(77, 522, 89, 23);
		getContentPane().add(btnEditar);

		btnFechar = new JButton("Fechar");
		btnFechar.setBounds(275, 522, 89, 23);
		getContentPane().add(btnFechar);

		lblCodigo = new JLabel("C\u00F3digo:");
		lblCodigo.setBounds(30, 98, 46, 23);
		getContentPane().add(lblCodigo);

		tfCodigo = new JTextField();
		tfCodigo.setBounds(75, 99, 46, 20);
		getContentPane().add(tfCodigo);
		tfCodigo.setColumns(10);

		lblNome = new JLabel("Nome:");
		lblNome.setBounds(138, 102, 46, 14);
		getContentPane().add(lblNome);

		tfNome = new JTextField();
		tfNome.setColumns(10);
		tfNome.setBounds(185, 99, 150, 20);
		getContentPane().add(tfNome);

		btnPesquisar = new JButton("Pesquisar");
		btnPesquisar.setBounds(163, 127, 102, 23);
		getContentPane().add(btnPesquisar);

		lblListagemIngredientes = new JLabel("Listagem de  Ingredientes");
		lblListagemIngredientes.setFont(new Font("Tahoma", Font.PLAIN, 19));
		lblListagemIngredientes.setBounds(96, 22, 239, 29);
		getContentPane().add(lblListagemIngredientes);

		JLabel lblParaPesquisasMais = new JLabel("Para uma pesquisa mais avan\u00E7adas digite algum filtro:");
		lblParaPesquisasMais.setBounds(30, 73, 327, 14);
		getContentPane().add(lblParaPesquisasMais);

		TableColumn col0 = getTblIngredientes().getColumnModel().getColumn(0);
		col0.setPreferredWidth(70);

		TableColumn col1 = getTblIngredientes().getColumnModel().getColumn(1);
		col1.setPreferredWidth(290);

		getTblIngredientes().setDefaultRenderer(Object.class, new CellRenderer());
	}

	private JTable getTblIngredientes() {
		if (tblIngredientes == null) {
			tblIngredientes = new JTable();
			tblIngredientes.setModel(new IngredienteTableModel());
		}
		return tblIngredientes;
	}

	private IngredienteTableModel getModel() {
		if (atm == null) {
			atm = (IngredienteTableModel) getTblIngredientes().getModel();
		}
		return atm;
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

	private void addIngredientes() {
		getModel().addListaDeIngredientes(getIngredientes());
	}

	public static void main(String[] args) {
		ListagemIngredientes listagemIngredientes = new ListagemIngredientes();
		listagemIngredientes.setVisible(true);
	}
}
