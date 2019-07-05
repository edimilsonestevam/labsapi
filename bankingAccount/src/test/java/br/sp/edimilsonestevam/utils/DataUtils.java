package br.sp.edimilsonestevam.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DataUtils {

	public static String getDataDiferencaDias(Integer quantidadeDias) {
		
		Calendar calendario = Calendar.getInstance();
		calendario.add(Calendar.DAY_OF_MONTH, quantidadeDias);
		return getDataFormatada(calendario.getTime());
		
	}
	
	public static String getDataFormatada(Date data) {
		
		DateFormat formatacao = new SimpleDateFormat("dd/MM/yyyy");
		return formatacao.format(data);
		
	}
}
