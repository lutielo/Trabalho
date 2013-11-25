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
import br.unisul.dados.ReceitaIngrediente;
import br.unisul.dados.Unidade;
import br.unisul.dao.AutorDAO;
import br.unisul.dao.DAOException;
import br.unisul.dao.IngredienteDAO;
import br.unisul.dao.ReceitaDAO;
import br.unisul.dao.ReceitaIngredienteDAO;
import br.unisul.dao.UnidadeDAO;
import br.unisul.gui.relatorios.janelas.ListagemIngredientesDaReceita;
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
	private List<ReceitaIngrediente> listaIngredientesAdicionados;
	private JScrollPane spResumoDaReceita;
	private JScrollPane spModoDePreparo;

	public EditaReceita(ReceitaIngrediente receitaIngrediente) {
		super("Edita Receita");
		this.setResizable(false);
		this.setType(Type.UTILITY);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setSize(800, 508);
		this.setLocationRelativeTo(null);
		getContentPane().setLayout(null);

		this.abreTela(receitaIngrediente);
	}

	private void abreTela(ReceitaIngrediente receitaIngrediente) {

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
		tfNomeReceita.setText(receitaIngrediente.getReceita().getNome());

		lblModoDePreparo = new JLabel("Modo de preparo*:");
		lblModoDePreparo.setBounds(21, 272, 115, 14);

		cbAutor = new JComboBox<String>();
		cbAutor.setBounds(65, 74, 257, 20);
		prencherComboBoxAutor(cbAutor, receitaIngrediente.getReceita().getAutor());

		listaIngredientesAdicionados = new ArrayList<ReceitaIngrediente>();

		btnEditarIngrediente = new JButton("<html><center>Editar<br>Ingredientes</center></html>");
		btnEditarIngrediente.setBounds(103, 159, 162, 49);
		TrataEventoEditarIngredientes trataEventoEditarIngrediente = new TrataEventoEditarIngredientes(receitaIngrediente);
		btnEditarIngrediente.addActionListener(trataEventoEditarIngrediente);

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
		populaResumoReceita(receitaIngrediente);
		spResumoDaReceita.setViewportView(taResumoReceita);

		spModoDePreparo = new JScrollPane();
		spModoDePreparo.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		spModoDePreparo.setBounds(21, 308, 350, 123);

		taModoPreparo = new JTextArea();
		spModoDePreparo.setViewportView(taModoPreparo);
		taModoPreparo.setWrapStyleWord(true);
		taModoPreparo.setLineWrap(true);
		taModoPreparo.setText(receitaIngrediente.getReceita().getModo_preparo());

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

	private void populaResumoReceita(ReceitaIngrediente receitaIngrediente) {
		taResumoReceita.append("Nome da receita : " + tfNomeReceita.getText());
		taResumoReceita.append("\nNome do autor : " + cbAutor.getSelectedItem().toString());
		taResumoReceita.append("\n\nIngredientes : ");
		ReceitaIngredienteDAO receitaIngredienteDAO = new ReceitaIngredienteDAO();
		try {
			List<ReceitaIngrediente> listaIngredientes = receitaIngredienteDAO.listarIngredientesDaReceita(receitaIngrediente);
			for (ReceitaIngrediente i : listaIngredientes) {
				taResumoReceita.append("\n" + i.getQuantidade() + " " + i.getUnidade().getTipo() + " de " + i.getIngrediente().getNome());
			}
		} catch (DAOException e) {
			JOptionPane.showMessageDialog(null, "Sua requisição não foi processada.");
			e.printStackTrace();
		}
		taResumoReceita.append("\n\nModo de preparo : " + receitaIngrediente.getReceita().getModo_preparo());
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

	private class TrataEventoEditarIngredientes implements ActionListener {

		private ReceitaIngrediente receitaIngrediente;

		public TrataEventoEditarIngredientes(ReceitaIngrediente receitaIngrediente) {
			super();
			this.receitaIngrediente = receitaIngrediente;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			ListagemIngredientesDaReceita listagemIngredientesDaReceita = new ListagemIngredientesDaReceita(receitaIngrediente);
			listagemIngredientesDaReceita.setVisible(true);
		}
	}

	private class TrataEventoSalvarReceita implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if (!StringUtils.isNuloOuBranco(tfNomeReceita.getText())) {
				Autor autor = null;
				autor = resgatarAutorSelecionado();
				if (!StringUtils.isNuloOuBranco(taModoPreparo.getText())) {
					this.editarReceita(autor);
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
			} catch (ArrayIndexOutOfBoundsException e) {
				JOptionPane.showMessageDialog(null, "Selecione um autor.");
			} catch (DAOException e) {
				JOptionPane.showMessageDialog(null, "Sua requisição não foi processada.");
				e.printStackTrace();
			}
			return autor;
		}

		private void editarReceita(Autor autor) {
			Receita receita = new Receita(null, tfNomeReceita.getText(), new Date(), taModoPreparo.getText(), autor);
			ReceitaDAO receitaDAO = new ReceitaDAO();
			try {
				receitaDAO.alterarReceita(receita);
				JOptionPane.showMessageDialog(null, "Receita editada com sucesso");
				fecharTela();
			} catch (DAOException e) {
				JOptionPane.showMessageDialog(null, "Sua requisição não foi processada.");
				e.printStackTrace();
			}
		}

		private void cadatrarIngredientesNaReceita() {
			try {
				ReceitaDAO receitaDAO = new ReceitaDAO();
				int codigoReceitaCriada = receitaDAO.resgatarUltimoRegistro();
				ReceitaIngredienteDAO receitaIngredienteDAO = new ReceitaIngredienteDAO();
				for (ReceitaIngrediente receitaIngrediente : listaIngredientesAdicionados) {
					Receita receita = new Receita();
					receita.setCodigo(codigoReceitaCriada);
					receitaIngrediente.setReceita(receita);
					receitaIngredienteDAO.cadastrarIngredienteNaReceita(receitaIngrediente);
				}
			} catch (DAOException e) {
				JOptionPane.showMessageDialog(null, "Sua requisição não foi processada.");
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
