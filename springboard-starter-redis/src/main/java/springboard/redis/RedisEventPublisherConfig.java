package springboard.redis;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import springboard.lang.EventPublisher;

public class RedisEventPublisherConfig extends RedisCommonConfig {

    @Bean
    @ConfigurationProperties("redis.event-publisher")
    EventPublisher eventPublisher() {
        return new RedisEventPublisher(redisTemplate, objectMapper());
    }

}
