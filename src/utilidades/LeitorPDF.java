package utilidades;

import java.io.File;
import java.io.IOException;

public class LeitorPDF {
	private static final String EXECUTAVEL_LEITOR = "SumatraPDF.exe";
	
	private File caminhoPdf;
	private Process processoLeitor = null;
	
	public LeitorPDF(File caminhoPdf) {
		this.caminhoPdf = caminhoPdf;
	}
	
	public void exibir() throws IOException {
		if (processoLeitor != null) return;
		
		processoLeitor = Runtime.getRuntime().exec(new String[] { EXECUTAVEL_LEITOR, caminhoPdf.getAbsolutePath() });
	}
}
