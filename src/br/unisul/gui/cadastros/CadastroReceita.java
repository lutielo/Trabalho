package br.unisul.gui.cadastros;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;

import br.unisul.dados.Autor;
import br.unisul.dados.Ingrediente;
import br.unisul.dados.Receita;
import br.unisul.dados.Receita_Ingrediente;
import br.unisul.dados.Unidade;
import br.unisul.dao.AutorDAO;
import br.unisul.dao.DAOException;
import br.unisul.dao.IngredienteDAO;
import br.unisul.dao.ReceitaDAO;
import br.unisul.dao.Receita_IngredienteDAO;
import br.unisul.dao.UnidadeDAO;
import br.unisul.util.StringUtils;

public class CadastroReceita extends JFrame {

	private static final long serialVersionUID = 1L;

	private JLabel lblCadastro;
	private JLabel lblNomeDaReceita;
	private JLabel lblNomeDoAutor;
	private JLabel lblModoDePreparo;
	private JLabel lblReceita;
	private JLabel lblIngrediente;
	private JLabel lblUnidadeDeMedida;
	private JLabel lblQuantidade;
	private JLabel lblcamposObrigatrios;
	private JTextField tfNomeReceita;
	private JTextField tfQuantidade;
	private JTextArea taModoPreparo;
	private JTextArea taResumoReceita;
	private JSeparator jsSeparator;
	private JButton btnAdicionarIngrediente;
	private JButton btnSalvar;
	private JButton btnCancelar;
	private JButton btnVisualizaReceita;
	private JComboBox<String> cbAutor;
	private JComboBox<String> cbUnidade;
	private JComboBox<String> cbIngrediente;
	private List<Receita_Ingrediente> listaIngredientesAdicionados;
	private JScrollPane spResumoDaReceita;
	private JScrollPane spModoDePreparo;

	public CadastroReceita() {
		super("Cadastro Receita");
		this.setResizable(false);
		this.setType(Type.UTILITY);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setSize(800, 508);
		this.setLocationRelativeTo(null);
		getContentPane().setLayout(null);

		this.abreTela();
	}

	private void abreTela() {

		lblCadastro = new JLabel("Cadastro de Receita");
		lblCadastro.setFont(new Font("Tahoma", Font.PLAIN, 19));
		lblCadastro.setBounds(103, 11, 190, 29);

		lblNomeDaReceita = new JLabel("Nome*:");
		lblNomeDaReceita.setBounds(21, 49, 45, 14);

		lblNomeDoAutor = new JLabel("Autor*:");
		lblNomeDoAutor.setBounds(21, 77, 45, 14);

		tfNomeReceita = new JTextField();
		tfNomeReceita.setToolTipText("Ex: Bolo de Chocolate");
		tfNomeReceita.setBounds(65, 46, 257, 20);
		tfNomeReceita.setColumns(10);

		lblModoDePreparo = new JLabel("Modo de preparo*:");
		lblModoDePreparo.setBounds(21, 272, 115, 14);

		cbAutor = new JComboBox<String>();
		cbAutor.setBounds(65, 74, 257, 20);
		prencherComboBoxAutor(cbAutor);

		listaIngredientesAdicionados = new ArrayList<Receita_Ingrediente>();

		btnAdicionarIngrediente = new JButton("<html><center>Adicionar<br>Ingrediente</center></html>");
		btnAdicionarIngrediente.setBounds(209, 172, 162, 49);
		TrataEventoSalvarIngrediente trataEventoSalvarIngrediente = new TrataEventoSalvarIngrediente();
		btnAdicionarIngrediente.addActionListener(trataEventoSalvarIngrediente);

		jsSeparator = new JSeparator();
		jsSeparator.setOrientation(SwingConstants.VERTICAL);
		jsSeparator.setBounds(394, 11, 10, 454);

		lblReceita = new JLabel("Resumo da receita :");
		lblReceita.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblReceita.setBounds(414, 17, 197, 20);

		cbIngrediente = new JComboBox<String>();
		cbIngrediente.setBounds(21, 141, 167, 20);
		prencherComboBoxIngrediente(cbIngrediente);

		lblIngrediente = new JLabel("Ingrediente*:");
		lblIngrediente.setBounds(21, 127, 79, 14);

		cbUnidade = new JComboBox<String>();
		cbUnidade.setBounds(21, 229, 167, 20);
		prencherComboBoxUnidade(cbUnidade);

		lblUnidadeDeMedida = new JLabel("Unidade*:");
		lblUnidadeDeMedida.setBounds(21, 215, 59, 14);

		tfQuantidade = new JTextField();
		tfQuantidade.setBounds(21, 186, 66, 20);
		tfQuantidade.setColumns(10);

		lblQuantidade = new JLabel("Quantidade*:");
		lblQuantidade.setBounds(21, 172, 73, 14);

		btnSalvar = new JButton("Salvar");
		btnSalvar.setBounds(31, 442, 89, 23);
		TrataEventoSalvarReceita trataEventoSalvarReceita = new TrataEventoSalvarReceita();
		btnSalvar.addActionListener(trataEventoSalvarReceita);

		btnCancelar = new JButton("Cancelar");
		btnCancelar.setBounds(147, 442, 89, 23);
		TrataEventoCancelar trataEventoCancelar = new TrataEventoCancelar();
		btnCancelar.addActionListener(trataEventoCancelar);

		btnVisualizaReceita = new JButton("Visualizar");
		btnVisualizaReceita.setToolTipText("Clique para visualizar a receita");
		btnVisualizaReceita.setBounds(259, 442, 98, 23);
		TrataEventoVisualizar trataEventoVisualizar = new TrataEventoVisualizar();
		btnVisualizaReceita.addActionListener(trataEventoVisualizar);

		spResumoDaReceita = new JScrollPane();
		spResumoDaReceita.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		spResumoDaReceita.setBounds(414, 46, 370, 404);

		taResumoReceita = new JTextArea();
		taResumoReceita.setWrapStyleWord(true);
		taResumoReceita.setLineWrap(true);
		taResumoReceita.setEditable(false);
		spResumoDaReceita.setViewportView(taResumoReceita);

		spModoDePreparo = new JScrollPane();
		spModoDePreparo.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		spModoDePreparo.setBounds(21, 308, 350, 123);

		taModoPreparo = new JTextArea();
		spModoDePreparo.setViewportView(taModoPreparo);
		taModoPreparo.setWrapStyleWord(true);
		taModoPreparo.setLineWrap(true);

		lblcamposObrigatrios = new JLabel("*campos obrigat\u00F3rios");
		lblcamposObrigatrios.setBounds(414, 459, 135, 14);

		getContentPane().add(lblCadastro);
		getContentPane().add(lblNomeDaReceita);
		getContentPane().add(lblNomeDoAutor);
		getContentPane().add(lblModoDePreparo);
		getContentPane().add(tfNomeReceita);
		getContentPane().add(cbAutor);
		getContentPane().add(btnSalvar);
		getContentPane().add(btnCancelar);
		getContentPane().add(btnAdicionarIngrediente);
		getContentPane().add(spModoDePreparo);
		getContentPane().add(jsSeparator);
		getContentPane().add(lblReceita);
		getContentPane().add(spResumoDaReceita);
		getContentPane().add(cbIngrediente);
		getContentPane().add(lblIngrediente);
		getContentPane().add(cbUnidade);
		getContentPane().add(lblUnidadeDeMedida);
		getContentPane().add(tfQuantidade);
		getContentPane().add(lblQuantidade);
		getContentPane().add(btnVisualizaReceita);
		getContentPane().add(lblcamposObrigatrios);
		
		this.tabOrder();

	}
	
	private void tabOrder() {
		
	}

	public void fecharTela() {
		this.dispose();
	}

	public void prencherComboBoxAutor(JComboBox<String> comboBox) {
		AutorDAO autorDAO = new AutorDAO();
		try {
			List<Autor> listaAutores = autorDAO.listarTodosAutores();
			comboBox.addItem(" -- Selecione -- ");
			for (Autor autor : listaAutores) {
				comboBox.addItem(autor.getNome());
			}
		} catch (DAOException e) {
			e.printStackTrace();
		}
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

	private class TrataEventoSalvarIngrediente implements ActionListener {

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
			new Receita_IngredienteDAO();
			Receita_Ingrediente receita_Ingrediente = new Receita_Ingrediente(null, ingrediente, unidade, quantidade);
			int dialogButton = JOptionPane.showConfirmDialog(null, "Deseja salvar este ingrediente? " + "\nIngrediente \t: "
				+ receita_Ingrediente.getIngrediente().getNome() + "\nQuantidade \t: " + receita_Ingrediente.getQuantidade() + "\nUnidade \t: "
				+ receita_Ingrediente.getUnidade().getTipo(), "Atenção", JOptionPane.YES_NO_OPTION);
			if (dialogButton == JOptionPane.YES_OPTION) {
				listaIngredientesAdicionados.add(receita_Ingrediente);
				limparCamposIngrediente();
			} else if (dialogButton == JOptionPane.NO_OPTION) {
				JOptionPane.showMessageDialog(null, "Ingrediente não adicionado.");
			}
		}

		private void limparCamposIngrediente() {
			tfQuantidade.setText("");
			cbIngrediente.setSelectedIndex(0);
			cbUnidade.setSelectedIndex(0);
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

	private class TrataEventoSalvarReceita implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if (!StringUtils.isNuloOuBranco(tfNomeReceita.getText())) {
				Autor autor = null;
				if (!cbAutor.getSelectedItem().toString().contains(" -- Selecione -- ")) {
					autor = resgatarAutorSelecionado();
					if (!StringUtils.isNuloOuBranco(taModoPreparo.getText())) {
						this.cadastrarReceita(autor);
						this.cadatrarIngredientesNaReceita();
					} else {
						JOptionPane.showMessageDialog(null, "Digite o modo de preparo.");
					}
				} else {
					JOptionPane.showMessageDialog(null, "Selecione um Autor");
				}
			} else {
				JOptionPane.showMessageDialog(null, "Digite o nome da receita");
			}
		}

		private Autor resgatarAutorSelecionado() {
			Autor autor = null;
			AutorDAO autorDAO = new AutorDAO();
			List<Autor> listaAutores;
			try {
				listaAutores = autorDAO.listarTodosAutores();
				autor = (Autor) listaAutores.get(cbAutor.getSelectedIndex() - 1);
			} catch (DAOException e) {
				e.printStackTrace();
			} catch (ArrayIndexOutOfBoundsException e) {
				JOptionPane.showMessageDialog(null, "Selecione um autor.");
			}
			return autor;
		}

		private void cadastrarReceita(Autor autor) {
			Receita receita = new Receita(null, tfNomeReceita.getText(), new Date(), taModoPreparo.getText(), autor);
			ReceitaDAO receitaDAO = new ReceitaDAO();
			try {
				receitaDAO.cadastreReceita(receita);
				JOptionPane.showMessageDialog(null, "Receita cadastrada com sucesso");
				fecharTela();
			} catch (DAOException e1) {
				JOptionPane.showMessageDialog(null, "Occoreu um erro ao processar a sua requisição.");
			}
		}

		private void cadatrarIngredientesNaReceita() {
			try {
				ReceitaDAO receitaDAO = new ReceitaDAO();
				int codigoReceitaCriada = receitaDAO.resgatarUltimoRegistro();
				Receita_IngredienteDAO receita_IngredienteDAO = new Receita_IngredienteDAO();
				for (Receita_Ingrediente receita_Ingrediente : listaIngredientesAdicionados) {
					Receita receita = new Receita();
					receita.setCodigo(codigoReceitaCriada);
					receita_Ingrediente.setReceita(receita);
					receita_IngredienteDAO.cadastreIngredienteNaReceita(receita_Ingrediente);
				}
			} catch (DAOException e) {
				e.printStackTrace();
			}
		}
	}

	private class TrataEventoCancelar implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent event) {
			fecharTela();
		}
	}

	private class TrataEventoVisualizar implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			taResumoReceita.setText("");
			taResumoReceita.append("Nome da receita : " + tfNomeReceita.getText());
			taResumoReceita.append("\nNome do autor : " + cbAutor.getSelectedItem().toString());
			taResumoReceita.append("\n\nIngredientes : ");
			for (Receita_Ingrediente i : listaIngredientesAdicionados) {
				taResumoReceita.append("\n" + i.getQuantidade() + " " + i.getUnidade().getTipo() + " de " + i.getIngrediente().getNome());
			}
			taResumoReceita.append("\n\nModo de preparo : " + taModoPreparo.getText());
		}
	}

	public static void main(String[] args) {
		CadastroReceita cadastroReceita = new CadastroReceita();
		cadastroReceita.setVisible(true);
	}
}
