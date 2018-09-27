package springboard.shiro.annotation;

import org.springframework.context.annotation.Import;
import springboard.shiro.ShiroJsonSecurityConfig;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@Import(ShiroJsonSecurityConfig.class)
public @interface EnableShiroJsonSecurity {
}
