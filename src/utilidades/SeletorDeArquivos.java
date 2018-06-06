package utilidades;

import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;

public class SeletorDeArquivos {
    public static String getExtension(File f) {
        String ext = null;
        String s = f.getName();
        int i = s.lastIndexOf('.');

        if (i > 0 &&  i < s.length() - 1) {
            ext = s.substring(i+1).toLowerCase();
        }
        return ext;
    }
	
	static class ExtensionFilter extends FileFilter {
		private String[] extensoesAceitas;
		private String descricao;
		
		public ExtensionFilter(String[] extensoes, String descricao) {
			this.extensoesAceitas = extensoes;
			this.descricao = descricao;
		}
		
		@Override
		public boolean accept(File pathname) {
			if (pathname.isDirectory()) return true;
			if (extensoesAceitas == null || extensoesAceitas.length == 0) return true;
			
			String extensao = getExtension(pathname);
			
			for (String extAceita : extensoesAceitas) {
				if (extAceita.equals(extensao)) return true;
			}
			
			return false;
		}

		@Override
		public String getDescription() {
			return descricao;
		}
		
	}
	
	/**
	 * Seleciona um arquivo com as extensões requisitadas.
	 * @param extensoes As extensões requeridas. Caso seja nulo ou vazio, aceita todas as extensões.
	 * @param descricaoDasExtensoes Uma descrição para as extensões.
	 * @return Um arquivo selecionado, ou null, caso o usuário não tenha selecionado nada.
	 */
	public static File selecionar(String[] extensoes, String descricaoDasExtensoes) {
		JFileChooser seletor = new JFileChooser();
		FileFilter filtro = new ExtensionFilter(extensoes, descricaoDasExtensoes);
		seletor.setFileFilter(filtro);
		int escolha = seletor.showOpenDialog(null);
		
		if (escolha == JFileChooser.APPROVE_OPTION) {
			return seletor.getSelectedFile();
		} else {
			return null;
		}
	}
}
