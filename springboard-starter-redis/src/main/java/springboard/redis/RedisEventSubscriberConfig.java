package springboard.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;

@Configuration
public class RedisEventSubscriberConfig extends RedisCommonConfig {

    @Autowired
    RedisConnectionFactory redisConnectionFactory;

    @Bean
    @ConfigurationProperties("redis.event-subscriber")
    RedisEventSubscriber eventSubscriber() {
        return new RedisEventSubscriber(redisConnectionFactory, redisTemplate, objectMapper());
    }

}
