package cartoes;

public class CartaoSemSaldoException extends Exception {

	public CartaoSemSaldoException() {
		super();
	}

	public CartaoSemSaldoException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public CartaoSemSaldoException(String message, Throwable cause) {
		super(message, cause);
	}

	public CartaoSemSaldoException(String message) {
		super(message);
	}

	public CartaoSemSaldoException(Throwable cause) {
		super(cause);
	}
}
