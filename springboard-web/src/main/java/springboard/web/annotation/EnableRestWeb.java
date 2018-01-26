package springboard.web.annotation;

import org.springframework.context.annotation.Import;
import springboard.web.RestWebConfig;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(RestWebConfig.class)
public @interface EnableRestWeb { }
