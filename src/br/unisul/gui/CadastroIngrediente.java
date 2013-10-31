package br.unisul.gui;

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

public class CadastroIngrediente extends JFrame {

	private static final long serialVersionUID = 1L;

	private JTextField tfNomeIngrediente;
	private JLabel lblCadastroDeIngredientes;
	private JLabel lblNome;
	private JLabel lblCamposObrigatrios;
	private JButton btnSalvar;
	private JButton btnCancelar;

	CadastroIngrediente() {
		super("Cadastro Ingrediente");
		this.setResizable(false);
		this.setType(Type.UTILITY);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(400, 300);
		this.setLocationRelativeTo(null);
		getContentPane().setLayout(null);

		this.abreTela();
	}

	private void abreTela() {
		lblCadastroDeIngredientes = new JLabel("Cadastro de Ingrediente");
		lblCadastroDeIngredientes.setFont(new Font("Tahoma", Font.PLAIN, 19));
		lblCadastroDeIngredientes.setBounds(83, 11, 221, 31);

		lblNome = new JLabel("Nome*:");
		lblNome.setBounds(22, 56, 42, 14);

		tfNomeIngrediente = new JTextField();
		tfNomeIngrediente.setToolTipText("Ex: Arroz");
		tfNomeIngrediente.setBounds(80, 53, 151, 20);
		tfNomeIngrediente.setColumns(10);

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

		getContentPane().add(lblCadastroDeIngredientes);
		getContentPane().add(lblNome);
		getContentPane().add(tfNomeIngrediente);
		getContentPane().add(lblCamposObrigatrios);
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
			try {
				// resgatando informações do ingrediente
				String nomeIngrediente = tfNomeIngrediente.getText();

				if (!StringUtils.isNuloOuBranco(nomeIngrediente)) {
					// instanciando objeto com as informações resgatadas da pagina

					Ingrediente ingredienteASerGravado = new Ingrediente(null, nomeIngrediente);

					// cadastrando Ingrediente no banco de dados
					IngredienteDAO ingredienteDAO = new IngredienteDAO();
					ingredienteDAO.cadastreIngrediente(ingredienteASerGravado);
					JOptionPane.showMessageDialog(null, "Ingrediente " + nomeIngrediente + " cadastrado com sucesso.");

				} else {
					JOptionPane.showMessageDialog(null, "Digite o nome do ingrediente");
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
		CadastroIngrediente cadastroIngrediente = new CadastroIngrediente();
		cadastroIngrediente.setVisible(true);
	}
}
