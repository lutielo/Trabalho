package br.unisul.gui.cadastros;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import br.unisul.dados.Ingrediente;
import br.unisul.dados.ReceitaIngrediente;
import br.unisul.dados.Unidade;
import br.unisul.dao.DAOException;
import br.unisul.dao.IngredienteDAO;
import br.unisul.dao.ReceitaIngredienteDAO;
import br.unisul.dao.UnidadeDAO;
import br.unisul.util.IndexedFocusTraversalPolicy;
import br.unisul.util.StringUtils;

public class CadastraNovoIngredienteReceita extends JFrame {

	private static final long serialVersionUID = 1L;
	private JTextField tfQuantidade;
	private JLabel lblAlteracaoDeIngredientes;
	private JLabel lblIngrediente;
	private JLabel lblCamposObrigatrios;
	private JButton btnSalvar;
	private JButton btnCancelar;
	private JComboBox<String> cbIngrediente;
	private JComboBox<String> cbUnidade;
	private JLabel lblUnidade;
	private JLabel lblQuantidade;

	public CadastraNovoIngredienteReceita(ReceitaIngrediente receitaIngrediente) {
		super("Cadastro de Ingrediente na receita " + "NOME DA RECEITA");
		this.setResizable(false);
		this.setType(Type.UTILITY);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setSize(400, 300);
		this.setLocationRelativeTo(null);
		getContentPane().setLayout(null);

		this.abreTela(receitaIngrediente);
	}

	private void abreTela(ReceitaIngrediente receitaIngrediente) {
		lblAlteracaoDeIngredientes = new JLabel("Cadastro de Ingrediente na receita " + "NOME DA RECEITA");
		lblAlteracaoDeIngredientes.setFont(new Font("Tahoma", Font.PLAIN, 19));
		lblAlteracaoDeIngredientes.setBounds(83, 11, 221, 31);

		lblIngrediente = new JLabel("Ingrediente*:");
		lblIngrediente.setBounds(20, 66, 89, 14);

		lblCamposObrigatrios = new JLabel("* campos obrigat\u00F3rios");
		lblCamposObrigatrios.setBounds(10, 251, 128, 14);
		
		cbIngrediente = new JComboBox<String>();
		cbIngrediente.setBounds(105, 63, 183, 20);
		prencherComboBoxIngrediente(cbIngrediente);

		cbUnidade = new JComboBox<String>();
		cbUnidade.setBounds(77, 149, 193, 20);
		prencherComboBoxUnidade(cbUnidade);

		btnSalvar = new JButton("Salvar");
		TrataEventoSalvar trataEventoSalvar = new TrataEventoSalvar();
		btnSalvar.addActionListener(trataEventoSalvar);
		btnSalvar.setBounds(76, 195, 89, 23);

		btnCancelar = new JButton("Cancelar");
		btnCancelar.setBounds(204, 195, 89, 23);
		TrataEventoCancelar trataEventoCancelar = new TrataEventoCancelar();
		btnCancelar.addActionListener(trataEventoCancelar);

		lblUnidade = new JLabel("Unidade*:");
		lblUnidade.setBounds(20, 152, 59, 14);

		tfQuantidade = new JTextField();
		tfQuantidade.setColumns(10);
		tfQuantidade.setBounds(99, 106, 66, 20);

		lblQuantidade = new JLabel("Quantidade*:");
		lblQuantidade.setBounds(20, 109, 73, 14);

		getContentPane().add(lblAlteracaoDeIngredientes);
		getContentPane().add(lblIngrediente);
		getContentPane().add(lblCamposObrigatrios);
		getContentPane().add(lblUnidade);
		getContentPane().add(lblQuantidade);
		getContentPane().add(tfQuantidade);
		getContentPane().add(cbIngrediente);
		getContentPane().add(cbUnidade);
		getContentPane().add(btnSalvar);
		getContentPane().add(btnCancelar);

		this.tabOrder();
	}

	private void tabOrder() {
		IndexedFocusTraversalPolicy policy = new IndexedFocusTraversalPolicy();
		policy.addIndexedComponent(btnSalvar);
		policy.addIndexedComponent(btnCancelar);
		setFocusTraversalPolicy(policy);
	}
	
	public void prencherComboBoxIngrediente(JComboBox<String> comboBox) {
		IngredienteDAO ingredienteDAO = new IngredienteDAO();
		try {
			List<Ingrediente> listaIngredientes = ingredienteDAO.listeTodosIngredientes();
			comboBox.addItem(" -- Selecione -- ");
			for (Ingrediente ingrediente : listaIngredientes) {
				comboBox.addItem(ingrediente.getNome());
			}
		} catch (DAOException e) {
			e.printStackTrace();
		}
	}
	
	public void prencherComboBoxUnidade(JComboBox<String> comboBox) {
		UnidadeDAO unidadeDAO = new UnidadeDAO();
		try {
			List<Unidade> listaUnidades = unidadeDAO.listeTodasUnidades();
			comboBox.addItem(" -- Selecione -- ");
			for (Unidade unidade : listaUnidades) {
				comboBox.addItem(unidade.getTipo());
			}
		} catch (DAOException e) {
			e.printStackTrace();
		}
	}

	public void fecharTela() {
		this.dispose();
	}

	private class TrataEventoSalvar implements ActionListener {
			
			
		
			@Override
			public void actionPerformed(ActionEvent e) {
				if (!cbIngrediente.getSelectedItem().toString().contains(" -- Selecione -- ")) {
					if (!cbUnidade.getSelectedItem().toString().contains(" -- Selecione -- ")) {
						cadastrarIngredienteNaReceita();
					} else {
						JOptionPane.showMessageDialog(null, "Selecione uma unidade");
					}
				} else {
					JOptionPane.showMessageDialog(null, "Selecione um ingrediente");
				}
			}

			private void cadastrarIngredienteNaReceita() {
				try {
					IngredienteDAO ingredienteDAO = new IngredienteDAO();
					UnidadeDAO unidadeDAO = new UnidadeDAO();
					List<Unidade> listaUnidades;
					List<Ingrediente> listaIngredientes;
					listaUnidades = unidadeDAO.listeTodasUnidades();
					Unidade unidade = (Unidade) listaUnidades.get(cbUnidade.getSelectedIndex() - 1);
					listaIngredientes = ingredienteDAO.listeTodosIngredientes();
					Ingrediente ingrediente = (Ingrediente) listaIngredientes.get(cbIngrediente.getSelectedIndex() - 1);
					if (!StringUtils.isNuloOuBranco(tfQuantidade.getText())) {
						Double quantidade = null;
						if (validarValorQuantidade(quantidade)) {
							cadastraIngrediente(unidade, ingrediente);
						}
					} else {
						JOptionPane.showMessageDialog(null, "Digite a quantidade");
					}
				} catch (DAOException e) {
					e.printStackTrace();
				} catch (ArrayIndexOutOfBoundsException e) {
					JOptionPane.showMessageDialog(null, "Selecione todos os campos obrigatórios.");
				}
			}

			private void cadastraIngrediente(Unidade unidade, Ingrediente ingrediente) {
				Double quantidade;
				quantidade = Double.parseDouble(tfQuantidade.getText());
				new ReceitaIngredienteDAO();
				ReceitaIngrediente receitaIngrediente = new ReceitaIngrediente(null, ingrediente, unidade, quantidade);
				int dialogButton = JOptionPane.showConfirmDialog(null, "Deseja salvar este ingrediente? " + "\nIngrediente \t: "
					+ receitaIngrediente.getIngrediente().getNome() + "\nQuantidade \t: " + receitaIngrediente.getQuantidade() + "\nUnidade \t: "
					+ receitaIngrediente.getUnidade().getTipo(), "Atenção", JOptionPane.YES_NO_OPTION);
				if (dialogButton == JOptionPane.YES_OPTION) {
					JOptionPane.showMessageDialog(null, "Ingrediente adiciona com sucesso.");
				} else if (dialogButton == JOptionPane.NO_OPTION) {
					JOptionPane.showMessageDialog(null, "Ingrediente não adicionado.");
				}
			}

			private boolean validarValorQuantidade(Double quantidade) {
				try {
					quantidade = Double.parseDouble(tfQuantidade.getText());
					return true;
				} catch (NumberFormatException e) {
					JOptionPane.showMessageDialog(null, "Digite um valor de quantidade válida.");
					return false;
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
