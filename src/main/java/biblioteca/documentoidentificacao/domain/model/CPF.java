package biblioteca.documentoidentificacao.domain.model;

import javax.validation.constraints.NotBlank;

public final class CPF implements DocumentoIdentificacao {

	@NotBlank(message = "{CPF.numero.NotBlank}")
	@br.com.caelum.stella.bean.validation.CPF(message = "{CPF.numero.CPF}")
	private final String numero;

	private CPF(String numero) {
		this.numero = numero;
	}

	public static CPF from(String numero) {
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
