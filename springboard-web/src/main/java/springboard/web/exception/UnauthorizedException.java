package springboard.web.exception;

import org.springframework.http.HttpStatus;

public class UnauthorizedException extends RuntimeException {

    public UnauthorizedException() { }

    public UnauthorizedException(String message) {
        super(message);
    }

}
