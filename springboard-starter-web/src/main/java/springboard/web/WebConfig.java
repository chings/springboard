package springboard.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.web.server.ConfigurableWebServerFactory;
import org.springframework.boot.web.server.ErrorPage;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import springboard.databind.ObjectMappers;
import springboard.web.exception.*;

@Configuration
@ControllerAdvice
public class WebConfig {

    private static final Logger log = LoggerFactory.getLogger(WebConfig.class);

    @Bean
    @ConditionalOnMissingBean
    public ObjectMapper objectMapper() {
        return ObjectMappers.generic();
    }

    @Bean
    @ConditionalOnExpression("T(org.springframework.util.StringUtils).hasText('${springboard.web.router-page:}')")
    public WebServerFactoryCustomizer containerCustomizer(Environment env) {
        String routerPage = env.getProperty("springboard.web.router-page");
        return new WebServerFactoryCustomizer<ConfigurableWebServerFactory>() {
            @Override
            public void customize(ConfigurableWebServerFactory container) {
                ErrorPage errorPage = new ErrorPage(HttpStatus.NOT_FOUND, routerPage);
                container.addErrorPages(errorPage);
                errorPage = new ErrorPage(HttpStatus.METHOD_NOT_ALLOWED, routerPage);
                container.addErrorPages(errorPage);
            }
        };
    }

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
