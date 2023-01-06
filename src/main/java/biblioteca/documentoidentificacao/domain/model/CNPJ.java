package biblioteca.documentoidentificacao.domain.model;

import biblioteca.documentoidentificacao.exception.CNPJSomenteZerosException;
import biblioteca.documentoidentificacao.exception.DocumentoIdentificacaoInvalidoException;
import br.com.caelum.stella.validation.CNPJValidator;
import br.com.caelum.stella.validation.InvalidStateException;

import javax.validation.constraints.NotBlank;


public final class CNPJ implements DocumentoIdentificacao {

	@NotBlank(message = "{CNPJ.numero.NotBlank}")
	private String numero;

	private CNPJ(String numero) {
		this.numero = numero;

		if (cnpjSomenteComZeros(numero))
			throw new CNPJSomenteZerosException(numero);
	}

	public static CNPJ from(String numero) {
		CNPJValidator validator = new CNPJValidator();

		try {
			validator.assertValid(numero);
		} catch (InvalidStateException e){
			throw new DocumentoIdentificacaoInvalidoException("Documento inválido: "
					+ numero + " Informe um CPF(11 dígitos) ou CNPJ(14 dígitos) válido." + e);
		}

		return new CNPJ(numero);
	}

	@Override
	public TipoDocumentoIdentificacao getTipo() {
		return TipoDocumentoIdentificacao.CNPJ;
	}

	@Override
	public String getNumero() {
		return this.numero;
	}

	@Override
	public boolean isPessoaJuridica() {
		return true;
	}

	private boolean cnpjSomenteComZeros(String cnpj) {
		return cnpj.chars().allMatch(c -> c == (char) 48);
	}
}