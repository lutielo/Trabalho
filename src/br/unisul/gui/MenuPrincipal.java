package br.unisul.gui;

import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenu;

public class MenuPrincipal extends JFrame {

	private static final long serialVersionUID = 1L;
	private JMenuBar mbMenuBarra;
	private JMenu jmArquivo;

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
	}

	public static void main(String[] args) {
		MenuPrincipal menuPrincipal = new MenuPrincipal();
		menuPrincipal.setVisible(true);
	}
}
