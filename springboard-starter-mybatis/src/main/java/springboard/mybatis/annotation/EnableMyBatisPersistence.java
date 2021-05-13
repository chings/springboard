package springboard.mybatis.annotation;

import org.apache.ibatis.annotations.Mapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@MapperScan(annotationClass= Mapper.class)
public @interface EnableMyBatisPersistence {

    @AliasFor(annotation = MapperScan.class)
    String[] value() default {};

    @AliasFor(annotation = MapperScan.class)
    String[] basePackages() default {};

    @AliasFor(annotation = MapperScan.class)
    Class<?>[] basePackageClasses() default {};

}
