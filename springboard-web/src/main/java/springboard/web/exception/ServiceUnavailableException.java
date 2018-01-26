package springboard.web.exception;

import org.springframework.http.HttpStatus;

public class ServiceUnavailableException extends RuntimeException {

    public ServiceUnavailableException() { }

    public ServiceUnavailableException(String message) {
        super(message);
    }

}
