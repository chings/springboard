package springboard.redis;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.core.StringRedisTemplate;
import springboard.lang.EventPublisher;

public class RedisEventPublisherConfig {

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    ObjectMapper objectMapper;

    @Bean
    @ConfigurationProperties("redis.event-publisher")
    EventPublisher eventPublisher() {
        return new RedisEventPublisher(redisTemplate, objectMapper);
    }

}
