package springboard.web.exception;

import org.springframework.http.HttpStatus;

public class InternalServerErrorException extends RuntimeException {

    public InternalServerErrorException() { }

    public InternalServerErrorException(String message) {
        super(message);
    }

}
