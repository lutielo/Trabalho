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
import br.unisul.gui.relatorios.janelas.ListagemAutores;
import br.unisul.gui.relatorios.janelas.ListagemIngredientes;
import br.unisul.gui.relatorios.janelas.ListagemReceitas;

public class MenuPrincipal extends JFrame {

	private static final long serialVersionUID = 1L;
	
	private JMenuBar mbMenuBarra;
	private JMenu mnArquivo;
	private JMenu mnCadastro;
	private JMenuItem miCadastroReceita;
	private JMenuItem miCadastroAutor;
	private JMenuItem miCadastroIngrediente;
	private JMenu mnListagem;
	private JMenuItem mntmListagemAutores;
	private JMenuItem mntmListagemIngredientes;
	private JMenuItem mntmListagemReceitas; 
	private JMenu mnRelatorios;
	
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
		
		mnListagem = new JMenu("Listagem");
		mnArquivo.add(mnListagem);
		
		mntmListagemAutores = new JMenuItem("Listagem Autores");
		TrataEventoListagemAutores trataEventoListagemAutores = new TrataEventoListagemAutores();
		mntmListagemAutores.addActionListener(trataEventoListagemAutores);
		mnListagem.add(mntmListagemAutores);
		
		mntmListagemIngredientes = new JMenuItem("Listagem Ingredientes");
		TrataEventoListagemIngredientes trataEventoListagemIngredientes = new TrataEventoListagemIngredientes();
		mntmListagemIngredientes.addActionListener(trataEventoListagemIngredientes);
		mnListagem.add(mntmListagemIngredientes);
		
		mntmListagemReceitas = new JMenuItem("Listagem Receitas");
		TrataEventoListagemReceitas trataEventoListagemReceitas = new TrataEventoListagemReceitas();
		mntmListagemReceitas.addActionListener(trataEventoListagemReceitas);
		mnListagem.add(mntmListagemReceitas);
		
		mnRelatorios = new JMenu("Relat\u00F3rios");
		mbMenuBarra.add(mnRelatorios);

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
	
	private class TrataEventoListagemAutores implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			
			ListagemAutores listagemAutores = new ListagemAutores();
			listagemAutores.setVisible(true);
		}
		
	}
	
	private class TrataEventoListagemIngredientes implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {

			ListagemIngredientes listagemIngredientes = new ListagemIngredientes();
			listagemIngredientes.setVisible(true);
		}
		
	}
	
	private class TrataEventoListagemReceitas implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {

			ListagemReceitas listagemReceitas = new ListagemReceitas();
			listagemReceitas.setVisible(true);
		}
		
	}

	public static void main(String[] args) {
		MenuPrincipal menuPrincipal = new MenuPrincipal();
		menuPrincipal.setVisible(true);
	}
}
