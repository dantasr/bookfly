package utilidades;

import java.awt.Image;
import javax.swing.JLabel;
import java.io.File;
import java.net.MalformedURLException;

import javax.swing.ImageIcon;

public class IconeLabel {
	public static void colocarImagemNoLabel(JLabel lbl, File imagem) throws MalformedURLException {
		ImageIcon iconeOriginal = new ImageIcon(imagem.toURI().toURL());
		int w = lbl.getWidth();
		int h = lbl.getHeight();
		
		ImageIcon iconeScaled = new ImageIcon(iconeOriginal.getImage().getScaledInstance(w, h, Image.SCALE_SMOOTH));
		lbl.setIcon(iconeScaled);
	}
	
	public static void colocarImagemNoLabel(JLabel lbl, Image imagem) throws MalformedURLException {
		ImageIcon iconeOriginal = new ImageIcon(imagem);
		int w = lbl.getWidth();
		int h = lbl.getHeight();
		
		ImageIcon iconeScaled = new ImageIcon(iconeOriginal.getImage().getScaledInstance(w, h, Image.SCALE_SMOOTH));
		lbl.setIcon(iconeScaled);
	}
}
