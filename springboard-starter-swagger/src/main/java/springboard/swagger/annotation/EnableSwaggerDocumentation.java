package springboard.swagger.annotation;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@EnableSwagger2
public @interface EnableSwaggerDocumentation { }
