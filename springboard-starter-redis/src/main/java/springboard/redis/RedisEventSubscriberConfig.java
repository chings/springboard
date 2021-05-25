package springboard.redis;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
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
import org.springframework.data.redis.stream.Subscription;

import java.util.UUID;

@Configuration
public class RedisEventSubscriberConfig implements InitializingBean, DisposableBean {

    @Autowired
    RedisConnectionFactory redisConnectionFactory;

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Value("${redis.event-subscriber.topic}")
    String topic;

    @Value("${redis.event-subscriber.group}")
    String group;

    private StreamMessageListenerContainer<String, MapRecord<String, String, String>> listenerContainer;
    private Subscription subscription;

    @Bean
    @ConditionalOnMissingBean
    ObjectMapper objectMapper() {
        return new ObjectMapper();
    }

    @Bean
    @ConfigurationProperties("redis.event-subscriber")
    RedisEventSubscriber eventSubscriber() {
        return new RedisEventSubscriber(redisTemplate, objectMapper());
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        boolean groupExists = redisTemplate.opsForStream().groups(topic).stream().anyMatch(g -> g.groupName().equalsIgnoreCase(group));
        if(!groupExists) redisTemplate.opsForStream().createGroup(topic, group);
        listenerContainer = StreamMessageListenerContainer.create(this.redisConnectionFactory);
        subscription = listenerContainer.receive(Consumer.from(group, UUID.randomUUID().toString()),
                StreamOffset.create(topic, ReadOffset.lastConsumed()),
                eventSubscriber());
        listenerContainer.start();
    }

    @Override
    public void destroy() throws Exception {
        listenerContainer.remove(subscription);
        listenerContainer.stop();
    }

}
