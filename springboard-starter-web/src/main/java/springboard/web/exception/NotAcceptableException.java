package springboard.web.exception;

import org.springframework.http.HttpStatus;

public class NotAcceptableException extends RuntimeException {

    public NotAcceptableException() { }

    public NotAcceptableException(String message) {
        super(message);
    }

}
