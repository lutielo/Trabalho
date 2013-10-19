package br.unisul.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {
	private static SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
	
	public static Date toDate(String strDate) {
		if (strDate == null) {
			return null;
		} else {
			try {
				return dateFormat.parse(strDate);
			} catch (ParseException e) {
				throw new RuntimeException("Erro ao parsear data", e);
			}			
		}
	}
	
	public static String toString(Date date) {
		if (date == null) {
			return null;
		} else {
			return dateFormat.format(date); 			
		}
	}
}
