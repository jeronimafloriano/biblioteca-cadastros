package biblioteca.documentoidentificacao.domain.model;


import biblioteca.documentoidentificacao.exception.DocumentoIdentificacaoInvalidoException;
import br.com.caelum.stella.validation.CPFValidator;
import br.com.caelum.stella.validation.InvalidStateException;

import javax.validation.constraints.NotBlank;

public final class CPF implements DocumentoIdentificacao {

	@NotBlank(message = "{CPF.numero.NotBlank}")
	private final String numero;

	private CPF(String numero) {
		this.numero = numero;

	}

	public static CPF from(String numero) {
		CPFValidator validator = new CPFValidator();

		try {
			validator.assertValid(numero);
		} catch (InvalidStateException e){
			throw new DocumentoIdentificacaoInvalidoException("Documento inválido: "
					+ numero + " Informe um CPF(11 dígitos) ou CNPJ(14 dígitos) válido." + e);
		}

		return new CPF(numero);
	}

	@Override
	public TipoDocumentoIdentificacao getTipo() {
		return TipoDocumentoIdentificacao.CPF;
	}

	@Override
	public String getNumero() {
		return this.numero;
	}

	@Override
	public boolean isPessoaFisica() {
		return true;
	}


}
