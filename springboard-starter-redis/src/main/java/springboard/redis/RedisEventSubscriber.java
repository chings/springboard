package springboard.redis;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.stream.Consumer;
import org.springframework.data.redis.connection.stream.MapRecord;
import org.springframework.data.redis.connection.stream.ReadOffset;
import org.springframework.data.redis.connection.stream.StreamOffset;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.stream.StreamListener;
import org.springframework.data.redis.stream.StreamMessageListenerContainer;
import org.springframework.data.redis.stream.Subscription;
import org.springframework.util.StringUtils;
import springboard.databind.ObjectMappers;

import java.io.IOException;
import java.util.Map;
import java.util.UUID;

import static springboard.redis.RedisEventPublisher.EVENT_CLASS_KEY;
import static springboard.redis.RedisEventPublisher.EVENT_OBJECT_KEY;

public class RedisEventSubscriber implements StreamListener<String, MapRecord<String, String, String>>, ApplicationEventPublisherAware, InitializingBean, DisposableBean {

    private static final Logger log = LoggerFactory.getLogger(RedisEventSubscriber.class);

    RedisConnectionFactory redisConnectionFactory;
    StringRedisTemplate redisTemplate;
    ObjectMapper objectMapper;
    ApplicationEventPublisher localEventPublisher;
    String topic;
    String group;

    private StreamMessageListenerContainer<String, MapRecord<String, String, String>> listenerContainer;
    private Subscription subscription;

    public RedisEventSubscriber(RedisConnectionFactory redisConnectionFactory, StringRedisTemplate redisTemplate) {
        this.redisConnectionFactory = redisConnectionFactory;
        this.redisTemplate = redisTemplate;
        this.objectMapper = ObjectMappers.generic();
    }

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        localEventPublisher = applicationEventPublisher;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        boolean groupExists = redisTemplate.opsForStream().groups(topic).stream().anyMatch(g -> g.groupName().equalsIgnoreCase(group));
        if(!groupExists) redisTemplate.opsForStream().createGroup(topic, group);
        listenerContainer = StreamMessageListenerContainer.create(redisConnectionFactory);
        subscription = listenerContainer.receive(Consumer.from(group, UUID.randomUUID().toString()),
                StreamOffset.create(topic, ReadOffset.lastConsumed()),
                this);
        listenerContainer.start();
    }

    @Override
    public void onMessage(MapRecord<String, String, String> message) {
        log.debug("Received: {}", message);
        Map<String, String> messageMap = message.getValue();
        String eventClass = messageMap.get(EVENT_CLASS_KEY);
        Object event = messageMap.get(EVENT_OBJECT_KEY);
        if(StringUtils.hasText(eventClass)) {
            try {
                event = objectMapper.readValue((String)event, Class.forName(eventClass));
                log.debug("Deserialized: {}", event);
            } catch(ClassNotFoundException | IOException x) {
                throw new RuntimeException(x);
            }
            localEventPublisher.publishEvent(event);
        }
        redisTemplate.opsForStream().acknowledge(group, message);
    }

    @Override
    public void destroy() throws Exception {
        listenerContainer.remove(subscription);
        listenerContainer.stop();
    }

}
