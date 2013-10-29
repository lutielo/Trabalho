package br.unisul.util;

public class StringUtils {
	
	public static final String NULL = "null";
	public static final String BRANCO = "";
	
	public static boolean isNuloOuBranco(Object objeto) {
		return objeto == null || objeto.toString().trim().equals(BRANCO) || objeto.toString().trim().equals(NULL);
	}
}
