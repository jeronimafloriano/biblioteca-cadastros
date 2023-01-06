package biblioteca.cep.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class CepException extends RuntimeException {

    private static final long serialVersionUID = 6234645947541731503L;

    private final String cep;

    public CepException(String cep) {
        this.cep = cep;
    }

}
