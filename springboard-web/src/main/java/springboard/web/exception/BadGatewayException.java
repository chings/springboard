package springboard.web.exception;

import org.springframework.http.HttpStatus;

public class BadGatewayException extends RuntimeException {

    public BadGatewayException() { }

    public BadGatewayException(String message) {
        super(message);
    }

}
