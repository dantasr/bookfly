package arquivos;

import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

import javax.imageio.ImageIO;

import beans.Livro;
import beans.Usuario;
import utilidades.Log;

public class GerenciadorArquivos {
	private final String caminhoPastaImagensLivro = "imagens/livro";
	private final String caminhoPastaImagensUsuario = "imagens/usuario";
	private final String caminhoPastaPDFLivro = "pdfs";

	private void criaPastaLivro() {
		File pastaImagem = new File(caminhoPastaImagensLivro);
		pastaImagem.mkdirs();
	}

	private void criaPastaUsuario() {
		File pastaUsuario = new File(caminhoPastaImagensUsuario);
		pastaUsuario.mkdirs();
	}

	private void criaPastaPDFLivro() {
		File pastaPDFLivro = new File(caminhoPastaPDFLivro);
		pastaPDFLivro.mkdirs();
	}

	public GerenciadorArquivos() {
		criaPastaLivro();
		criaPastaUsuario();
		criaPastaPDFLivro();
	}
	
	public Image carregarImagem(String caminho) {
		Image imagem = null;

		try {
			File imagemFile = new File(caminho);
			imagem = ImageIO.read(imagemFile);
		} catch (IOException e) {
			Log.gravaLog(e);
		}

		return imagem;
	}

	private String obterCaminhoImagemLivro(Livro livro) {
		return caminhoPastaImagensLivro + "/" + livro.getCodigo();
	}

	private String obterCaminhoPdfLivro(Livro livro) {
		return caminhoPastaPDFLivro + "/" + livro.getCodigo() + ".pdf";
	}

	private String obterCaminhoImagemUsuario(Usuario usuario) {
		return caminhoPastaImagensUsuario + "/" + usuario.getCodigo();
	}

	public Image carregarImagemDoLivro(Livro livro) {
		return carregarImagem(obterCaminhoImagemLivro(livro));
	}

	public Image carregarImagemDoUsuario(Usuario usuario) {
		return carregarImagem(obterCaminhoImagemUsuario(usuario));
	}

	public void salvarImagemDoLivro(Livro livro, File imagem) throws IOException {
		String destino = obterCaminhoImagemLivro(livro);

		Files.copy(imagem.toPath(), new File(destino).toPath(), StandardCopyOption.REPLACE_EXISTING);
	}

	public void salvarImagemDoUsuario(Usuario usuario, File imagem) throws IOException {
		String destino = obterCaminhoImagemUsuario(usuario);

		Files.copy(imagem.toPath(), new File(destino).toPath(), StandardCopyOption.REPLACE_EXISTING);
	}

	public File obterArquivoPdf(Livro livro) {
		return new File(obterCaminhoPdfLivro(livro));
	}

	public void salvarArquivoPdf(Livro livro, File pdf) throws IOException {
		String destino = obterCaminhoPdfLivro(livro);

		Files.copy(pdf.toPath(), new File(destino).toPath(), StandardCopyOption.REPLACE_EXISTING);
	}
}
