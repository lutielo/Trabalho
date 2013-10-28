package br.unisul.gui;

import java.awt.Font;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import br.unisul.dados.Unidade;
import br.unisul.dao.DAOException;
import br.unisul.dao.UnidadeDAO;

public class CadastroIngrediente extends JFrame {

	private static final long serialVersionUID = 1L;
	private JTextField textField;
	private JComboBox comboBox;
	
	CadastroIngrediente() {
		super("Cadastro Ingrediente");
		setResizable(false);
		setType(Type.UTILITY);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(400, 300);
		getContentPane().setLayout(null);

		this.abreTela();
	}
	
	private void abreTela() {
		JLabel lblCadastroDeIngredientes = new JLabel("Cadastro de Ingrediente");
		lblCadastroDeIngredientes.setFont(new Font("Tahoma", Font.PLAIN, 19));
		lblCadastroDeIngredientes.setBounds(83, 11, 221, 31);
		getContentPane().add(lblCadastroDeIngredientes);
		
		JLabel lblNome = new JLabel("Nome*:");
		lblNome.setBounds(22, 56, 42, 14);
		getContentPane().add(lblNome);
		
		textField = new JTextField();
		textField.setBounds(80, 53, 151, 20);
		getContentPane().add(textField);
		textField.setColumns(10);
		
		JLabel lblUnidade = new JLabel("Unidade*:");
		lblUnidade.setBounds(22, 93, 60, 14);
		getContentPane().add(lblUnidade);
		
		comboBox = new JComboBox();
		comboBox.setBounds(83, 90, 119, 20);
		prencherComboBoxUnidade(comboBox);
		getContentPane().add(comboBox);
		
		JLabel lblCamposObrigatrios = new JLabel("* campos obrigat\u00F3rios");
		lblCamposObrigatrios.setBounds(10, 251, 128, 14);
		getContentPane().add(lblCamposObrigatrios);
		
		JButton btnSalvar = new JButton("Salvar");
		btnSalvar.setBounds(76, 195, 89, 23);
		getContentPane().add(btnSalvar);
		
		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.setBounds(204, 195, 89, 23);
		getContentPane().add(btnCancelar);
	}
	
	public void prencherComboBoxUnidade(JComboBox comboBox) {
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

	public static void main(String[] args) {
		CadastroIngrediente cadastroIngrediente = new CadastroIngrediente();
		cadastroIngrediente.setVisible(true);
	}
}
