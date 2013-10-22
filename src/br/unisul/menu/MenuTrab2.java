package br.unisul.menu;

import br.gov.geracaotecsc.ui.console.GenericConsoleUI;

public class MenuTrab2 {
	
	private GenericConsoleUI ui;

	public static void main(String[] args) {
		new MenuTrab2().run();
	}

	public MenuTrab2() {
		ui = new GenericConsoleUI();
	}

	public void run() {
		int opcao = 0;
		while(opcao != 4){
			opcao = ui.facaPerguntaInt( "\n**********  MENU  BIBLIOTECA ************"+
										"\n*Oque voce quer fazer?\t\t\t*" +
										"\n*1-Ações Livro\t\t\t\t*" +
										"\n*2-Ações Aluno\t\t\t\t*" +
										"\n*3-Ações Professor\t\t\t*" +
										"\n*4-Sair\t\t\t\t\t*"+
										"\n*****************************************");
			if (opcao == 1){
				//new ControleLivrosApp().run();
			} else if (opcao == 2){
				//new ControleAlunosApp().run();
			} else if (opcao == 3){
				//new ControleProfessoresApp().run();
			} else if (opcao != 4){
				System.out.println("Opção invalida, tente novamente");
			} else{
				System.out.println("Saindo do programa...");
			}
		}
	}
}
