package springboard.mybatis.annotation;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Import;
import org.springframework.core.annotation.AliasFor;
import springboard.mybatis.MybatisPersistenceConfig;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@MapperScan
@Import(MybatisPersistenceConfig.class)
public @interface EnableMybatisPersistence {

    @AliasFor(annotation = MapperScan.class)
    String[] value() default {};

    @AliasFor(annotation = MapperScan.class)
    String[] basePackages() default {};

}
