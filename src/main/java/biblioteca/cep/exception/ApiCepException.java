package biblioteca.cep.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ApiCepException extends RuntimeException {

    private static final long serialVersionUID = 6234645947541731503L;

    public ApiCepException(String message) {
        super(message);
    }

}
