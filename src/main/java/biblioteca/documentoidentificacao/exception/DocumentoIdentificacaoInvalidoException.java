package biblioteca.documentoidentificacao.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class DocumentoIdentificacaoInvalidoException extends RuntimeException {

	private static final long serialVersionUID = -8703432311507014651L;

	private final String numeroDocumento;

	public DocumentoIdentificacaoInvalidoException(String numeroDocumento) {
		this.numeroDocumento = numeroDocumento;
	}

}