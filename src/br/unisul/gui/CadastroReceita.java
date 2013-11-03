package br.unisul.gui;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JSeparator;
import javax.swing.JTextArea;
import javax.swing.JTextField;
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
	private JTextField tfNomeReceita;
	private JTextField tfQuantidade;
	private JTextArea taModoPreparo;
	private JTextArea taResumoReceita;
	private JSeparator jsSeparator;
	private JButton btnAdicionarIngrediente;
	private JButton btnSalvar;
	private JButton btnCancelar;
	private JComboBox<String> cbAutor;
	private JComboBox<String> cbUnidade;
	private JComboBox<String> cbIngrediente;
	private List<Receita_Ingrediente> listaIngredientesAdicionados;

	CadastroReceita() {
		super("Cadastro Receita");
		this.setResizable(false);
		this.setType(Type.UTILITY);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(800, 500);
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
		VerificaFocoNomeReceita verificaFocoNomeReceita = new VerificaFocoNomeReceita();
		tfNomeReceita.addFocusListener(verificaFocoNomeReceita);

		lblModoDePreparo = new JLabel("Modo de preparo*:");
		lblModoDePreparo.setBounds(21, 272, 115, 14);

		cbAutor = new JComboBox<String>();
		cbAutor.setBounds(65, 74, 257, 20);
		prencherComboBoxAutor(cbAutor);
		VerificaFocoNomeAutor verificaFocoNomeAutor = new VerificaFocoNomeAutor();
		cbAutor.addFocusListener(verificaFocoNomeAutor);

		listaIngredientesAdicionados = new ArrayList<Receita_Ingrediente>();

		btnAdicionarIngrediente = new JButton("<html><center>Adicionar<br>Ingrediente</center></html>");
		btnAdicionarIngrediente.setBounds(209, 172, 162, 49);
		TrataEventoSalvarIngrediente trataEventoSalvarIngrediente = new TrataEventoSalvarIngrediente();
		btnAdicionarIngrediente.addActionListener(trataEventoSalvarIngrediente);

		taModoPreparo = new JTextArea();
		taModoPreparo.setWrapStyleWord(true);
		taModoPreparo.setLineWrap(true);
		taModoPreparo.setBounds(21, 308, 350, 123);

		jsSeparator = new JSeparator();
		jsSeparator.setOrientation(SwingConstants.VERTICAL);
		jsSeparator.setBounds(394, 11, 10, 454);

		lblReceita = new JLabel("Resumo da receita :");
		lblReceita.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblReceita.setBounds(414, 17, 197, 20);

		taResumoReceita = new JTextArea();
		taResumoReceita.setBounds(414, 46, 370, 319);

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
		btnSalvar.setBounds(65, 442, 89, 23);
		TrataEventoSalvarReceita trataEventoSalvarReceita = new TrataEventoSalvarReceita();
		btnSalvar.addActionListener(trataEventoSalvarReceita);

		btnCancelar = new JButton("Cancelar");
		btnCancelar.setBounds(183, 442, 89, 23);
		TrataEventoCancelar trataEventoCancelar = new TrataEventoCancelar();
		btnCancelar.addActionListener(trataEventoCancelar);

		getContentPane().add(lblCadastro);
		getContentPane().add(lblNomeDaReceita);
		getContentPane().add(lblNomeDoAutor);
		getContentPane().add(tfNomeReceita);
		getContentPane().add(lblModoDePreparo);
		getContentPane().add(cbAutor);
		getContentPane().add(btnSalvar);
		getContentPane().add(btnCancelar);
		getContentPane().add(btnAdicionarIngrediente);
		getContentPane().add(taModoPreparo);
		getContentPane().add(jsSeparator);
		getContentPane().add(lblReceita);
		getContentPane().add(taResumoReceita);
		getContentPane().add(cbIngrediente);
		getContentPane().add(lblIngrediente);
		getContentPane().add(cbUnidade);
		getContentPane().add(lblUnidadeDeMedida);
		getContentPane().add(tfQuantidade);
		getContentPane().add(lblQuantidade);
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
				if (!StringUtils.isNuloOuBranco(tfQuantidade)) {
					Double quantidade = Double.parseDouble(tfQuantidade.getText());
					new Receita_IngredienteDAO();
					Receita_Ingrediente receita_Ingrediente = new Receita_Ingrediente(null, ingrediente, unidade, quantidade);
					listaIngredientesAdicionados.add(receita_Ingrediente);
				} else {
					JOptionPane.showMessageDialog(null, "Digite a quantidade");
				}
			} catch (DAOException e) {
				e.printStackTrace();
			} catch (ArrayIndexOutOfBoundsException e1) {
				JOptionPane.showMessageDialog(null, "Selecione todos os campos obrigatórios.");
			}
		}
	}

	private class VerificaFocoNomeReceita implements FocusListener {

		@Override
		public void focusGained(FocusEvent arg0) {
			// TODO Auto-generated method stub
		}

		@Override
		public void focusLost(FocusEvent e) {
			taResumoReceita.setText("Nome da Receita : " + tfNomeReceita.getText());
		}
	}

	private class VerificaFocoNomeAutor implements FocusListener {

		@Override
		public void focusGained(FocusEvent e) {
			// TODO Auto-generated method stub
		}

		@Override
		public void focusLost(FocusEvent e) {
			String resumoReceita = taResumoReceita.getText() + "\nNome do Autor: " + cbAutor.getSelectedItem().toString();
			taResumoReceita.setText(resumoReceita);
		}
	}

	private class TrataEventoSalvarReceita implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if (!StringUtils.isNuloOuBranco(tfNomeReceita.getText())) {
				Autor autor = null;
				if (!cbAutor.getSelectedItem().toString().contains(" -- Selecione -- ")) {
					autor = validarAutor();
					if (!StringUtils.isNuloOuBranco(taModoPreparo.getText())) {
						this.cadastrarReceita(autor);
					} else {
						JOptionPane.showMessageDialog(null, "Digite o modo de preparo.");
					}
				} else {
					JOptionPane.showMessageDialog(null, "Selecione um Autor");
				}
			} else {
				JOptionPane.showMessageDialog(null, "Digite o nome da receita");
			}
			this.cadatrarIngredienteNaReceita();
		}

		private Autor validarAutor() {
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

		private void cadatrarIngredienteNaReceita() {
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

	public static void main(String[] args) {
		CadastroReceita cadastroReceita = new CadastroReceita();
		cadastroReceita.setVisible(true);
	}
}
