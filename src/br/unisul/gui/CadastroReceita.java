package br.unisul.gui;

import java.awt.Font;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
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

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class CadastroReceita extends JFrame{

	private static final long serialVersionUID = 1L;
	
	private JTextField tfNomeReceita;
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
	private JTextField textFieldQuantidade;
	private JComboBox<String> comboBoxUnidade;
	private JComboBox<String> comboBoxIngrediente;
	private List<Receita_Ingrediente> listaIngredientesAdicionados = new ArrayList<Receita_Ingrediente>();
	
	CadastroReceita() {
		super("Cadastro Receita");
		this.setResizable(false);
		this.setType(Type.UTILITY);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(800, 500);
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
		
		tfNomeReceita = new JTextField();
		tfNomeReceita.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent arg0) {
				taResumoReceita.setText("Nome da Receita : " + tfNomeReceita.getText());
			}
		});
		tfNomeReceita.setToolTipText("Ex: Bolo de Chocolate");
		tfNomeReceita.setBounds(65, 46, 167, 20);
		getContentPane().add(tfNomeReceita);
		tfNomeReceita.setColumns(10);
		
		lblModoDePreparo = new JLabel("Modo de preparo*:");
		lblModoDePreparo.setBounds(21, 272, 115, 14);
		getContentPane().add(lblModoDePreparo);
		
		cbAutor = new JComboBox<String>();
		cbAutor.setBounds(65, 74, 167, 20);
		prencherComboBoxAutor(cbAutor);
		getContentPane().add(cbAutor);
		
		btnSalvar = new JButton("Salvar");
		btnSalvar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String modoPreparo = null;
				if (!StringUtils.isNuloOuBranco(tfNomeReceita)) {
					String nomeReceita = tfNomeReceita.getText();
					Date dataAtual = new Date();
					
					if(!cbAutor.getSelectedItem().toString().contains(" -- Selecione -- ")){
						AutorDAO autorDAO = new AutorDAO();
						Autor autorASerGravado = new Autor();
						List<Autor> listaAutores;
						try {
							// pegando código do ingrediente selecionado no comboBox
							listaAutores = autorDAO.listeTodosAutores();
							Autor autor = (Autor) listaAutores.get(comboBoxUnidade.getSelectedIndex() - 1);
									
							Integer cd_autor = autor.getCodigo();
							autorASerGravado = new Autor(cd_autor, null, null);
						} catch (DAOException e1) {
							e1.printStackTrace();
						} catch (ArrayIndexOutOfBoundsException e1) {
							JOptionPane.showMessageDialog(null, "Selecione um autor.");
						}
						
						if (!StringUtils.isNuloOuBranco(taModoPreparo)) {
							modoPreparo = taModoPreparo.getText();
							
							Receita receita = new Receita(null, nomeReceita, dataAtual, modoPreparo, autorASerGravado);
							ReceitaDAO receitaeDAO = new ReceitaDAO();
							
							try {
								receitaeDAO.cadastreReceita(receita);
								System.out.println("Receita cadastrada com sucesso");
							} catch (DAOException e1) {
								System.err.println("Prezado usuário, infelizmente occoreu um erro ao processar a sua requisição.");
							}
						} else {
							JOptionPane.showMessageDialog(null, "Digite o modo de preparo.");
						}
					} else {
						JOptionPane.showMessageDialog(null, "Selecione algum Autor");
					}
				} else {
					JOptionPane.showMessageDialog(null, "Digite o nome da receita");
				}
			}
		});
		btnSalvar.setBounds(65, 442, 89, 23);
		getContentPane().add(btnSalvar);
		
		btnCancelar = new JButton("Cancelar");
		btnCancelar.setBounds(183, 442, 89, 23);
		getContentPane().add(btnCancelar);
		
		btnAdicionarIngrediente = new JButton("<html><center>Adicionar<br>Ingrediente</center></html>");
		btnAdicionarIngrediente.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				IngredienteDAO ingredienteDAO = new IngredienteDAO();
				UnidadeDAO unidadeDAO = new UnidadeDAO();
				List<Unidade> listaUnidades;
				List<Ingrediente> listaIngredientes;
				try {
					// pegando código do ingrediente e da unidade selecionados no comboBox
					listaUnidades = unidadeDAO.listeTodasUnidades();
					Unidade unidade = (Unidade) listaUnidades.get(comboBoxUnidade.getSelectedIndex() - 1);
					
					listaIngredientes = ingredienteDAO.listeTodosIngredientes();
					Ingrediente ingrediente = (Ingrediente) listaIngredientes.get(comboBoxIngrediente.getSelectedIndex() - 1);
					
					// resgatando informações do ingrediente
					int codigoUnidade = unidade.getCodigo();
					int codigoIngrediente = ingrediente.getCodigo();
					
					if (!StringUtils.isNuloOuBranco(textFieldQuantidade)) {
						// instanciando objeto com as informações resgatadas da pagina
						Unidade unidadeAserGravada = new Unidade(codigoUnidade, null);

						Ingrediente ingredienteASerGravado = new Ingrediente(codigoIngrediente, null);
						
						Double quantidadeASerGravada = Double.parseDouble(textFieldQuantidade.getText());

						// cadastrando Ingrediente na tabela receita no banco de dados
						Receita_IngredienteDAO receita_IngredienteDAO = new Receita_IngredienteDAO();
						Receita_Ingrediente receita_Ingrediente = new Receita_Ingrediente();
						
						receita_Ingrediente.setReceita(null);
						receita_Ingrediente.setIngredientes(ingredienteASerGravado);
						receita_Ingrediente.setQuantidade(quantidadeASerGravada);
						receita_Ingrediente.setUnidade(unidadeAserGravada);
						
						listaIngredientesAdicionados.add(receita_Ingrediente);
						//receita_IngredienteDAO.cadastreIngredienteNaReceita(receita_Ingrediente);
						
					} else {
						JOptionPane.showMessageDialog(null, "Digite a quantidade");
					}
				} catch (DAOException e1) {
					e1.printStackTrace();
				} catch (ArrayIndexOutOfBoundsException e1) {
					JOptionPane.showMessageDialog(null, "Selecione todos os campos obrigatórios.");
				}
			}
		});
		btnAdicionarIngrediente.setBounds(209, 172, 162, 49);
		getContentPane().add(btnAdicionarIngrediente);
		
		taModoPreparo = new JTextArea();
		taModoPreparo.setWrapStyleWord(true);
		taModoPreparo.setLineWrap(true);
		taModoPreparo.setBounds(21, 308, 350, 123);
		getContentPane().add(taModoPreparo);
		
		jsSeparator = new JSeparator();
		jsSeparator.setOrientation(SwingConstants.VERTICAL);
		jsSeparator.setBounds(394, 11, 10, 454);
		getContentPane().add(jsSeparator);
		
		lblReceita = new JLabel("Resumo da receita :");
		lblReceita.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblReceita.setBounds(414, 17, 197, 20);
		getContentPane().add(lblReceita);
		
		taResumoReceita = new JTextArea();
		taResumoReceita.setBounds(414, 46, 370, 319);
		getContentPane().add(taResumoReceita);
		
		comboBoxIngrediente = new JComboBox();
		comboBoxIngrediente.setBounds(21, 141, 167, 20);
		prencherComboBoxIngrediente(comboBoxIngrediente);
		getContentPane().add(comboBoxIngrediente);
		
		JLabel lblIngrediente = new JLabel("Ingrediente*:");
		lblIngrediente.setBounds(21, 127, 79, 14);
		getContentPane().add(lblIngrediente);
		
		comboBoxUnidade = new JComboBox();
		comboBoxUnidade.setBounds(21, 229, 167, 20);
		prencherComboBoxUnidade(comboBoxUnidade);
		getContentPane().add(comboBoxUnidade);
		
		JLabel lblUnidadeDeMedida = new JLabel("Unidade*:");
		lblUnidadeDeMedida.setBounds(21, 215, 59, 14);
		getContentPane().add(lblUnidadeDeMedida);
		
		textFieldQuantidade = new JTextField();
		textFieldQuantidade.setBounds(21, 186, 66, 20);
		getContentPane().add(textFieldQuantidade);
		textFieldQuantidade.setColumns(10);
		
		JLabel lblQuantidade = new JLabel("Quantidade*:");
		lblQuantidade.setBounds(21, 172, 73, 14);
		getContentPane().add(lblQuantidade);

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
	
	public void fecharTela() {
		this.dispose();
	}

	public static void main(String[] args) {
		CadastroReceita cadastroReceita = new CadastroReceita();
		cadastroReceita.setVisible(true);
	}
}
