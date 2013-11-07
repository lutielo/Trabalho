package br.unisul.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import br.unisul.gui.cadastros.CadastroAutor;
import br.unisul.gui.cadastros.CadastroIngrediente;
import br.unisul.gui.cadastros.CadastroReceita;

public class MenuPrincipal extends JFrame {

	private static final long serialVersionUID = 1L;
	
	private JMenuBar mbMenuBarra;
	private JMenu mnArquivo;
	private JMenu mnCadastro;
	private JMenuItem miCadastroReceita;
	private JMenuItem miCadastroAutor;
	private JMenuItem miCadastroIngrediente;
	private JMenu mnSair;

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

		mnArquivo = new JMenu("Arquivo");
		mbMenuBarra.add(mnArquivo);

		mnCadastro = new JMenu("Cadastro");
		mnArquivo.add(mnCadastro);

		miCadastroAutor = new JMenuItem("Cadastro Autor");
		TrataEventoCadastroAutor trataEventoCadastroAutor = new TrataEventoCadastroAutor();
		miCadastroAutor.addActionListener(trataEventoCadastroAutor);
		mnCadastro.add(miCadastroAutor);

		miCadastroIngrediente = new JMenuItem("Cadastro Ingrediente");
		TrataEventoCadastroIngrediente trataEventoCadastroIngrediente = new TrataEventoCadastroIngrediente();
		miCadastroIngrediente.addActionListener(trataEventoCadastroIngrediente);
		mnCadastro.add(miCadastroIngrediente);

		miCadastroReceita = new JMenuItem("Cadastro Receita");
		TrataEventoCadastroReceita trataEventoCadastroReceita = new TrataEventoCadastroReceita();
		miCadastroReceita.addActionListener(trataEventoCadastroReceita);
		mnCadastro.add(miCadastroReceita);

		mnSair = new JMenu("Sair");
		mbMenuBarra.add(mnSair);
		mnSair.addMouseListener(new java.awt.event.MouseAdapter() {

			@Override
			public void mouseClicked(java.awt.event.MouseEvent e) {
				int dialogButton = JOptionPane.showConfirmDialog(null, "Deseja sair?", "Atenção", JOptionPane.YES_NO_OPTION);
				if (dialogButton == JOptionPane.YES_OPTION) {
					System.exit(0);
				}
			}
		});
	}

	public void fecharTela() {
		getContentPane();
		System.exit(0);
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
