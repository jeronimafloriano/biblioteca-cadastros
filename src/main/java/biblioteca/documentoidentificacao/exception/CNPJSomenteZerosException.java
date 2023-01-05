package biblioteca.documentoidentificacao.exception;

public class CNPJSomenteZerosException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	private final String numeroDocumento;

	public CNPJSomenteZerosException(String numeroDocumento) {
		this.numeroDocumento = numeroDocumento;
	}

}