package springboard.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import springboard.web.exception.*;

@Configuration
@ControllerAdvice
public class RestWebMVCConfig {

    private static Logger log = LoggerFactory.getLogger(RestWebMVCConfig.class);

    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public @ResponseBody String handleException(BadRequestException x) {
        log.debug("{} was thrown", x.getClass(), x);
        return x.getMessage();
    }

    @ExceptionHandler(UnauthorizedException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public @ResponseBody String handleException(UnauthorizedException x) {
        log.debug("{} was thrown", x.getClass(), x);
        return x.getMessage();
    }

    @ExceptionHandler(PaymentRequiredException.class)
    @ResponseStatus(HttpStatus.PAYMENT_REQUIRED)
    public @ResponseBody String handleException(PaymentRequiredException x) {
        log.debug("{} was thrown", x.getClass(), x);
        return x.getMessage();
    }

    @ExceptionHandler(ForbiddenException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public @ResponseBody String handleException(ForbiddenException x) {
        log.debug("{} was thrown", x.getClass(), x);
        return x.getMessage();
    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public @ResponseBody String handleException(NotFoundException x) {
        log.debug("{} was thrown", x.getClass(), x);
        return x.getMessage();
    }

    @ExceptionHandler(MethodNotAllowedException.class)
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    public @ResponseBody String handleException(MethodNotAllowedException x) {
        log.debug("{} was thrown", x.getClass(), x);
        return x.getMessage();
    }

    @ExceptionHandler(NotAcceptableException.class)
    @ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
    public @ResponseBody String handleException(NotAcceptableException x) {
        log.debug("{} was thrown", x.getClass(), x);
        return x.getMessage();
    }

    @ExceptionHandler(InternalServerErrorException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public @ResponseBody String handleException(InternalServerErrorException x) {
        log.debug("{} was thrown", x.getClass(), x);
        return x.getMessage();
    }

    @ExceptionHandler(NotImplementedException.class)
    @ResponseStatus(HttpStatus.NOT_IMPLEMENTED)
    public @ResponseBody String handleException(NotImplementedException x) {
        log.debug("{} was thrown", x.getClass(), x);
        return x.getMessage();
    }

    @ExceptionHandler(BadGatewayException.class)
    @ResponseStatus(HttpStatus.BAD_GATEWAY)
    public @ResponseBody String handleException(BadGatewayException x) {
        log.debug("{} was thrown", x.getClass(), x);
        return x.getMessage();
    }

    @ExceptionHandler(ServiceUnavailableException.class)
    @ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
    public @ResponseBody String handleException(ServiceUnavailableException x) {
        log.debug("{} was thrown", x.getClass(), x);
        return x.getMessage();
    }

}
