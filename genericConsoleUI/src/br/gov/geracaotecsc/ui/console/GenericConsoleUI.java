package br.gov.geracaotecsc.ui.console;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;

public class GenericConsoleUI {

	private BufferedReader in;
	private SimpleDateFormat dateFormat;

	public GenericConsoleUI() {
		in = new BufferedReader(new InputStreamReader(System.in));
		dateFormat = new SimpleDateFormat("dd/MM/yyyy");
	}
	
	public String facaPergunta(String pergunta) {
		String resposta = null;
		try {
			System.out.println(pergunta);
			resposta = in.readLine();
		} catch (IOException e) {
			System.err.println("Ocorreu um erro ao ler os dados do usuário");
			e.printStackTrace();
		}
		return resposta;
	}

	public int facaPerguntaInt(String pergunta) {
		while (true) {
			String resposta = facaPergunta(pergunta);
			try {
				return Integer.parseInt(resposta);
			} catch (Exception e) {
				System.out.println("Valor invalido");
			}
		}
	}

	public double facaPerguntaDouble(String pergunta) {
		while (true) {
			String resposta = facaPergunta(pergunta);
			try {
				return Double.parseDouble(resposta);
			} catch (Exception e) {
				System.out.println("Valor invalido");
			}
		}
	}
	
	public Date facaPerguntaDate(String pergunta) {
		while (true) {
			String resposta = facaPergunta(pergunta);
			try {
				return dateFormat.parse(resposta);
			} catch (Exception e) {
				System.out.println("Valor invalido");
			}
		}
	}
}