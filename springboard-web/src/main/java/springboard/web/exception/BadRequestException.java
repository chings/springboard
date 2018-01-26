package springboard.web.exception;

import org.springframework.http.HttpStatus;

public class BadRequestException extends RuntimeException {

    public BadRequestException() { }

    public BadRequestException(String message) {
        super(message);
    }

}
