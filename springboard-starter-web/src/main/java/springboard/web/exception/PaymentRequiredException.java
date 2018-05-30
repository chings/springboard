package springboard.web.exception;

public class PaymentRequiredException extends RuntimeException {

    public PaymentRequiredException() { }

    public PaymentRequiredException(String message) {
        super(message);
    }

}
