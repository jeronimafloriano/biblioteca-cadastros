package biblioteca.documentoidentificacao.exception;

public class DocumentoIdentificacaoInvalidoException extends RuntimeException {

	private static final long serialVersionUID = -8703432311507014651L;

	private final String numeroDocumento;

	public DocumentoIdentificacaoInvalidoException(String numeroDocumento) {
		this.numeroDocumento = numeroDocumento;
	}

}