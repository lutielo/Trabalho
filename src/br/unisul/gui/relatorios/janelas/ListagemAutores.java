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

import br.unisul.dados.Autor;
import br.unisul.dao.AutorDAO;
import br.unisul.dao.DAOException;
import br.unisul.gui.relatorios.tablemodels.AutorTableModel;
import br.unisul.gui.relatorios.tablemodels.CellRenderer;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;

public class ListagemAutores extends JFrame {

	private static final long serialVersionUID = 1L;

	private List<Autor> listaAutores;
	private AutorTableModel atm;
	private JTable tblAutores;
	private JTextField tfCodigo;
	private JTextField tfNome;
	private JScrollPane spListagemAutores;
	private JButton btnExcluir;
	private JButton btnEditar;
	private JButton btnFechar;
	private JButton btnPesquisar;
	private JLabel lblCodigo;
	private JLabel lblNome;
	private JLabel lblListagemAutores;

	public ListagemAutores() {
		super("Listagem Autores");
		this.setSize(562, 548);
		this.setResizable(false);
		this.setType(Type.UTILITY);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setLocationRelativeTo(null);
		getContentPane().setLayout(null);

		this.abreTela();
	}

	private void abreTela() {
		spListagemAutores = new JScrollPane(getTblAutores());
		spListagemAutores.setBounds(30, 136, 481, 328);
		getContentPane().add(spListagemAutores);
		this.addAutores();

		getTblAutores().setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		getTblAutores().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		btnExcluir = new JButton("Excluir");
		btnExcluir.setBounds(219, 476, 89, 23);
		getContentPane().add(btnExcluir);

		btnEditar = new JButton("Editar");
		btnEditar.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				// RESGATANDO CODIGO DA ROW SELECIONADA
				int i = getTblAutores().getSelectedRow();
				try {
					String codigo = getTblAutores().getValueAt(i, 0).toString();
					// TODO LUTIELO - ABRE TELA DE IGUAL A DE CADASTRO COM OS CAMPOS PREENCHIDOS JÁ
					JOptionPane.showMessageDialog(null, "Codigo do autor da linha selecionada : " + codigo);
				} catch (IndexOutOfBoundsException e1) {
					JOptionPane.showMessageDialog(null, "Para editar selecione um autor");
				}
			}
		});

		btnEditar.setBounds(120, 476, 89, 23);
		getContentPane().add(btnEditar);

		btnFechar = new JButton("Fechar");
		btnFechar.setBounds(318, 476, 89, 23);
		getContentPane().add(btnFechar);

		lblCodigo = new JLabel("C\u00F3digo:");
		lblCodigo.setBounds(32, 102, 46, 23);
		getContentPane().add(lblCodigo);

		tfCodigo = new JTextField();
		tfCodigo.setBounds(77, 103, 46, 20);
		getContentPane().add(tfCodigo);
		tfCodigo.setColumns(10);

		lblNome = new JLabel("Nome:");
		lblNome.setBounds(140, 106, 46, 14);
		getContentPane().add(lblNome);

		tfNome = new JTextField();
		tfNome.setColumns(10);
		tfNome.setBounds(187, 103, 187, 20);
		getContentPane().add(tfNome);

		btnPesquisar = new JButton("Pesquisar");
		btnPesquisar.setBounds(392, 102, 102, 23);
		getContentPane().add(btnPesquisar);

		lblListagemAutores = new JLabel("Listagem de  Autores");
		lblListagemAutores.setFont(new Font("Tahoma", Font.PLAIN, 19));
		lblListagemAutores.setBounds(182, 22, 192, 29);
		getContentPane().add(lblListagemAutores);

		JLabel lblParaPesquisasMais = new JLabel("Para uma pesquisa mais avan\u00E7adas digite algum filtro:");
		lblParaPesquisasMais.setBounds(30, 73, 327, 14);
		getContentPane().add(lblParaPesquisasMais);

		TableColumn col0 = getTblAutores().getColumnModel().getColumn(0);
		col0.setPreferredWidth(70);

		TableColumn col1 = getTblAutores().getColumnModel().getColumn(1);
		col1.setPreferredWidth(300);

		TableColumn col2 = getTblAutores().getColumnModel().getColumn(2);
		col2.setPreferredWidth(100);
		getTblAutores().setDefaultRenderer(Object.class, new CellRenderer());
	}

	private JTable getTblAutores() {
		if (tblAutores == null) {
			tblAutores = new JTable();
			tblAutores.setModel(new AutorTableModel());
		}
		return tblAutores;
	}

	private AutorTableModel getModel() {
		if (atm == null) {
			atm = (AutorTableModel) getTblAutores().getModel();
		}
		return atm;
	}

	private List<Autor> getAutores() {
		AutorDAO autorDAO = new AutorDAO();
		listaAutores = new ArrayList<>();
		try {
			listaAutores = autorDAO.listarTodosAutores();
		} catch (DAOException e) {
			e.printStackTrace();
		}
		return listaAutores;
	}

	private void addAutores() {
		getModel().addListaDeAutores(getAutores());
	}

	public static void main(String[] args) {
		ListagemAutores listagemAutores = new ListagemAutores();
		listagemAutores.setVisible(true);
	}
}
