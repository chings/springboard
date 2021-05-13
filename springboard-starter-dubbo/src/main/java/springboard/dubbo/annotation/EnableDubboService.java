package springboard.dubbo.annotation;

import org.apache.dubbo.config.spring.context.annotation.DubboComponentScan;
import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@DubboComponentScan
public @interface EnableDubboService {

    @AliasFor(annotation = DubboComponentScan.class)
    String[] value() default {};

    @AliasFor(annotation = DubboComponentScan.class)
    String[] basePackages() default {};

    @AliasFor(annotation = DubboComponentScan.class)
    Class<?>[] basePackageClasses() default {};

}
