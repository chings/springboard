package springboard.shiro.annotation;

import org.springframework.context.annotation.Import;
import springboard.shiro.ShiroSecurityConfig;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(ShiroSecurityConfig.class)
public @interface EnableShiroSecurity { }
