package springboard.web.exception;

import org.springframework.http.HttpStatus;

public class ForbiddenException extends RuntimeException {

    public ForbiddenException() { }

    public ForbiddenException(String message) {
        super(message);
    }

}
