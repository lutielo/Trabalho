package br.unisul.gui.alteracoes;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import br.unisul.dados.Ingrediente;
import br.unisul.dao.DAOException;
import br.unisul.dao.IngredienteDAO;
import br.unisul.util.IndexedFocusTraversalPolicy;
import br.unisul.util.StringUtils;

public class EditaIngrediente extends JFrame {

	private static final long serialVersionUID = 1L;

	private JTextField tfCodigo;
	private JTextField tfNomeIngrediente;
	private JLabel lblAlteracaoDeIngredientes;
	private JLabel lblCodigo;
	private JLabel lblNome;
	private JLabel lblCamposObrigatrios;
	private JButton btnSalvar;
	private JButton btnCancelar;

	public EditaIngrediente(Ingrediente ingrediente) {
		super("Altera\u00E7\u00E3o Ingrediente");
		this.setResizable(false);
		this.setType(Type.UTILITY);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setSize(400, 300);
		this.setLocationRelativeTo(null);
		getContentPane().setLayout(null);

		this.abreTela(ingrediente);
	}

	private void abreTela(Ingrediente ingrediente) {
		lblAlteracaoDeIngredientes = new JLabel("Altera\u00E7\u00E3o de Ingrediente");
		lblAlteracaoDeIngredientes.setFont(new Font("Tahoma", Font.PLAIN, 19));
		lblAlteracaoDeIngredientes.setBounds(83, 11, 221, 31);

		lblNome = new JLabel("Nome*:");
		lblNome.setBounds(20, 91, 42, 14);

		tfNomeIngrediente = new JTextField();
		tfNomeIngrediente.setToolTipText("Ex: Arroz");
		tfNomeIngrediente.setBounds(78, 88, 151, 20);
		tfNomeIngrediente.setColumns(10);
		tfNomeIngrediente.setText(ingrediente.getNome());

		lblCamposObrigatrios = new JLabel("* campos obrigat\u00F3rios");
		lblCamposObrigatrios.setBounds(10, 251, 128, 14);

		btnSalvar = new JButton("Salvar");
		TrataEventoSalvar trataEventoSalvar = new TrataEventoSalvar();
		btnSalvar.addActionListener(trataEventoSalvar);
		btnSalvar.setBounds(76, 195, 89, 23);

		btnCancelar = new JButton("Cancelar");
		btnCancelar.setBounds(204, 195, 89, 23);
		TrataEventoCancelar trataEventoCancelar = new TrataEventoCancelar();
		btnCancelar.addActionListener(trataEventoCancelar);

		lblCodigo = new JLabel("C\u00F3digo");
		lblCodigo.setBounds(20, 60, 46, 14);

		tfCodigo = new JTextField();
		tfCodigo.setEditable(false);
		tfCodigo.setBounds(79, 57, 46, 20);
		tfCodigo.setColumns(10);
		tfCodigo.setText(ingrediente.getCodigo().toString());

		getContentPane().add(lblAlteracaoDeIngredientes);
		getContentPane().add(lblCodigo);
		getContentPane().add(lblNome);
		getContentPane().add(lblCamposObrigatrios);
		getContentPane().add(tfCodigo);
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

	public void fecharTela() {
		this.dispose();
	}

	private class TrataEventoSalvar implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent event) {
			if (!StringUtils.isNuloOuBranco(tfNomeIngrediente.getText())) {
				this.editarIngrediente();
			} else {
				JOptionPane.showMessageDialog(null, "Digite o nome do ingrediente");
			}
		}

		private void editarIngrediente() {
			Ingrediente ingrediente = new Ingrediente(null, tfNomeIngrediente.getText());
			IngredienteDAO ingredienteDAO = new IngredienteDAO();
			try {
				ingredienteDAO.cadastrarIngrediente(ingrediente);
				JOptionPane.showMessageDialog(null, "Ingrediente " + tfNomeIngrediente.getText() + " editado com sucesso.");
				fecharTela();
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
