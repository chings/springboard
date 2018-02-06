package springboard.web.annotation;

import org.springframework.context.annotation.Import;
import springboard.web.RestWebMVCConfig;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(RestWebMVCConfig.class)
public @interface EnableRestWebMVC { }
