package br.unisul.gui;

import java.awt.Font;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import br.unisul.dados.Autor;
import br.unisul.dao.AutorDAO;
import br.unisul.dao.DAOException;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;
import javax.swing.JTextPane;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

public class CadastroReceita extends JFrame{

	private static final long serialVersionUID = 1L;
	private JTextField textField;
	private JComboBox comboBox;
	private JLabel lblCadastro;
	private JLabel lblNomeDaReceita;
	private JLabel lblNomeDoAutor;
	private JLabel lblModoDePreparo;
	private JButton btnAdicionarIngrediente;
	private JTextArea textArea;
	private JSeparator separator;
	private JLabel lblReceita;
	private JTextArea textArea_1;
	
	CadastroReceita() {
		super("Cadastro Receita");
		getContentPane().setFont(new Font("Tahoma", Font.PLAIN, 19));
		setResizable(false);
		setType(Type.UTILITY);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(800, 400);
		getContentPane().setLayout(null);
		
		lblCadastro = new JLabel("Cadastro de Receita");
		lblCadastro.setFont(new Font("Tahoma", Font.PLAIN, 19));
		lblCadastro.setBounds(103, 11, 190, 29);
		getContentPane().add(lblCadastro);
		
		lblNomeDaReceita = new JLabel("Nome*:");
		lblNomeDaReceita.setBounds(21, 49, 94, 14);
		getContentPane().add(lblNomeDaReceita);
		
		lblNomeDoAutor = new JLabel("Autor*:");
		lblNomeDoAutor.setBounds(21, 77, 45, 14);
		getContentPane().add(lblNomeDoAutor);
		
		textField = new JTextField();
		textField.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent arg0) {
				textArea_1.setText("Nome da Receita : " + textField.getText());
			}
		});
		textField.setToolTipText("Ex: Bolo de Chocolate");
		textField.setBounds(65, 46, 167, 20);
		getContentPane().add(textField);
		textField.setColumns(10);
		
		lblModoDePreparo = new JLabel("Modo de preparo*:");
		lblModoDePreparo.setBounds(21, 151, 115, 14);
		getContentPane().add(lblModoDePreparo);
		
		comboBox = new JComboBox();
		comboBox.setBounds(65, 74, 167, 20);
		prencherComboBoxAutor(comboBox);
		getContentPane().add(comboBox);
		
		JButton btnSalvar = new JButton("Salvar");
		btnSalvar.setBounds(65, 310, 89, 23);
		getContentPane().add(btnSalvar);
		
		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.setBounds(187, 310, 89, 23);
		getContentPane().add(btnCancelar);
		
		btnAdicionarIngrediente = new JButton("Adicionar Ingrediente");
		btnAdicionarIngrediente.setBounds(108, 117, 155, 23);
		getContentPane().add(btnAdicionarIngrediente);
		
		textArea = new JTextArea();
		textArea.setWrapStyleWord(true);
		textArea.setLineWrap(true);
		textArea.setBounds(21, 176, 350, 123);
		getContentPane().add(textArea);
		
		separator = new JSeparator();
		separator.setOrientation(SwingConstants.VERTICAL);
		separator.setBounds(394, 11, 10, 354);
		getContentPane().add(separator);
		
		lblReceita = new JLabel("Resumo da receita :");
		lblReceita.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblReceita.setBounds(414, 17, 197, 20);
		getContentPane().add(lblReceita);
		
		textArea_1 = new JTextArea();
		textArea_1.setBounds(414, 46, 370, 319);
		getContentPane().add(textArea_1);

		this.abreTela();
	}
	
	private void abreTela() {

	}
	
	public void prencherComboBoxAutor(JComboBox comboBox) {
		AutorDAO autorDAO = new AutorDAO();
		try {
			List<Autor> listaAutores = autorDAO.listeTodosAutores();
			comboBox.addItem(" -- Selecione -- ");
			for (Autor autor : listaAutores) {
				comboBox.addItem(autor.getNome());
			}
		} catch (DAOException e) {
			e.printStackTrace();
		}
	}
	
	public void fecharTela() {
		this.dispose();
	}

	public static void main(String[] args) {
		CadastroReceita cadastroReceita = new CadastroReceita();
		cadastroReceita.setVisible(true);
	}
}
