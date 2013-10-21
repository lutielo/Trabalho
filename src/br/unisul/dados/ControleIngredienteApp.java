package br.unisul.dados;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.List;

import br.gov.geracaotecsc.ui.console.GenericConsoleUI;
import br.unisul.dao.DAOException;
import br.unisul.dao.IngredienteDAO;

public class ControleIngredienteApp {
	
	//Teste lucas
	
	private GenericConsoleUI ui;
	private PrintWriter logErro;

	public static void main(String[] args) {
		new ControleIngredienteApp().run();
	}

	public ControleIngredienteApp() {
		ui = new GenericConsoleUI();
		try {
			logErro = new PrintWriter(new FileOutputStream(new File("C:\\temp\\logAplicacaoTrabalhoProg2.txt"), true));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	public void run() {
		int opcao = 0;
		while (opcao != 7) {
			opcao = ui.facaPerguntaInt("\n**********  MENU  INGREDIENTE ***********" + 
									   "\n*Oque voce quer fazer?\t\t\t*" + 
									   "\n*1-Cadastrar Ingrediente\t\t*" + 
									   "\n*2-Listar todos Ingredientes\t\t*" + 
									   "\n*3-Listar Ingredientes pelo nome\t*" + 
									   "\n*4-Listar Ingredientes pelo código\t*" + 
									   "\n*5-Deletar Ingrediente \t\t\t*" + 
									   "\n*6-Altera - n funfa \t\t\t*" +
									   "\n*7-Voltar ao menu principal\t\t*" + 
									   "\n*****************************************");
			if (opcao == 1) {
				abrirCadastroIngrediente();
			} else if (opcao == 2) {
				abrirListagemIngredientes();
			} else if (opcao == 3) {
				listarDadosIngredientePeloNome();
			} else if (opcao == 4) {
				listarDadosIngredientePeloCodigo();
			} else if (opcao == 5) {
				deletaIngrediente();
			} else if (opcao == 6) {
				//TODO ALTERAR
			} else if (opcao != 7) {
				System.out.println("Opção invalida, tente novamente");
			}
		}
	}

	private void listarDadosIngredientePeloNome() {
		IngredienteDAO ingredienteDAO = new IngredienteDAO();
		String nomeRecebido = ui.facaPergunta("Digite o nome do ingrediente para pesquisa");
		try {
			List<Ingrediente> listaIngredientes = ingredienteDAO.listeTodosIngredientesPeloNome(nomeRecebido);
			if (listaIngredientes.isEmpty()) {
				System.err.println("Não há registros");
			} else {
				System.out.println("Listando dados dos ingredientes cadastrados:");
				for (Ingrediente ingrediente : listaIngredientes) {
					System.out.println("Codigo:" + ingrediente.getCodigo() + "\tNome:" + ingrediente.getNome() + "\tUnidade: " + ingrediente.getUnidade().getTipo());
				}
			}
		} catch (DAOException e) {
			System.out.println("Prezado usuário, infelizmente ocorreu um erro ao processar sua requisição.");
			e.printStackTrace(logErro);
			logErro.flush();
		}
	}

	private void listarDadosIngredientePeloCodigo() {
		IngredienteDAO ingredienteDAO = new IngredienteDAO();
		int codigoRecebido = ui.facaPerguntaInt("Digite o codigo do ingrediente para pesquisa");
		try {
			Ingrediente ingrediente = ingredienteDAO.listeDadosDoIngredientePeloCodigo(codigoRecebido);
			if (ingrediente != null) {
				System.out.println("Listando dados do ingrediente com código = " + ingrediente.getCodigo() + ":");
				System.out.println("Codigo:" + ingrediente.getCodigo() + "\tNome:" + ingrediente.getNome() + "\tCodigo da Unidade: " + ingrediente.getUnidade().getCodigo());
			} else {
				System.out.println("Código não existe");
			}
		} catch (DAOException e) {
			System.out.println("Prezado usuário, infelizmente ocorreu um erro ao processar sua requisição.");
			e.printStackTrace(logErro);
			logErro.flush();
		}
	}

	private void abrirListagemIngredientes() {
		IngredienteDAO ingredienteDAO = new IngredienteDAO();
		try {
			List<Ingrediente> listaIngredientes = ingredienteDAO.listeTodosIngredientes();
			if (listaIngredientes.isEmpty()) {
				System.err.println("Não há registros");
			} else {
				System.out.println("Listando dados dos ingredientes cadastrados:");
				for (Ingrediente ingrediente : listaIngredientes) {
					System.out.println("Codigo:" + ingrediente.getCodigo() + "\tNome:" + ingrediente.getNome() + "\tCodigo unidade:" + ingrediente.getUnidade().getCodigo());
				}
			}
		} catch (DAOException e) {
			System.out.println("Prezado usuário, infelizmente ocorreu um erro ao processar sua requisição.");
			e.printStackTrace(logErro);
			logErro.flush();
		}
	}

	private void abrirCadastroIngrediente() {
		String nome = ui.facaPergunta("Digite o nome do ingrediente");
		Integer cd_tp_unidade = ui.facaPerguntaInt("Digite o codigo do tipo da unidade do ingrediente");

		Unidade unidade = new Unidade(cd_tp_unidade, null);
		Ingrediente ingrediente = new Ingrediente(null, nome, unidade);

		IngredienteDAO ingredienteDAO = new IngredienteDAO();
		try {
			ingredienteDAO.cadastreIngrediente(ingrediente);
			System.out.println("Ingrediente cadastrado com sucesso");
		} catch (DAOException e) {
			System.err.println("Prezado usuário, infelizmente occoreu um erro ao processar a sua requisição.");
			e.printStackTrace(logErro);
			logErro.flush();
		}
	}

	private void deletaIngrediente() {
		IngredienteDAO ingredienteDAO = new IngredienteDAO();
		int codigoRecebido = ui.facaPerguntaInt("Digite o codigo do ingrediente para deletar");
		try {
			Ingrediente ingrediente = new Ingrediente(codigoRecebido, null, null);
			ingredienteDAO.deletaIngrediente(ingrediente);
			System.out.println("Ingrediente deletado com sucesso");
		} catch (DAOException e) {
			System.out.println("Prezado usuário, infelizmente ocorreu um erro ao processar sua requisição.");
			e.printStackTrace(logErro);
			logErro.flush();
		}
	}
}
