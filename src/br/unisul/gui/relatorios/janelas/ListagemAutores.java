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

import br.unisul.dados.Autor;
import br.unisul.dao.AutorDAO;
import br.unisul.dao.DAOException;
import br.unisul.gui.alteracoes.EditaAutor;
import br.unisul.gui.relatorios.tablemodels.AutorTableModel;
import br.unisul.gui.relatorios.tablemodels.CellRenderer;
import br.unisul.util.StringUtils;

public class ListagemAutores extends JFrame {

	private static final long serialVersionUID = 1L;
	
	private PrintWriter logErro;
	private List<Autor> listaAutores;
	private AutorTableModel atm;
	private JTable tblAutores;
	private JTextField tfCodigo;
	private JTextField tfNome;
	private JScrollPane spListagemAutores;
	private JButton btnExcluir;
	private JButton btnEditar;
	private JButton btnCancelar;
	private JButton btnPesquisar;
	private JLabel lblCodigo;
	private JLabel lblNome;
	private JLabel lblListagemAutores;
	private JLabel lblPesquisa;

	public ListagemAutores() {
		super("Listagem Autores");
		this.setSize(562, 548);
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
		spListagemAutores = new JScrollPane(getTblAutores());
		spListagemAutores.setBounds(30, 136, 481, 328);

		btnExcluir = new JButton("Excluir");
		btnExcluir.setBounds(219, 476, 89, 23);
		TrataEventoExcluir trataEventoExcluir = new TrataEventoExcluir();
		btnExcluir.addActionListener(trataEventoExcluir);

		btnEditar = new JButton("Editar");
		btnEditar.setBounds(120, 476, 89, 23);
		TrataEventoEditar trataEventoEditar = new TrataEventoEditar();
		btnEditar.addActionListener(trataEventoEditar);

		btnCancelar = new JButton("Cancelar");
		btnCancelar.setBounds(318, 476, 89, 23);
		TrataEventoCancelar trataEventoCancelar = new TrataEventoCancelar();
		btnCancelar.addActionListener(trataEventoCancelar);

		lblCodigo = new JLabel("C\u00F3digo:");
		lblCodigo.setBounds(32, 102, 46, 23);

		tfCodigo = new JTextField();
		tfCodigo.setBounds(77, 103, 46, 20);
		tfCodigo.setColumns(10);

		lblNome = new JLabel("Nome:");
		lblNome.setBounds(140, 106, 46, 14);

		tfNome = new JTextField();
		tfNome.setBounds(187, 103, 187, 20);
		tfNome.setColumns(10);

		btnPesquisar = new JButton("Pesquisar");
		btnPesquisar.setBounds(392, 102, 102, 23);
		TrataEventoPesquisar trataEventoPesquisar = new TrataEventoPesquisar();
		btnPesquisar.addActionListener(trataEventoPesquisar);

		lblListagemAutores = new JLabel("Listagem de  Autores");
		lblListagemAutores.setBounds(182, 22, 192, 29);
		lblListagemAutores.setFont(new Font("Tahoma", Font.PLAIN, 19));

		lblPesquisa = new JLabel("Para uma pesquisa mais avan\u00E7adas, digite um filtro:");
		lblPesquisa.setBounds(30, 73, 327, 14);

		getContentPane().add(spListagemAutores);
		getContentPane().add(btnExcluir);
		getContentPane().add(btnEditar);
		getContentPane().add(btnCancelar);
		getContentPane().add(lblCodigo);
		getContentPane().add(tfCodigo);
		getContentPane().add(lblNome);
		getContentPane().add(tfNome);
		getContentPane().add(btnPesquisar);
		getContentPane().add(lblListagemAutores);
		getContentPane().add(lblPesquisa);

		configuraTable();
	}

	private void configuraTable() {
		TableColumn col0 = getTblAutores().getColumnModel().getColumn(0);
		col0.setPreferredWidth(70);

		TableColumn col1 = getTblAutores().getColumnModel().getColumn(1);
		col1.setPreferredWidth(300);

		TableColumn col2 = getTblAutores().getColumnModel().getColumn(2);
		col2.setPreferredWidth(100);
		getTblAutores().setDefaultRenderer(Object.class, new CellRenderer());

		getTblAutores().setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		getTblAutores().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		this.addAutores();
	}

	private void addAutores() {
		getModel().limpar();
		getModel().addListaDeAutores(getAutores());
	}

	private AutorTableModel getModel() {
		if (atm == null) {
			atm = (AutorTableModel) getTblAutores().getModel();
		}
		return atm;
	}

	private JTable getTblAutores() {
		if (tblAutores == null) {
			tblAutores = new JTable();
			tblAutores.setModel(new AutorTableModel());
		}
		return tblAutores;
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

	public void fecharTela() {
		this.dispose();
	}

	private class TrataEventoPesquisar implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if (!StringUtils.isNuloOuBranco(tfCodigo.getText())) {
				if (validarValorCodigo()) {
					boolean numeroInvalido = true;
					for (Autor autor : listaAutores) {
						Integer codigo = Integer.parseInt(tfCodigo.getText());
						if (autor.getCodigo() == codigo) {
							listarAutorCodigo();
							numeroInvalido = false;
						}
					}
					if (numeroInvalido) {
						JOptionPane.showMessageDialog(null, "Digite um código existente", "Atenção", JOptionPane.WARNING_MESSAGE);
					}
				}
			} else if (!StringUtils.isNuloOuBranco(tfNome.getText())) {
				listarAutoresNome();
			} else {
				addAutores();
			}
		}

		private boolean validarValorCodigo() {
			try {
				Integer.parseInt(tfCodigo.getText());
				return true;
			} catch (NumberFormatException e) {
				JOptionPane.showMessageDialog(null, "Insira um valor válido", "Atenção", JOptionPane.WARNING_MESSAGE);
				return false;
			}
		}

		private void listarAutoresNome() {
			String nomeAutor = tfNome.getText().toString();
			try {
				AutorDAO autorDAO = new AutorDAO();
				List<Autor> autores = autorDAO.listarAutoresPeloNome(nomeAutor);
				if (!autores.isEmpty()) {
					getModel().limpar();
					getModel().addListaDeAutores(autores);
				} else {
					JOptionPane.showMessageDialog(null, "Nenhum resultado encontrado", "Atenção", JOptionPane.WARNING_MESSAGE);
				}
			} catch (DAOException e) {
				JOptionPane.showMessageDialog(null, "Sua requisição não foi processada.", "Erro", JOptionPane.ERROR_MESSAGE); 
				e.printStackTrace(logErro);
				logErro.flush();
			}
		}

		private void listarAutorCodigo() {
			int codigoAutor = Integer.parseInt(tfCodigo.getText().toString());
			try {
				AutorDAO autorDAO = new AutorDAO();
				Autor autor = autorDAO.listarAutorPeloCodigo(codigoAutor);
				getModel().limpar();
				getModel().addAutor(autor);
			} catch (DAOException e) {
				JOptionPane.showMessageDialog(null, "Sua requisição não foi processada.", "Erro", JOptionPane.ERROR_MESSAGE); 
				e.printStackTrace(logErro);
				logErro.flush();
			}
		}
	}

	private class TrataEventoEditar implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			int selectedRow = getTblAutores().getSelectedRow();
			editaAutor(selectedRow);
		}

		private void editaAutor(int selectedRow) {
			try {
				int codigoAutor = Integer.parseInt(getTblAutores().getValueAt(selectedRow, 0).toString());
				AutorDAO autorDAO = new AutorDAO();
				try {
					Autor autor = autorDAO.listarAutorPeloCodigo(codigoAutor);
					EditaAutor editaAutor = new EditaAutor(autor);
					editaAutor.setVisible(true);
				} catch (DAOException e) {
					JOptionPane.showMessageDialog(null, "Sua requisição não foi processada.", "Erro", JOptionPane.ERROR_MESSAGE); 
					e.printStackTrace(logErro);
					logErro.flush();
				}
			} catch (IndexOutOfBoundsException e1) {
				JOptionPane.showMessageDialog(null, "Para editar selecione um autor", "Atenção", JOptionPane.WARNING_MESSAGE);
			}
		}
	}

	private class TrataEventoExcluir implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			int selectedRow = getTblAutores().getSelectedRow();
			deletarAutor(selectedRow);
		}

		private void deletarAutor(int selectedRow) {
			try {
				int codigoAutor = Integer.parseInt(getTblAutores().getValueAt(selectedRow, 0).toString());
				String nomeAutor = getTblAutores().getValueAt(selectedRow, 1).toString();
				AutorDAO autorDAO = new AutorDAO();
				try {
					int dialogButton = JOptionPane.showConfirmDialog(null, "Deseja excluir " + nomeAutor + "?", "Atenção",
						JOptionPane.YES_NO_OPTION);
					if (dialogButton == JOptionPane.YES_OPTION) {
						autorDAO.deletarAutor(new Autor(codigoAutor, null, null));
						JOptionPane.showMessageDialog(null, "Autor deletado com sucesso.", "Sucesso!", JOptionPane.INFORMATION_MESSAGE); 
						getModel().limpar();
						configuraTable();
					} else if (dialogButton == JOptionPane.NO_OPTION) {
						JOptionPane.showMessageDialog(null, "Operação cancelada.");
					}
				} catch (DAOException e) {
					JOptionPane.showMessageDialog(null, "Sua requisição não foi processada.", "Erro", JOptionPane.ERROR_MESSAGE); 
					e.printStackTrace(logErro);
					logErro.flush();
				}
			} catch (IndexOutOfBoundsException e) {
				JOptionPane.showMessageDialog(null, "Para remover selecione um autor", "Atenção", JOptionPane.WARNING_MESSAGE);
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
		ListagemAutores listagemAutores = new ListagemAutores();
		listagemAutores.setVisible(true);
	}
}
