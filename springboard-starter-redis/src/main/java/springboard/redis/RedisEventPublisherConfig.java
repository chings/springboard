package springboard.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.core.StringRedisTemplate;
import springboard.lang.EventPublisher;

public class RedisEventPublisherConfig {

    @Autowired
    StringRedisTemplate redisTemplate;

    @Bean
    @ConfigurationProperties("springboard.redis.event-publisher")
    EventPublisher eventPublisher() {
        return new RedisEventPublisher(redisTemplate);
    }

}
