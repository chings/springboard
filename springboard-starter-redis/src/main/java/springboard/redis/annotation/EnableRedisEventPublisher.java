package springboard.redis.annotation;

import org.springframework.context.annotation.Import;
import springboard.redis.RedisEventPublisherConfig;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@Import(RedisEventPublisherConfig.class)
public @interface EnableRedisEventPublisher { }
