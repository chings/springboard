package springboard.swagger.annotation;

import org.springframework.context.annotation.Import;
import springboard.swagger.SwaggerDocumentationConfig;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@Import(SwaggerDocumentationConfig.class)
public @interface EnableSwaggerDocumentation { }
