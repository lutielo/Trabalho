package br.unisul.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public class MenuPrincipal extends JFrame {

	private static final long serialVersionUID = 1L;
	private JMenuBar mbMenuBarra;
	private JMenu jmArquivo;
	private JMenu jmCadastro;
	private JMenuItem miCadastrarReceita;
	private JMenuItem miCadastrarAutor;
	private JMenuItem miCadastrarIngrediente;

	public MenuPrincipal() {
		super("Menu Principal");
		this.setResizable(false);
		this.setType(Type.UTILITY);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(1000, 700);
		this.setLocationRelativeTo(null);
		getContentPane().setLayout(null);

		this.abreTela();
	}

	private void abreTela() {
		mbMenuBarra = new JMenuBar();
		this.setJMenuBar(mbMenuBarra);

		jmArquivo = new JMenu("Arquivo");
		mbMenuBarra.add(jmArquivo);

		jmCadastro = new JMenu("Cadastro");
		jmArquivo.add(jmCadastro);

		miCadastrarAutor = new JMenuItem("Cadastrar Autor");
		TrataEventoCadastroAutor trataEventoCadastroAutor = new TrataEventoCadastroAutor();
		miCadastrarAutor.addActionListener(trataEventoCadastroAutor);
		jmCadastro.add(miCadastrarAutor);

		miCadastrarIngrediente = new JMenuItem("Cadastrar Ingrediente");
		TrataEventoCadastroIngrediente trataEventoCadastroIngrediente = new TrataEventoCadastroIngrediente();
		miCadastrarIngrediente.addActionListener(trataEventoCadastroIngrediente);
		jmCadastro.add(miCadastrarIngrediente);

		miCadastrarReceita = new JMenuItem("Cadastrar Receita");
		TrataEventoCadastroReceita trataEventoCadastroReceita = new TrataEventoCadastroReceita();
		miCadastrarReceita.addActionListener(trataEventoCadastroReceita);
		jmCadastro.add(miCadastrarReceita);
	}

	private class TrataEventoCadastroAutor implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			CadastroAutor cadastroAutor = new CadastroAutor();
			cadastroAutor.setVisible(true);
		}
	}

	private class TrataEventoCadastroIngrediente implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			CadastroIngrediente cadastroIngrediente = new CadastroIngrediente();
			cadastroIngrediente.setVisible(true);
		}
	}

	private class TrataEventoCadastroReceita implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			CadastroReceita cadastroReceita = new CadastroReceita();
			cadastroReceita.setVisible(true);
		}
	}

	public static void main(String[] args) {
		MenuPrincipal menuPrincipal = new MenuPrincipal();
		menuPrincipal.setVisible(true);
	}
}
