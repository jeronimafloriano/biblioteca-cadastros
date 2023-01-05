package biblioteca.documentoidentificacao.domain.model;

import biblioteca.documentoidentificacao.exception.DocumentoIdentificacaoInvalidoException;

public interface DocumentoIdentificacao {

	public TipoDocumentoIdentificacao getTipo();

	public String getNumero();

	default boolean isPessoaFisica() {
		return false;
	}

	default boolean isPessoaJuridica() {
		return false;
	}

	public static DocumentoIdentificacao from(String documento) {

		if (documento == null)
			throw new DocumentoIdentificacaoInvalidoException(documento);

		if (documento.length() == 11)
			return CPF.from(documento);

		if (documento.length() == 14)
			return CNPJ.from(documento);

		throw new DocumentoIdentificacaoInvalidoException(documento);

	}

}