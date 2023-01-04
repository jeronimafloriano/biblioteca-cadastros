package cadastros.documentoidentificacao.domain.model;


import cadastros.documentoidentificacao.exception.CNPJSomenteZerosException;

import javax.validation.constraints.NotBlank;

public final class CNPJ implements DocumentoIdentificacao {

	@NotBlank(message = "{CNPJ.numero.NotBlank}")
	@br.com.caelum.stella.bean.validation.CNPJ(message = "{CNPJ.numero.CNPJ}")
	private String numero;

	private CNPJ(String numero) {
		this.numero = numero;

		if (cnpjSomenteComZeros(numero))
			throw new CNPJSomenteZerosException(numero);
	}

	public static CNPJ from(String numero) {
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