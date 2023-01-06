package biblioteca.documentoidentificacao.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class CNPJSomenteZerosException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	private final String numeroDocumento;

	public CNPJSomenteZerosException(String numeroDocumento) {
		this.numeroDocumento = numeroDocumento;
	}

}