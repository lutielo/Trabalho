package br.unisul.dados;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.List;

import br.gov.geracaotecsc.ui.console.GenericConsoleUI;
import br.unisul.dao.DAOException;
import br.unisul.dao.UnidadeDAO;

public class ControleUnidadeApp {

	private GenericConsoleUI ui;
	private PrintWriter logErro;

	public static void main(String[] args) {
		new ControleUnidadeApp().run();
	}

	public ControleUnidadeApp() {
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
					("\n************  MENU  UNIDADE *************"
					+ "\n*Oque voce quer fazer?\t\t\t*"
					+ "\n*1-Cadastrar Unidade\t\t\t*"
					+ "\n*2-Listar todas unidades\t\t*"
					+ "\n*3-Listar unidades pelo nome\t\t*"
					+ "\n*4-Listar unidades pelo código\t\t*"
					+ "\n*5-Deleta unidade \t\t\t*"
					+ "\n*6-Altera - n funfa \t\t\t*"
					+ "\n*7-Voltar ao menu principal\t\t*"
					+ "\n*****************************************");;
			if (opcao == 1) {
				abrirCadastroUnidade();
			} else if (opcao == 2) {
				abrirListagemUnidades();
			} else if (opcao == 3) {
				listarDadosUnidadePeloNome();
			} else if (opcao == 4) {
				listarDadosUnidadePeloCodigo();
			} else if (opcao == 5) {
				deletaUnidade();
			}else if (opcao == 6) {
				
			} else if (opcao != 7) {
				System.out.println("Opção invalida, tente novamente");
			}
		}
	}

	private void listarDadosUnidadePeloNome() {
		UnidadeDAO unidadeDAO = new UnidadeDAO();
		String nomeRecebido = ui.facaPergunta("Digite o nome da unidade para pesquisa");
		try {
			List<Unidade> listaUnidades = unidadeDAO.listeTodasUnidadesPeloNome(nomeRecebido);
			if (listaUnidades.isEmpty()) {
				System.err.println("Não há registros");
			} else {
				System.out.println("Listando dados das unidades cadastradas:");
				for (Unidade unidade : listaUnidades) {
					System.out.println("Codigo:" + unidade.getCodigo() + "\tTipo da unidade:" + unidade.getTipo());
				}
			}
		} catch (DAOException e) {
			System.out.println("Prezado usuário, infelizmente ocorreu um erro ao processar sua requisição.");
			e.printStackTrace(logErro);
			logErro.flush();
		}
	}

	private void listarDadosUnidadePeloCodigo() {
		UnidadeDAO unidadeDAO = new UnidadeDAO();
		int codigoRecebido = ui.facaPerguntaInt("Digite a código da unidade para pesquisa");
		try {
			Unidade unidade = unidadeDAO.listeDadosDaUnidadePeloCodigo(codigoRecebido);
			if (unidade != null) {
				System.out.println("Listando dados da unidade com código " + unidade.getCodigo() + ":");
				System.out.println("Codigo:" + unidade.getCodigo() + "\tTipo da unidade:" + unidade.getTipo());
			} else {
				System.out.println("Código não existe");
			}
		} catch (DAOException e) {
			System.out.println("Prezado usuário, infelizmente ocorreu um erro ao processar sua requisição.");
			e.printStackTrace(logErro);
			logErro.flush();
		}
	}

	private void abrirListagemUnidades() {
		UnidadeDAO unidadeDAO = new UnidadeDAO();
		try {
			List<Unidade> listaUnidades = unidadeDAO.listeTodasUnidades();
			if (listaUnidades.isEmpty()) {
				System.err.println("Não há registros");
			} else {
				System.out.println("Listando dados das unidades cadastradas:");
				for (Unidade unidade : listaUnidades) {
					System.out.println("Codigo:" + unidade.getCodigo() + "\tTipo da unidade:" + unidade.getTipo());
				}
			}
		} catch (DAOException e) {
			System.out.println("Prezado usuário, infelizmente ocorreu um erro ao processar sua requisição.");
			e.printStackTrace(logErro);
			logErro.flush();
		}
	}

	private void abrirCadastroUnidade() {
		String tipo_unidade = ui.facaPergunta("Digite o nome do tipo da unidade");

		Unidade unidade = new Unidade(null, tipo_unidade);
		UnidadeDAO unidadeDAO = new UnidadeDAO();
		try {
			unidadeDAO.cadastreUnidade(unidade);
			System.out.println("Unidade cadastrado com sucesso");
		} catch (DAOException e) {
			System.err.println("Prezado usuário, infelizmente occoreu um erro ao processar a sua requisição.");
			e.printStackTrace(logErro);
			logErro.flush();
		}
	}

	private void deletaUnidade() {
		UnidadeDAO unidadeDAO = new UnidadeDAO();
		int codigoRecebido = ui.facaPerguntaInt("Digite o codigo da unidade para deletar");
		try {
			Unidade unidade = new Unidade(codigoRecebido, null);
			unidadeDAO.deletaUnidade(unidade);
			System.out.println("Unidade deletada com sucesso");
		} catch (DAOException e) {
			System.out.println("Prezado usuário, infelizmente ocorreu um erro ao processar sua requisição.");
			e.printStackTrace(logErro);
			logErro.flush();
		}
	}
}
