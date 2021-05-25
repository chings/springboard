package springboard.dubbo.annotation;

import org.springframework.context.annotation.Import;
import springboard.dubbo.config.DisableDubboRPCConfig;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@Import(DisableDubboRPCConfig.class)
public @interface DisableDubboRPC {

}
