package utilidades;

import java.io.File;
import java.io.IOException;

public class LeitorPDF {
	private File caminhoPdf;
	private Process processoLeitor = null;
	
	public LeitorPDF(File caminhoPdf) {
		this.caminhoPdf = caminhoPdf;
	}
	
	private boolean isWindows() {
	    String os = System.getProperty("os.name").toLowerCase();
	    return os.indexOf("windows") != -1 || os.indexOf("nt") != -1;
	}
	private boolean isMac() {
	    String os = System.getProperty("os.name").toLowerCase();
	    return os.indexOf("mac") != -1;
	}
	
	public void exibir() throws IOException {
		if (processoLeitor != null) return;
		if (isWindows()) {
			processoLeitor = Runtime.getRuntime().exec(new String[] { "cmd.exe", "/C", caminhoPdf.getAbsolutePath() });
		}
		else if (isMac()) {
			processoLeitor = Runtime.getRuntime().exec(new String[] { "open", caminhoPdf.getAbsolutePath() });
		}
		else throw new RuntimeException();
	}
}
