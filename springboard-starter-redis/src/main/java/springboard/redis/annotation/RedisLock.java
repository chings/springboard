package springboard.redis.annotation;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface RedisLock {

    String value() default "redis-lock";
    long timeout() default 30000;

}