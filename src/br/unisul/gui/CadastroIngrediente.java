package br.unisul.gui;

import javax.swing.JFrame;

public class CadastroIngrediente extends JFrame {

	private static final long serialVersionUID = 1L;
	
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
		
	}
	
	public void fecharTela() {
		this.dispose();
	}

	public static void main(String[] args) {
		CadastroAutor cadastroAutor = new CadastroAutor();
		cadastroAutor.setVisible(true);
	}
}
