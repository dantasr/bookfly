package utilidades;

import java.io.File;
import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class Validador {	
	public void validaArquivo(File f, String rotuloCampo) throws ValidacaoException {
		if(f == null)throw new ValidacaoException("O arquivo " + rotuloCampo + " deve ser escolhido.");
	}
	
	
	public String validaNaoVazio(String txt, int tamanhoMax,
			String rotuloCampo) throws ValidacaoException {
		if (txt == null) {
			throw new ValidacaoException("O campo " + rotuloCampo
					+ " deve ser preenchido.");
		}

		txt = txt.trim();

		if (txt.length() == 0) {
			throw new ValidacaoException("O campo " + rotuloCampo
					+ " deve ser preenchido.");
		}

		if (txt.length() > tamanhoMax) {
			throw new ValidacaoException("O campo " + rotuloCampo
					+ " deve conter no máximo " + tamanhoMax + " caracteres.");
		}

		return txt;
	}

	public int validaFormataInteiro(String txt, String rotuloCampo)
			throws ValidacaoException {
		if (txt == null) {
			throw new ValidacaoException("O campo " + rotuloCampo
					+ " deve ser preenchido.");
		}

		txt = txt.trim().toUpperCase();

		if (txt.length() == 0) {
			throw new ValidacaoException("O campo " + rotuloCampo
					+ " deve ser preenchido.");
		}

		int i;

		try {
			i = Integer.parseInt(txt);
		} catch (NumberFormatException e) {
			throw new ValidacaoException("Erro na digitação do campo " + rotuloCampo
					+ ". \n" + e.getMessage());
		}

		return i;
	}
	
	public int validaFormataInteiroPositivo(String txt, String rotuloCampo) throws ValidacaoException {
		int valor = validaFormataInteiro(txt, rotuloCampo);
		
		if (valor <= 0) {
			throw new ValidacaoException("O campo " + rotuloCampo + " deve ser maior que 0!");
		}
		
		return valor;
	}
	
	public Date validaData(String txt, String rotuloCampo) throws ValidacaoException {
		try {
			DateFormat fmt = new SimpleDateFormat("dd/MM/yyyy");
			return new Date(fmt.parse(txt).getTime());
		} catch (Exception e) {
			throw new ValidacaoException("Erro no campo " + rotuloCampo + ": Insira a data no formato DD/MM/YY");
		}
	}
}
