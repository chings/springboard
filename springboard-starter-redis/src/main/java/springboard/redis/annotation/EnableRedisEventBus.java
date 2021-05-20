package springboard.redis.annotation;

import org.springframework.context.annotation.Import;
import springboard.redis.RedisEventPublisherConfig;
import springboard.redis.RedisEventSubscriberConfig;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@Import({ RedisEventPublisherConfig.class, RedisEventSubscriberConfig.class })
public @interface EnableRedisEventBus {
}
