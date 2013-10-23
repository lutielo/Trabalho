package br.unisul.dados;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.List;

import br.gov.geracaotecsc.ui.console.GenericConsoleUI;
import br.unisul.dao.AutorDAO;
import br.unisul.dao.DAOException;

public class ControleAutorApp {

	private GenericConsoleUI ui;
	private PrintWriter logErro;

	public static void main(String[] args) {
		new ControleAutorApp().run();
	}

	public ControleAutorApp() {
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
			opcao = ui.facaPerguntaInt("\n************  MENU  AUTOR ***************" + "\n*1-Cadastrar Autor\t\t\t*"
				+ "\n*2-Listar todos autores\t\t\t*" + "\n*3-Listar autores pelo nome\t\t*" + "\n*4-Listar autor pelo codigo\t\t*"
				+ "\n*5-Deletar autor \t\t\t*" + "\n*6-Altera - n funfa \t\t\t*" + "\n*7-Voltar ao menu principal\t\t*"
				+ "\n*****************************************");
			if (opcao == 1) {
				abrirCadastroAutor();
			} else if (opcao == 2) {
				abrirListagemAutores();
			} else if (opcao == 3) {
				String nomeRecebido = ui.facaPergunta("Digite o nome do autor para pesquisa");
				listarDadosDoAutorPeloNome(nomeRecebido);
			} else if (opcao == 4) {
				Integer codigoRecebido = ui.facaPerguntaInt("Digite o codigo do autor para pesquisa");
				listarDadosDoAutorPeloCodigo(codigoRecebido);
			} else if (opcao == 5) {
				deletaAutor();
			} else if (opcao == 6) {
				// TODO
			} else if (opcao != 7) {
				System.out.println("Opção invalida, tente novamente");
			}
		}
	}

	private void listarDadosDoAutorPeloNome(String nomeRecebido) {
		AutorDAO autorDAO = new AutorDAO();
		try {
			List<Autor> listaAutores = autorDAO.listeTodosAutoresPeloNome(nomeRecebido);
			if (listaAutores.isEmpty()) {
				System.err.println("Não há registros");
			} else {
				System.out.println("Listando dados dos autores cadastrados:");
				for (Autor autor : listaAutores) {
					System.out.println("Codigo:" + autor.getCodigo() + "\tNome:" + autor.getNome());
				}
			}
		} catch (DAOException e) {
			System.out.println("Prezado usuário, infelizmente ocorreu um erro ao processar sua requisição.");
			e.printStackTrace(logErro);
			logErro.flush();
		}
	}

	private void listarDadosDoAutorPeloCodigo(Integer codigoRecebido) {
		AutorDAO autorDAO = new AutorDAO();
		try {
			Autor autor = autorDAO.listeDadosDoAutorPeloCodigo(codigoRecebido);
			if (autor != null) {
				System.out.println("Listando dados do autor com código = " + autor.getCodigo() + ":");
				System.out.println("Codigo:" + autor.getCodigo() + "\tNome:" + autor.getNome());
			} else {
				System.out.println("Código não existe");
			}
		} catch (DAOException e) {
			System.out.println("Prezado usuário, infelizmente ocorreu um erro ao processar sua requisição.");
			e.printStackTrace(logErro);
			logErro.flush();
		}
	}

	private void abrirListagemAutores() {
		AutorDAO autorDAO = new AutorDAO();
		try {
			List<Autor> listaAutores = autorDAO.listeTodosAutores();
			if (listaAutores.isEmpty()) {
				System.err.println("Não há registros");
			} else {
				System.out.println("Listando dados dos autores cadastrados:");
				for (Autor autor : listaAutores) {
					System.out.println("Codigo:" + autor.getCodigo() + "\tNome:" + autor.getNome());
				}
			}
		} catch (DAOException e) {
			System.out.println("Prezado usuário, infelizmente ocorreu um erro ao processar sua requisição.");
			e.printStackTrace(logErro);
			logErro.flush();
		}
	}

	private void abrirCadastroAutor() {
		String nome = ui.facaPergunta("Digite o nome do Autor");

		Autor autor = new Autor(null, nome);
		AutorDAO autorDAO = new AutorDAO();
		try {
			autorDAO.cadastreAutor(autor);
			System.out.println("Autor cadastrado com sucesso");
		} catch (DAOException e) {
			System.err.println("Prezado usuário, infelizmente occoreu um erro ao processar a sua requisição.");
			e.printStackTrace(logErro);
			logErro.flush();
		}
	}

	private void deletaAutor() {
		AutorDAO autorDAO = new AutorDAO();
		int codigoRecebido = ui.facaPerguntaInt("Digite o codigo do autor para deletar");
		try {
			Autor autor = new Autor(codigoRecebido, null);
			listarDadosDoAutorPeloCodigo(codigoRecebido);
			int opcao = ui.facaPerguntaInt("Deseja Excluir esse registro? 1-Excluir 2-Cancelar");
			if (opcao == 1) {
				autorDAO.deletaAutor(autor);
				System.out.println("Autor deletado com sucesso");
			} else if (opcao == 2) {
				System.out.println("Cancelado com sucesso");
			}
		} catch (DAOException e) {
			System.out.println("Prezado usuário, infelizmente ocorreu um erro ao processar sua requisição.");
			e.printStackTrace(logErro);
			logErro.flush();
		}
	}
}
