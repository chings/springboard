package springboard.redis.annotation;

import org.springframework.context.annotation.Import;
import springboard.redis.RedisEventSubscriberConfig;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@Import(RedisEventSubscriberConfig.class)
public @interface EnableRedisEventSubscriber { }
