package br.unisul.gui;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import br.unisul.dados.Autor;
import br.unisul.dao.AutorDAO;
import br.unisul.dao.DAOException;

public class CadastroAutor extends JFrame {

	private static final long serialVersionUID = 1L;
	
	private JTextField nome_autor;

	public CadastroAutor() {
		super("Cadastro de Autor");
		
		nome_autor = new JTextField();
		nome_autor.setBounds(69, 44, 243, 20);
		getContentPane().add(nome_autor);
		nome_autor.setColumns(10);

		JLabel lblNome = new JLabel("Nome  ");
		lblNome.setBounds(28, 47, 46, 14);
		getContentPane().add(lblNome);

		JLabel lblCadastroDeAutor = new JLabel("Cadastro de Autor");
		lblCadastroDeAutor.setHorizontalAlignment(SwingConstants.LEFT);
		lblCadastroDeAutor.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblCadastroDeAutor.setBounds(129, 11, 168, 22);
		getContentPane().add(lblCadastroDeAutor);

		JButton btnCadastrar = new JButton("Salvar");
		btnCadastrar.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				String nomeAutor = nome_autor.getText();

				if (!"".equals(nomeAutor) || nomeAutor == null) {

					AutorDAO autorDAO = new AutorDAO();

					Autor autor = new Autor(null, nomeAutor);

					try {
						autorDAO.cadastreAutor(autor);
						JOptionPane.showMessageDialog(null, "Nome : " + autor.getNome() + " Cadastrado com sucesso");
					} catch (DAOException e) {
						JOptionPane.showMessageDialog(null, "Prezado usuário, infelizmente occoreu um erro ao processar a sua requisição.");
						e.printStackTrace();
					}
				} else {
					JOptionPane.showMessageDialog(null, "Nome digitado não é valido. Tente novamente");
				}
			}
		});
		btnCadastrar.setBounds(119, 98, 89, 23);
		getContentPane().add(btnCadastrar);
		
				JButton btnVoltar = new JButton("Voltar");
				btnVoltar.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
					}
				});
				btnVoltar.setBounds(223, 98, 89, 23);
				getContentPane().add(btnVoltar);
	}

	private static void AbreTela() {
		CadastroAutor cadastrarAutor = new CadastroAutor();
		cadastrarAutor.getContentPane().setLayout(null);
		cadastrarAutor.setVisible(true);
		cadastrarAutor.setSize(400, 400);
		cadastrarAutor.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	public static void main(String[] args) {
		AbreTela();
	}

}
