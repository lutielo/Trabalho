package br.unisul.gui;

import java.awt.Font;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JSeparator;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import br.unisul.dados.Autor;
import br.unisul.dao.AutorDAO;
import br.unisul.dao.DAOException;

public class CadastroReceita extends JFrame{

	private static final long serialVersionUID = 1L;
	
	private JTextField tfNome;
	private JComboBox<String> cbAutor;
	private JLabel lblCadastro;
	private JLabel lblNomeDaReceita;
	private JLabel lblNomeDoAutor;
	private JLabel lblModoDePreparo;
	private JLabel lblReceita;
	private JSeparator jsSeparator;
	private JTextArea taModoPreparo;
	private JTextArea taResumoReceita;
	private JButton btnAdicionarIngrediente;
	private JButton btnSalvar;
	private JButton btnCancelar;
	
	CadastroReceita() {
		super("Cadastro Receita");
		this.setResizable(false);
		this.setType(Type.UTILITY);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(800, 400);
		getContentPane().setLayout(null);
		
		lblCadastro = new JLabel("Cadastro de Receita");
		lblCadastro.setFont(new Font("Tahoma", Font.PLAIN, 19));
		lblCadastro.setBounds(103, 11, 190, 29);
		getContentPane().add(lblCadastro);
		
		lblNomeDaReceita = new JLabel("Nome*:");
		lblNomeDaReceita.setBounds(21, 49, 45, 14);
		getContentPane().add(lblNomeDaReceita);
		
		lblNomeDoAutor = new JLabel("Autor*:");
		lblNomeDoAutor.setBounds(21, 77, 45, 14);
		getContentPane().add(lblNomeDoAutor);
		
		tfNome = new JTextField();
		tfNome.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent arg0) {
				taResumoReceita.setText("Nome da Receita : " + tfNome.getText());
			}
		});
		tfNome.setToolTipText("Ex: Bolo de Chocolate");
		tfNome.setBounds(65, 46, 167, 20);
		getContentPane().add(tfNome);
		tfNome.setColumns(10);
		
		lblModoDePreparo = new JLabel("Modo de preparo*:");
		lblModoDePreparo.setBounds(21, 151, 115, 14);
		getContentPane().add(lblModoDePreparo);
		
		cbAutor = new JComboBox<String>();
		cbAutor.setBounds(65, 74, 167, 20);
		prencherComboBoxAutor(cbAutor);
		getContentPane().add(cbAutor);
		
		btnSalvar = new JButton("Salvar");
		btnSalvar.setBounds(65, 310, 89, 23);
		getContentPane().add(btnSalvar);
		
		btnCancelar = new JButton("Cancelar");
		btnCancelar.setBounds(187, 310, 89, 23);
		getContentPane().add(btnCancelar);
		
		btnAdicionarIngrediente = new JButton("Adicionar Ingrediente");
		btnAdicionarIngrediente.setBounds(108, 117, 155, 23);
		getContentPane().add(btnAdicionarIngrediente);
		
		taModoPreparo = new JTextArea();
		taModoPreparo.setWrapStyleWord(true);
		taModoPreparo.setLineWrap(true);
		taModoPreparo.setBounds(21, 176, 350, 123);
		getContentPane().add(taModoPreparo);
		
		jsSeparator = new JSeparator();
		jsSeparator.setOrientation(SwingConstants.VERTICAL);
		jsSeparator.setBounds(394, 11, 10, 354);
		getContentPane().add(jsSeparator);
		
		lblReceita = new JLabel("Resumo da receita :");
		lblReceita.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblReceita.setBounds(414, 17, 197, 20);
		getContentPane().add(lblReceita);
		
		taResumoReceita = new JTextArea();
		taResumoReceita.setBounds(414, 46, 370, 319);
		getContentPane().add(taResumoReceita);

		this.abreTela();
	}
	
	private void abreTela() {

	}
	
	public void prencherComboBoxAutor(JComboBox<String> comboBox) {
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
