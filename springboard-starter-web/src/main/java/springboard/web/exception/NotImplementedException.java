package springboard.web.exception;

import org.springframework.http.HttpStatus;

public class NotImplementedException extends RuntimeException {

    public NotImplementedException() { }

    public NotImplementedException(String message) {
        super(message);
    }

}
