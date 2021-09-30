package springboard.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;

@Configuration
public class RedisEventSubscriberConfig {

    @Autowired
    RedisConnectionFactory redisConnectionFactory;

    @Autowired
    StringRedisTemplate redisTemplate;

    @Bean
    @ConfigurationProperties("springboard.redis.event-subscriber")
    RedisEventSubscriber eventSubscriber() {
        return new RedisEventSubscriber(redisConnectionFactory, redisTemplate);
    }

}
