package br.unisul.gui;

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
import br.unisul.dados.Unidade;
import br.unisul.dao.DAOException;
import br.unisul.dao.IngredienteDAO;
import br.unisul.dao.UnidadeDAO;
import br.unisul.util.IndexedFocusTraversalPolicy;
import br.unisul.util.StringUtils;

public class CadastroIngrediente extends JFrame {

	private static final long serialVersionUID = 1L;

	private JTextField txtNomeIngrediente;
	private JComboBox<String> cbUnidade;
	private JLabel lblCadastroDeIngredientes;
	private JLabel lblNome;
	private JLabel lblUnidade;
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

		txtNomeIngrediente = new JTextField();
		txtNomeIngrediente.setToolTipText("Ex: Arroz");
		txtNomeIngrediente.setBounds(80, 53, 151, 20);
		txtNomeIngrediente.setColumns(10);

		lblUnidade = new JLabel("Unidade*:");
		lblUnidade.setBounds(22, 93, 60, 14);

		cbUnidade = new JComboBox<String>();
		cbUnidade.setToolTipText("Selecione a unidade de medida do ingrediente");
		cbUnidade.setBounds(83, 90, 119, 20);
		prencherComboBoxUnidade(cbUnidade);

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
		getContentPane().add(txtNomeIngrediente);
		getContentPane().add(lblUnidade);
		getContentPane().add(cbUnidade);
		getContentPane().add(lblCamposObrigatrios);
		getContentPane().add(btnSalvar);
		getContentPane().add(btnCancelar);

		this.tabOrder();
	}

	private void tabOrder() {
		IndexedFocusTraversalPolicy policy = new IndexedFocusTraversalPolicy();
		policy.addIndexedComponent(txtNomeIngrediente);
		policy.addIndexedComponent(cbUnidade);
		policy.addIndexedComponent(btnSalvar);
		policy.addIndexedComponent(btnCancelar);
		setFocusTraversalPolicy(policy);
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
		public void actionPerformed(ActionEvent event) {
			UnidadeDAO unidadeDAO = new UnidadeDAO();
			List<Unidade> listaUnidades;
			try {
				// pegando código do ingrediente selecionado no comboBox
				listaUnidades = unidadeDAO.listeTodasUnidades();
				Unidade unidade = (Unidade) listaUnidades.get(cbUnidade.getSelectedIndex() - 1);

				// resgatando informações do ingrediente
				int codigoUnidade = unidade.getCodigo();
				String nomeIngrediente = txtNomeIngrediente.getText();

				if (!StringUtils.isNuloOuBranco(nomeIngrediente)) {
					// instanciando objeto com as informações resgatadas da pagina
					Unidade unidadeAserGravada = new Unidade(codigoUnidade, null);

					Ingrediente ingredienteASerGravado = new Ingrediente();
					ingredienteASerGravado.setUnidade(unidadeAserGravada);
					ingredienteASerGravado.setNome(nomeIngrediente);

					// cadastrando Ingrediente no banco de dados
					IngredienteDAO ingredienteDAO = new IngredienteDAO();
					ingredienteDAO.cadastreIngrediente(ingredienteASerGravado);
					JOptionPane.showMessageDialog(null, "Ingrediente " + nomeIngrediente + " cadastrado com sucesso.");

				} else {
					JOptionPane.showMessageDialog(null, "Digite o nome do ingrediente");
				}
			} catch (DAOException e) {
				e.printStackTrace();
			} catch (ArrayIndexOutOfBoundsException e) {
				JOptionPane.showMessageDialog(null, "Selecione uma unidade.");
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
