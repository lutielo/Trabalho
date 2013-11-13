package br.unisul.gui.alteracoes;

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

public class EditaReceita extends JFrame {

	private static final long serialVersionUID = 1L;

	private JLabel lblAlteracaoDeReceita;
	private JLabel lblNomeDaReceita;
	private JLabel lblNomeDoAutor;
	private JLabel lblModoDePreparo;
	private JLabel lblReceita;
	private JLabel lblcamposObrigatrios;
	private JTextField tfNomeReceita;
	private JTextArea taModoPreparo;
	private JTextArea taResumoReceita;
	private JSeparator jsSeparator;
	private JButton btnEditarIngrediente;
	private JButton btnSalvar;
	private JButton btnCancelar;
	private JComboBox<String> cbAutor;
	private List<Receita_Ingrediente> listaIngredientesAdicionados;
	private JScrollPane spResumoDaReceita;
	private JScrollPane spModoDePreparo;

	public EditaReceita(Receita_Ingrediente receita_Ingrediente) {
		super("Edita Receita");
		this.setResizable(false);
		this.setType(Type.UTILITY);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setSize(800, 508);
		this.setLocationRelativeTo(null);
		getContentPane().setLayout(null);

		this.abreTela(receita_Ingrediente);
	}

	private void abreTela(Receita_Ingrediente receita_Ingrediente) {

		lblAlteracaoDeReceita = new JLabel("Altera\u00E7\u00E3o de Receita");
		lblAlteracaoDeReceita.setFont(new Font("Tahoma", Font.PLAIN, 19));
		lblAlteracaoDeReceita.setBounds(103, 11, 190, 29);

		lblNomeDaReceita = new JLabel("Nome*:");
		lblNomeDaReceita.setBounds(21, 49, 45, 14);

		lblNomeDoAutor = new JLabel("Autor*:");
		lblNomeDoAutor.setBounds(21, 77, 45, 14);

		tfNomeReceita = new JTextField();
		tfNomeReceita.setToolTipText("Ex: Bolo de Chocolate");
		tfNomeReceita.setBounds(65, 46, 257, 20);
		tfNomeReceita.setColumns(10);
		tfNomeReceita.setText(receita_Ingrediente.getReceita().getNome());

		lblModoDePreparo = new JLabel("Modo de preparo*:");
		lblModoDePreparo.setBounds(21, 272, 115, 14);

		cbAutor = new JComboBox<String>();
		cbAutor.setBounds(65, 74, 257, 20);
		prencherComboBoxAutor(cbAutor, receita_Ingrediente.getReceita().getAutor());

		listaIngredientesAdicionados = new ArrayList<Receita_Ingrediente>();

		btnEditarIngrediente = new JButton("<html><center>Editar<br>Ingredientes</center></html>");
		btnEditarIngrediente.setBounds(103, 159, 162, 49);
		// TrataEventoSalvarIngrediente trataEventoSalvarIngrediente = new TrataEventoSalvarIngrediente();
		// btnEditarIngrediente.addActionListener(trataEventoSalvarIngrediente);

		jsSeparator = new JSeparator();
		jsSeparator.setOrientation(SwingConstants.VERTICAL);
		jsSeparator.setBounds(394, 11, 10, 454);

		lblReceita = new JLabel("Resumo da receita :");
		lblReceita.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblReceita.setBounds(414, 17, 197, 20);

		btnSalvar = new JButton("Salvar");
		btnSalvar.setBounds(88, 442, 89, 23);
		TrataEventoSalvarReceita trataEventoSalvarReceita = new TrataEventoSalvarReceita();
		btnSalvar.addActionListener(trataEventoSalvarReceita);

		btnCancelar = new JButton("Cancelar");
		btnCancelar.setBounds(204, 442, 89, 23);
		TrataEventoCancelar trataEventoCancelar = new TrataEventoCancelar();
		btnCancelar.addActionListener(trataEventoCancelar);

		spResumoDaReceita = new JScrollPane();
		spResumoDaReceita.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		spResumoDaReceita.setBounds(414, 46, 370, 404);

		taResumoReceita = new JTextArea();
		taResumoReceita.setWrapStyleWord(true);
		taResumoReceita.setLineWrap(true);
		taResumoReceita.setEditable(false);
		populaResumoReceita(receita_Ingrediente);
		spResumoDaReceita.setViewportView(taResumoReceita);

		spModoDePreparo = new JScrollPane();
		spModoDePreparo.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		spModoDePreparo.setBounds(21, 308, 350, 123);

		taModoPreparo = new JTextArea();
		spModoDePreparo.setViewportView(taModoPreparo);
		taModoPreparo.setWrapStyleWord(true);
		taModoPreparo.setLineWrap(true);
		taModoPreparo.setText(receita_Ingrediente.getReceita().getModo_preparo());

		lblcamposObrigatrios = new JLabel("*campos obrigat\u00F3rios");
		lblcamposObrigatrios.setBounds(414, 459, 135, 14);

		getContentPane().add(lblAlteracaoDeReceita);
		getContentPane().add(lblNomeDaReceita);
		getContentPane().add(lblNomeDoAutor);
		getContentPane().add(lblModoDePreparo);
		getContentPane().add(tfNomeReceita);
		getContentPane().add(cbAutor);
		getContentPane().add(btnSalvar);
		getContentPane().add(btnCancelar);
		getContentPane().add(btnEditarIngrediente);
		getContentPane().add(spModoDePreparo);
		getContentPane().add(jsSeparator);
		getContentPane().add(lblReceita);
		getContentPane().add(spResumoDaReceita);
		getContentPane().add(lblcamposObrigatrios);

	}

	private void populaResumoReceita(Receita_Ingrediente receita_Ingrediente) {
		taResumoReceita.append("Nome da receita : " + tfNomeReceita.getText());
		taResumoReceita.append("\nNome do autor : " + cbAutor.getSelectedItem().toString());
		taResumoReceita.append("\n\nIngredientes : ");
		Receita_IngredienteDAO receita_IngredienteDAO = new Receita_IngredienteDAO();
		try {
			List<Receita_Ingrediente> listaIngredientes = receita_IngredienteDAO.listarIngredientesDaReceita(receita_Ingrediente);
			for (Receita_Ingrediente i : listaIngredientes) {
				taResumoReceita.append("\n" + i.getQuantidade() + " " + i.getUnidade().getTipo() + " de " + i.getIngredientes().getNome());
			}
		} catch (DAOException e) {
			e.printStackTrace();
		}
		taResumoReceita.append("\n\nModo de preparo : " + receita_Ingrediente.getReceita().getModo_preparo());
	}

	public void fecharTela() {
		this.dispose();
	}

	public void prencherComboBoxAutor(JComboBox<String> comboBox, Autor autor) {
		AutorDAO autorDAO = new AutorDAO();
		try {
			List<Autor> listaAutores = autorDAO.listarTodosAutores();
			int i = 0;
			for (Autor a : listaAutores) {
				comboBox.addItem(a.getNome());
				if (autor.getCodigo() == a.getCodigo()) {
					comboBox.setSelectedIndex(i);
				}
				i++;
			}
		} catch (DAOException e) {
			e.printStackTrace();
		}
	}

	public void prencherComboBoxIngrediente(JComboBox<String> comboBox) {
		IngredienteDAO ingredienteDAO = new IngredienteDAO();
		try {
			List<Ingrediente> listaIngredientes = ingredienteDAO.listeTodosIngredientes();
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
			for (Unidade unidade : listaUnidades) {
				comboBox.addItem(unidade.getTipo());
			}
		} catch (DAOException e) {
			e.printStackTrace();
		}
	}
	
	private class TrataEventoEditaIngredientes implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			
		}
	}

	private class TrataEventoSalvarReceita implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if (!StringUtils.isNuloOuBranco(tfNomeReceita.getText())) {
				Autor autor = null;
				autor = resgatarAutorSelecionado();
				if (!StringUtils.isNuloOuBranco(taModoPreparo.getText())) {
					this.cadastrarReceita(autor);
					this.cadatrarIngredientesNaReceita();
				} else {
					JOptionPane.showMessageDialog(null, "Digite o modo de preparo.");
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
}
