package br.unisul.dados;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.Date;
import java.util.List;

import br.gov.geracaotecsc.ui.console.GenericConsoleUI;
import br.unisul.dao.DAOException;
import br.unisul.dao.ReceitaDAO;

public class ControleReceitaApp {

	private GenericConsoleUI ui;
	private PrintWriter logErro;
	
	//TESTE

	public static void main(String[] args) {
		new ControleReceitaApp().run();
	}

	public ControleReceitaApp() {
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
			opcao = ui.facaPerguntaInt
							("\n************  MENU  RECEITA *************"
							+ "\n*Oque voce quer fazer?\t\t\t*"
							+ "\n*1-Cadastrar Receita\t\t\t*"
							+ "\n*2-Listar todas Receitas\t\t*"
							+ "\n*3-Listar receita pelo nome \t\t*"
							+ "\n*4-Listar receita pelo código \t\t*"
							+ "\n*5-Deleta receita \t\t\t*"
							+ "\n*6-Alterar - n funfa \t\t\t*"
							+ "\n*7-Voltar ao menu principal\t\t*"
							+ "\n*****************************************");
			if (opcao == 1) {
				abrirCadastroReceita();
			} else if (opcao == 2) {
				abrirListagemReceitas();
			} else if (opcao == 3) {
				listarDadosReceitaPeloNome();
			} else if (opcao == 4) {
				listarDadosReceitaPeloCodigo();
			} else if (opcao == 5) {
				deletaReceita();
			} else if (opcao == 6) {

			} else if (opcao != 7) {
				System.out.println("Opção invalida, tente novamente");
			}
		}
	}

	private void listarDadosReceitaPeloNome() {
		ReceitaDAO receitaDAO = new ReceitaDAO();
		String nomeRecebido = ui.facaPergunta("Digite o nome da receita para pesquisa");
		try {
			List<Receita> listaReceita = receitaDAO.listeTodasReceitasPeloNome(nomeRecebido);
			if (listaReceita.isEmpty()) {
				System.err.println("Não há registros");
			} else {
				System.out.println("Listando dados das receitas cadastradas:");
				for (Receita receita : listaReceita) {
					System.out.println("Codigo:" + receita.getCodigo() + "\tNome:" + receita.getNome() + "\tAutor: " + receita.getAutor().getNome() + "\tData de Criação: " + receita.getDataCriacaoFormatada() + "\tModo de preparo: " + receita.getModo_preparo());
				}
			}
		} catch (DAOException e) {
			System.out.println("Prezado usuário, infelizmente ocorreu um erro ao processar sua requisição.");
			e.printStackTrace(logErro);
			logErro.flush();
		}
	}

	private void listarDadosReceitaPeloCodigo() {
		ReceitaDAO receitaDAO = new ReceitaDAO();
		int codigoRecebido = ui.facaPerguntaInt("Digite o codigo da receita para pesquisa");
		try {
			Receita receita = receitaDAO.listeDadosDaReceitaPeloCodigo(codigoRecebido);
			if (receita != null) {
				System.out.println("Listando dados da receita com código = " + receita.getCodigo() + ":");
				System.out.println("Codigo:" + receita.getCodigo() + "\tNome:" + receita.getNome() + "\tAutor: " + receita.getAutor().getNome() + "\tData de Criação: " + receita.getDataCriacaoFormatada() + "\tModo de Preparo: " + receita.getModo_preparo());
			} else {
				System.out.println("Código não existe");
			}
		} catch (DAOException e) {
			System.out.println("Prezado usuário, infelizmente ocorreu um erro ao processar sua requisição.");
			e.printStackTrace(logErro);
			logErro.flush();
		}
	}

	private void abrirListagemReceitas() {
		ReceitaDAO receitaDAO = new ReceitaDAO();
		try {
			List<Receita> listaReceitas = receitaDAO.listeTodasReceitas();
			if (listaReceitas.isEmpty()) {
				System.err.println("Não há registros");
			} else {
				System.out.println("Listando dados das receitas cadastradas:");
				for (Receita receita : listaReceitas) {
					System.out.println("Codigo:" + receita.getCodigo() + "\tNome:" + receita.getNome() + "\tData Criação:" + receita.getDataCriacaoFormatada() + "\tCodigo Autor:" + receita.getAutor().getCodigo() + "\tModo preparo:" + receita.getModo_preparo());
				}
			}
		} catch (DAOException e) {
			System.out.println("Prezado usuário, infelizmente ocorreu um erro ao processar sua requisição.");
			e.printStackTrace(logErro);
			logErro.flush();
		}
	}

	private void abrirCadastroReceita() {
		String nome = ui.facaPergunta("Digite o nome da receita");
		Date dataAtual = new Date();

		Integer cd_autor = ui.facaPerguntaInt("Digite o código do autor da receita");
		Autor autor = new Autor(cd_autor, null);

		String modoPreparo = ui.facaPergunta("Digite o modo de preparo");

		Receita receita = new Receita(null, nome, dataAtual, modoPreparo, autor);
		ReceitaDAO receitaeDAO = new ReceitaDAO();
		try {
			receitaeDAO.cadastreReceita(receita);
			System.out.println("Receita cadastrada com sucesso");
		} catch (DAOException e) {
			System.err.println("Prezado usuário, infelizmente occoreu um erro ao processar a sua requisição.");
			e.printStackTrace(logErro);
			logErro.flush();
		}
	}

	private void deletaReceita() {
		ReceitaDAO receitaDAO = new ReceitaDAO();
		int codigoRecebido = ui.facaPerguntaInt("Digite o codigo da receita para deletar");
		try {
			Receita receita = new Receita(codigoRecebido, null, null, null, null);
			receitaDAO.deletaReceita(receita);
			System.out.println("Receita deletada com sucesso");
		} catch (DAOException e) {
			System.out.println("Prezado usuário, infelizmente ocorreu um erro ao processar sua requisição.");
			e.printStackTrace(logErro);
			logErro.flush();
		}
	}
}
