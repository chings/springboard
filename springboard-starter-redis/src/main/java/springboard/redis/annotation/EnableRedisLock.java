package springboard.redis.annotation;

import org.springframework.context.annotation.Import;
import springboard.redis.RedisLockConfig;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@Import(RedisLockConfig.class)
public @interface EnableRedisLock {
}
