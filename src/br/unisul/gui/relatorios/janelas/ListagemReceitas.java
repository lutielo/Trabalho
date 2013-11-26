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

import br.unisul.dados.Receita;
import br.unisul.dados.ReceitaIngrediente;
import br.unisul.dao.DAOException;
import br.unisul.dao.ReceitaDAO;
import br.unisul.gui.alteracoes.EditaReceita;
import br.unisul.gui.relatorios.tablemodels.CellRenderer;
import br.unisul.gui.relatorios.tablemodels.ReceitaTableModel;
import br.unisul.util.StringUtils;

public class ListagemReceitas extends JFrame {

	private static final long serialVersionUID = 1L;
	
	private PrintWriter logErro;
	private List<Receita> listaReceitas;
	private ReceitaTableModel rtm;
	private JTable tblReceitas;
	private JTextField tfCodigo;
	private JTextField tfNome;
	private JScrollPane spListagemReceitas;
	private JButton btnExcluir;
	private JButton btnEditar;
	private JButton btnCancelar;
	private JButton btnPesquisar;
	private JLabel lblCodigo;
	private JLabel lblNome;
	private JLabel lblListagemReceitas;
	private JLabel lblPesquisa;

	public ListagemReceitas() {
		super("Listagem Receitas");
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
		spListagemReceitas = new JScrollPane(getTblReceitas());
		spListagemReceitas.setBounds(30, 136, 481, 328);

		btnExcluir = new JButton("Excluir");
		btnExcluir.setBounds(237, 475, 89, 23);
		TrataEventoExcluir trataEventoExcluir = new TrataEventoExcluir();
		btnExcluir.addActionListener(trataEventoExcluir);

		btnEditar = new JButton("Editar");
		btnEditar.setBounds(138, 475, 89, 23);
		TrataEventoEditar trataEventoEditar = new TrataEventoEditar();
		btnEditar.addActionListener(trataEventoEditar);

		btnCancelar = new JButton("Cancelar");
		btnCancelar.setBounds(336, 475, 89, 23);
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
		tfNome.setBounds(178, 104, 187, 20);
		tfNome.setColumns(10);

		btnPesquisar = new JButton("Pesquisar");
		btnPesquisar.setBounds(392, 102, 102, 23);
		TrataEventoPesquisar trataEventoPesquisar = new TrataEventoPesquisar();
		btnPesquisar.addActionListener(trataEventoPesquisar);

		lblListagemReceitas = new JLabel("Listagem de  Receitas");
		lblListagemReceitas.setBounds(182, 22, 192, 29);
		lblListagemReceitas.setFont(new Font("Tahoma", Font.PLAIN, 19));

		lblPesquisa = new JLabel("Para uma pesquisa mais avan\u00E7adas, digite um filtro:");
		lblPesquisa.setBounds(30, 73, 327, 14);

		getContentPane().add(spListagemReceitas);
		getContentPane().add(btnExcluir);
		getContentPane().add(btnEditar);
		getContentPane().add(btnCancelar);
		getContentPane().add(lblCodigo);
		getContentPane().add(tfCodigo);
		getContentPane().add(lblNome);
		getContentPane().add(tfNome);
		getContentPane().add(btnPesquisar);
		getContentPane().add(lblListagemReceitas);
		getContentPane().add(lblPesquisa);

		configuraTable();
	}

	private void configuraTable() {
		TableColumn col0 = getTblReceitas().getColumnModel().getColumn(0);
		col0.setPreferredWidth(70);

		TableColumn col1 = getTblReceitas().getColumnModel().getColumn(1);
		col1.setPreferredWidth(300);

		TableColumn col2 = getTblReceitas().getColumnModel().getColumn(2);
		col2.setPreferredWidth(200);

		TableColumn col3 = getTblReceitas().getColumnModel().getColumn(3);
		col3.setPreferredWidth(150);

		getTblReceitas().setDefaultRenderer(Object.class, new CellRenderer());

		getTblReceitas().setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		getTblReceitas().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		this.addReceitas();
	}

	private void addReceitas() {
		getModel().limpar();
		getModel().addListaDeReceitas(getReceitas());
	}

	private ReceitaTableModel getModel() {
		if (rtm == null) {
			rtm = (ReceitaTableModel) getTblReceitas().getModel();
		}
		return rtm;
	}

	private JTable getTblReceitas() {
		if (tblReceitas == null) {
			tblReceitas = new JTable();
			tblReceitas.setModel(new ReceitaTableModel());
		}
		return tblReceitas;
	}

	private List<Receita> getReceitas() {
		ReceitaDAO receitaDAO = new ReceitaDAO();
		listaReceitas = new ArrayList<>();
		try {
			listaReceitas = receitaDAO.listeTodasReceitas();
		} catch (DAOException e) {
			e.printStackTrace();
		}
		return listaReceitas;
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
					for (Receita receita : listaReceitas) {
						Integer codigo = Integer.parseInt(tfCodigo.getText());
						if (receita.getCodigo() == codigo) {
							listarReceitaCodigo();
							numeroInvalido = false;
						}
					}
					if (numeroInvalido) {
						JOptionPane.showMessageDialog(null, "Digite um código existente", "Atenção", JOptionPane.WARNING_MESSAGE);
					}
				}
			} else if (!StringUtils.isNuloOuBranco(tfNome.getText())) {
				listarReceitasNome();
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

		private void listarReceitasNome() {
			String nomeReceita = tfNome.getText().toString();
			try {
				ReceitaDAO receitaDAO = new ReceitaDAO();
				List<Receita> receitas = receitaDAO.listeTodasReceitasPeloNome(nomeReceita);
				if (!receitas.isEmpty()) {
					getModel().limpar();
					getModel().addListaDeReceitas(receitas);
				} else {
					JOptionPane.showMessageDialog(null, "Nenhum resultado encontrado");
				}
			} catch (DAOException e) {
				JOptionPane.showMessageDialog(null, "Sua requisição não foi processada.", "Erro", JOptionPane.ERROR_MESSAGE); 
				e.printStackTrace(logErro);
				logErro.flush();
			}
		}

		private void listarReceitaCodigo() {
			int codigoReceita = Integer.parseInt(tfCodigo.getText().toString());
			try {
				ReceitaDAO receitaDAO = new ReceitaDAO();
				Receita receita = receitaDAO.listarDadosDaReceitaPeloCodigo(codigoReceita);
				getModel().limpar();
				getModel().addReceita(receita);
			} catch (DAOException e) {
				JOptionPane.showMessageDialog(null, "Sua requisição não foi processada.", "Erro", JOptionPane.ERROR_MESSAGE); 
				e.printStackTrace(logErro);
				logErro.flush();
			}
		}
	}

	private class TrataEventoEditar implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			int selectedRow = getTblReceitas().getSelectedRow();
			editaReceita(selectedRow);
		}

		private void editaReceita(int selectedRow) {
			try {
				int codigoReceita = Integer.parseInt(getTblReceitas().getValueAt(selectedRow, 0).toString());
				ReceitaDAO receitaDAO = new ReceitaDAO();
				try {
					Receita receita = receitaDAO.listarDadosDaReceitaPeloCodigo(codigoReceita);
					ReceitaIngrediente receitaIngrediente = new ReceitaIngrediente(receita, null, null, null);
					EditaReceita editaReceita = new EditaReceita(receitaIngrediente);
					editaReceita.setVisible(true);
				} catch (DAOException e) {
					JOptionPane.showMessageDialog(null, "Sua requisição não foi processada.", "Erro", JOptionPane.ERROR_MESSAGE); 
					e.printStackTrace(logErro);
					logErro.flush();
				}
			} catch (IndexOutOfBoundsException e1) {
				JOptionPane.showMessageDialog(null, "Para editar selecione uma receita", "Erro", JOptionPane.ERROR_MESSAGE); 
			}
		}
	}

	private class TrataEventoExcluir implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			int selectedRow = getTblReceitas().getSelectedRow();
			deletarReceita(selectedRow);
		}

		private void deletarReceita(int selectedRow) {
			try {
				int codigoReceita = Integer.parseInt(getTblReceitas().getValueAt(selectedRow, 0).toString());
				String nomeReceita = getTblReceitas().getValueAt(selectedRow, 1).toString();
				ReceitaDAO receitaDAO = new ReceitaDAO();
				try {
					int dialogButton = JOptionPane.showConfirmDialog(null, "Deseja excluir " + nomeReceita + "?", "Atenção",
						JOptionPane.YES_NO_OPTION);
					if (dialogButton == JOptionPane.YES_OPTION) {
						receitaDAO.deletarReceita(new Receita(codigoReceita, null, null, null, null));
						JOptionPane.showMessageDialog(null, "Receita deletada com sucesso.", "Sucesso!", JOptionPane.INFORMATION_MESSAGE);  
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
				JOptionPane.showMessageDialog(null, "Para remover selecione uma receita", "Atenção", JOptionPane.WARNING_MESSAGE);
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
		ListagemReceitas listagemReceitas = new ListagemReceitas();
		listagemReceitas.setVisible(true);
	}
}
