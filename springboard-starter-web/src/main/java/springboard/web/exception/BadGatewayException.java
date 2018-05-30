package springboard.web.exception;

public class BadGatewayException extends RuntimeException {

    public BadGatewayException() { }

    public BadGatewayException(String message) {
        super(message);
    }

}
