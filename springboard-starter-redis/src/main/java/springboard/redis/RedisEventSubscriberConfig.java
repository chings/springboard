package springboard.redis;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.stream.Consumer;
import org.springframework.data.redis.connection.stream.MapRecord;
import org.springframework.data.redis.connection.stream.ReadOffset;
import org.springframework.data.redis.connection.stream.StreamOffset;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.stream.StreamMessageListenerContainer;

import java.util.UUID;

@Configuration
public class RedisEventSubscriberConfig implements InitializingBean, DisposableBean {

    @Autowired
    RedisConnectionFactory redisConnectionFactory;

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    ObjectMapper objectMapper;

    @Value("${redis.event-subscriber.topic}")
    String topic;

    @Value("${redis.event-subscriber.group}")
    String group;

    StreamMessageListenerContainer<String, MapRecord<String, String, String>> listenerContainer;

    @Bean
    @ConfigurationProperties("redis.event-subscriber")
    RedisEventSubscriber eventSubscriber() {
        return new RedisEventSubscriber(redisTemplate, objectMapper);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        listenerContainer = StreamMessageListenerContainer.create(this.redisConnectionFactory);
        listenerContainer.receive(Consumer.from(group, UUID.randomUUID().toString()),
                StreamOffset.create(topic, ReadOffset.lastConsumed()),
                eventSubscriber());
        listenerContainer.start();
    }

    @Override
    public void destroy() throws Exception {
        listenerContainer.stop();
    }

}
