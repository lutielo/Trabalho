package br.unisul.dados;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.List;

import br.gov.geracaotecsc.ui.console.GenericConsoleUI;
import br.unisul.dao.DAOException;
import br.unisul.dao.Receita_IngredienteDAO;

public class ControleReceitaIngredienteApp {
	
	private GenericConsoleUI ui;
	private PrintWriter logErro;

	public static void main(String[] args) {
		new ControleReceitaIngredienteApp().run();
	}

	public ControleReceitaIngredienteApp() {
		ui = new GenericConsoleUI();
		try {
			logErro = new PrintWriter(new FileOutputStream(new File(
					"C:\\temp\\logAplicacaoTrabalhoProg2.txt"), true));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	public void run() {
		int opcao = 0;
		while (opcao != 6) {
			opcao = ui
					.facaPerguntaInt("\n****** MENU INGREDIENTE NA RECEITA ******"
							+ "\n*Oque voce quer fazer?\t\t\t*"
							+ "\n*1-Cadastrar Ingrediente na receita\t*"
							+ "\n*2-Listar Ingredientes das receitas\t*"
							+ "\n*3-Listar Ingredientes mais utilizados\t*"
							+ "\n*4-Deletar Ingrediente da receita \t*"
							+ "\n*5-Altera - n funfa \t\t\t*"
							+ "\n*6-Voltar ao menu principal\t\t*"
							+ "\n*****************************************");
			if (opcao == 1) {
				abrirCadastroIngredienteNaReceita();
			} else if (opcao == 2) {
				abrirListagemIngredientesReceita();
			} else if (opcao == 3) {
				abrirListagemIngredientesMaisUtilizados();
			} else if (opcao == 4) {
				//deletaIngredienteDaReceita();
			} else if (opcao != 5) {
				System.out.println("Opção invalida, tente novamente");
			}
		}
	}

	// private void listarDadosDoAlunoPeloNome() {
	// AlunoDAO alunoDAO = new AlunoDAO();
	// String nomeRecebido = ui
	// .facaPergunta("Digite o nome do aluno para pesquisa");
	// try {
	// List<Aluno> listaAlunos = alunoDAO
	// .listeDadosDoAlunoPorNome(nomeRecebido);
	// if (listaAlunos.isEmpty()) {
	// System.err.println("Não há registros");
	// } else {
	// System.out.println("Listando dados dos alunos cadastrados:");
	// for (Aluno aluno : listaAlunos) {
	// System.out.println("Codigo:" + aluno.getMatricula()
	// + "\tNome:" + aluno.getNome() + "\tCurso: "
	// + aluno.getCurso());
	// }
	// }
	// } catch (DAOException e) {
	// System.out
	// .println("Prezado usuário, infelizmente ocorreu um erro ao processar sua requisição.");
	// e.printStackTrace(logErro);
	// logErro.flush();
	// }
	// }
	//
	// private void listarDadosDoAlunoPeloCodigo() {
	// AlunoDAO alunoDAO = new AlunoDAO();
	// int codigoRecebido = ui
	// .facaPerguntaInt("Digite a matricula do aluno para pesquisa");
	// try {
	// Aluno aluno = alunoDAO.listeDadosDoAlunoPorCodigo(codigoRecebido);
	// if (aluno != null) {
	// System.out.println("Listando dados do aluno com código = "
	// + aluno.getMatricula() + ":");
	// System.out.println("Codigo:" + aluno.getMatricula() + "\tNome:"
	// + aluno.getNome() + "\tCurso: " + aluno.getCurso());
	// } else {
	// System.out.println("Código não existe");
	// }
	// } catch (DAOException e) {
	// System.out
	// .println("Prezado usuário, infelizmente ocorreu um erro ao processar sua requisição.");
	// e.printStackTrace(logErro);
	// logErro.flush();
	// }
	// }

	private void abrirListagemIngredientesReceita() {
		Receita_IngredienteDAO receita_IngredienteDAO = new Receita_IngredienteDAO();
		try {
			List<Receita_Ingrediente> listaReceita_Ingredientes = receita_IngredienteDAO.listeTodosIngredientesDasReceitas();
			if (listaReceita_Ingredientes.isEmpty()) {
				System.err.println("Não há registros");
			} else {
				System.out.println("Listando dados dos ingredientes cadastrados nas receitas:");
				for (Receita_Ingrediente receita_Ingrediente : listaReceita_Ingredientes) {
					System.out.println("\tNome Ingrediente:" + receita_Ingrediente.getIngrediente().getNome()
								+ "\tNome Receita:" + receita_Ingrediente.getReceita().getNome()
								+ "\tNome Receita:" + receita_Ingrediente.getUnidade().getTipo()
								+ "\tQuantidade:" + receita_Ingrediente.getQuantidade());
				}
			}
		} catch (DAOException e) {
			System.out.println("Prezado usuário, infelizmente ocorreu um erro ao processar sua requisição.");
			e.printStackTrace(logErro);
			logErro.flush();
		}
	}
	
	private void abrirListagemIngredientesMaisUtilizados() {
		Receita_IngredienteDAO receita_IngredienteDAO = new Receita_IngredienteDAO();
		try {
			List<Receita_Ingrediente> listaReceita_Ingredientes = receita_IngredienteDAO.listeIngredientesMaisUtilizados();
			if (listaReceita_Ingredientes.isEmpty()) {
				System.err.println("Não há registros");
			} else {
				System.out.println("Listando ingredientes mais utilizados nas receitas:");
				for (Receita_Ingrediente receita_Ingrediente : listaReceita_Ingredientes) {
					System.out.println("Nome Ingrediente:" + receita_Ingrediente.getIngrediente().getNome()
									 + "\tQuantidade:" + receita_Ingrediente.getQuantidade());
				}
			}
		} catch (DAOException e) {
			System.out.println("Prezado usuário, infelizmente ocorreu um erro ao processar sua requisição.");
			e.printStackTrace(logErro);
			logErro.flush();
		}
	}
	
	private void abrirCadastroIngredienteNaReceita() {
		Integer codigoReceita = ui.facaPerguntaInt("Digite o codigo da receita");
		Receita receita = new Receita(codigoReceita, null, null, null, null);
		
		Integer codigoIngrediente = ui.facaPerguntaInt("Digite o codigo do ingrediente");
		Ingrediente ingrediente = new Ingrediente(codigoIngrediente, null);
		
		Integer codigoUnidade = ui.facaPerguntaInt("Digite o codigo da unidade");
		Unidade unidade = new Unidade(codigoUnidade, null);
		
		Double quantidade = ui.facaPerguntaDouble("Digite a quantidade do ingrediente");

		Receita_Ingrediente receita_Ingrediente = new Receita_Ingrediente(receita, ingrediente, unidade, quantidade);
		Receita_IngredienteDAO Receita_IngredienteDAO = new Receita_IngredienteDAO();
		try {
			Receita_IngredienteDAO.cadastreIngredienteNaReceita(receita_Ingrediente);
			System.out.println("Ingrediente cadastrado na receita com sucesso");
		} catch (DAOException e) {
			System.err.println("Prezado usuário, infelizmente occoreu um erro ao processar a sua requisição.");
			e.printStackTrace(logErro);
			logErro.flush();
		}
	}
	
//	private void deletaIngredienteDaReceita() {
//		Receita_IngredientesDAO Receita_IngredientesDAO = new Receita_IngredientesDAO();
//		int codigoRecebido = ui.facaPerguntaInt("Digite o codigo do ingrediente na receita para deletar");
//		try {
//			Receita_Ingrediente receita_Ingrediente = new Receita_Ingrediente(codigoRecebido, null, null, null);
//			//TODO METODO DELATAR NA DAO 
//			//Receita_IngredientesDAO.deletaIngredienteDaReceita(receita_Ingrediente);
//			System.out.println("Autor deletado com sucesso");
//		} catch (DAOException e) {
//			System.out.println("Prezado usuário, infelizmente ocorreu um erro ao processar sua requisição.");
//			e.printStackTrace(logErro);
//			logErro.flush();
//		}
//	}
}
