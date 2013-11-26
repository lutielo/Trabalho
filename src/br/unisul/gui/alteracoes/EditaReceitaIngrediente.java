package br.unisul.gui.alteracoes;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import br.unisul.dados.ReceitaIngrediente;
import br.unisul.dados.Unidade;
import br.unisul.dao.DAOException;
import br.unisul.dao.ReceitaIngredienteDAO;
import br.unisul.dao.UnidadeDAO;
import br.unisul.util.IndexedFocusTraversalPolicy;

public class EditaReceitaIngrediente extends JFrame {

	private static final long serialVersionUID = 1L;
	
	private PrintWriter logErro;
	private JTextField tfNomeIngrediente;
	private JTextField tfQuantidade;
	private JLabel lblAlteracaoDeIngredientes;
	private JLabel lblNome;
	private JLabel lblCamposObrigatrios;
	private JButton btnSalvar;
	private JButton btnCancelar;
	private JComboBox<String> cbUnidade;
	private JLabel lblUnidade;
	private JLabel lblQuantidade;

	public EditaReceitaIngrediente(ReceitaIngrediente receitaIngrediente) {
		super("Altera\u00E7\u00E3o Ingrediente");
		this.setResizable(false);
		this.setType(Type.UTILITY);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setSize(400, 300);
		this.setLocationRelativeTo(null);
		getContentPane().setLayout(null);
		
		try {
			logErro = new PrintWriter(new FileOutputStream(new File("C:\\temp\\logAplicacaoTrabalhoProg2.txt"), true));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		this.abreTela(receitaIngrediente);
	}

	private void abreTela(ReceitaIngrediente receitaIngrediente) {
		lblAlteracaoDeIngredientes = new JLabel("Altera\u00E7\u00E3o de Ingrediente");
		lblAlteracaoDeIngredientes.setFont(new Font("Tahoma", Font.PLAIN, 19));
		lblAlteracaoDeIngredientes.setBounds(83, 11, 221, 31);

		lblNome = new JLabel("Nome*:");
		lblNome.setBounds(20, 65, 42, 14);

		tfNomeIngrediente = new JTextField();
		tfNomeIngrediente.setEditable(false);
		tfNomeIngrediente.setToolTipText("Ex: Arroz");
		tfNomeIngrediente.setBounds(78, 62, 151, 20);
		tfNomeIngrediente.setColumns(10);
		tfNomeIngrediente.setText(receitaIngrediente.getIngrediente().getNome());

		lblCamposObrigatrios = new JLabel("* campos obrigat\u00F3rios");
		lblCamposObrigatrios.setBounds(10, 251, 128, 14);

		cbUnidade = new JComboBox<String>();
		cbUnidade.setBounds(77, 149, 167, 20);
		prencherComboBoxUnidade(cbUnidade, receitaIngrediente);

		btnSalvar = new JButton("Salvar");
		TrataEventoSalvar trataEventoSalvar = new TrataEventoSalvar(receitaIngrediente);
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
		tfQuantidade.setText(receitaIngrediente.getQuantidade().toString());

		lblQuantidade = new JLabel("Quantidade*:");
		lblQuantidade.setBounds(20, 109, 73, 14);

		getContentPane().add(lblAlteracaoDeIngredientes);
		getContentPane().add(lblNome);
		getContentPane().add(lblCamposObrigatrios);
		getContentPane().add(lblUnidade);
		getContentPane().add(lblQuantidade);
		getContentPane().add(tfQuantidade);
		getContentPane().add(cbUnidade);
		getContentPane().add(tfNomeIngrediente);
		getContentPane().add(btnSalvar);
		getContentPane().add(btnCancelar);

		this.tabOrder();
	}

	private void tabOrder() {
		IndexedFocusTraversalPolicy policy = new IndexedFocusTraversalPolicy();
		policy.addIndexedComponent(tfNomeIngrediente);
		policy.addIndexedComponent(btnSalvar);
		policy.addIndexedComponent(btnCancelar);
		setFocusTraversalPolicy(policy);
	}

	public void prencherComboBoxUnidade(JComboBox<String> comboBox, ReceitaIngrediente receitaIngrediente) {
		UnidadeDAO unidadeDAO = new UnidadeDAO();
		try {
			List<Unidade> listaUnidades = unidadeDAO.listeTodasUnidades();
			int i = 0;
			for (Unidade unidade : listaUnidades) {
				comboBox.addItem(unidade.getTipo());
				if (unidade.getCodigo() == receitaIngrediente.getUnidade().getCodigo()) {
					comboBox.setSelectedIndex(i);
				}
				i++;
			}
		} catch (DAOException e) {
			e.printStackTrace();
		}
	}

	public void fecharTela() {
		this.dispose();
	}

	private class TrataEventoSalvar implements ActionListener {

		ReceitaIngrediente receitaIngredienteOLD = null;
		ReceitaIngrediente receitaIngredienteNEW = new ReceitaIngrediente();

		public TrataEventoSalvar(ReceitaIngrediente receita_IngredienteOLD) {
			super();
			this.receitaIngredienteOLD = receita_IngredienteOLD;
		}

		@Override
		public void actionPerformed(ActionEvent event) {

			ReceitaIngredienteDAO receitaIngredienteDAO = null;
			UnidadeDAO unidadeDAO = null;
			try {
				receitaIngredienteDAO = new ReceitaIngredienteDAO();
				unidadeDAO = new UnidadeDAO();
				List<Unidade> listaUnidades;
				listaUnidades = unidadeDAO.listeTodasUnidades();
				Unidade unidade = (Unidade) listaUnidades.get(cbUnidade.getSelectedIndex());
				receitaIngredienteNEW.setUnidade(unidade);
				receitaIngredienteNEW.setQuantidade(Double.parseDouble(tfQuantidade.getText()));
				receitaIngredienteDAO.alterarIngredienteNaReceita(receitaIngredienteNEW, receitaIngredienteOLD);
				JOptionPane.showMessageDialog(null, "Ingrediente " + tfNomeIngrediente.getText() + " editado com sucesso.", "Sucesso!", JOptionPane.INFORMATION_MESSAGE);  
				fecharTela();
			} catch (DAOException e) {
				JOptionPane.showMessageDialog(null, "Sua requisição não foi processada.", "Erro", JOptionPane.ERROR_MESSAGE); 
				e.printStackTrace(logErro);
				logErro.flush();
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
