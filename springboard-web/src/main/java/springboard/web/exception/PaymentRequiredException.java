package springboard.web.exception;

import org.springframework.http.HttpStatus;

public class PaymentRequiredException extends RuntimeException {

    public PaymentRequiredException() { }

    public PaymentRequiredException(String message) {
        super(message);
    }

}
