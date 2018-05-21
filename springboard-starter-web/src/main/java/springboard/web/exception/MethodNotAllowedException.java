package springboard.web.exception;

import org.springframework.http.HttpStatus;

public class MethodNotAllowedException extends RuntimeException {

    public MethodNotAllowedException() { }

    public MethodNotAllowedException(String message) {
        super(message);
    }

}
