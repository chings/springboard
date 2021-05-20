package springboard.redis;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.data.redis.connection.stream.MapRecord;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.stream.StreamListener;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.Map;

import static springboard.redis.RedisEventPublisher.EVENT_CLASS_KEY;
import static springboard.redis.RedisEventPublisher.EVENT_OBJECT_KEY;

public class RedisEventSubscriber implements StreamListener<String, MapRecord<String, String, String>>, ApplicationEventPublisherAware {

    private static final Logger log = LoggerFactory.getLogger(RedisEventSubscriber.class);

    StringRedisTemplate redisTemplate;
    ObjectMapper objectMapper;
    ApplicationEventPublisher localEventPublisher;
    String group;

    public RedisEventSubscriber(StringRedisTemplate redisTemplate, ObjectMapper objectMapper) {
        this.redisTemplate = redisTemplate;
        this.objectMapper = objectMapper;
    }

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        localEventPublisher = applicationEventPublisher;
    }

    public void setGroup(String group) {
        this.group = group;
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

}
