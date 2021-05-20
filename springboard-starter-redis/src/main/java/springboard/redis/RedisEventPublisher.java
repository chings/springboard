package springboard.redis;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.stream.StreamRecords;
import org.springframework.data.redis.connection.stream.StringRecord;
import org.springframework.data.redis.core.StringRedisTemplate;
import springboard.lang.EventPublisher;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class RedisEventPublisher implements EventPublisher {

    private static final Logger log = LoggerFactory.getLogger(RedisEventPublisher.class);

    public static final String EVENT_CLASS_KEY = "EventClass";
    public static final String EVENT_OBJECT_KEY = "EventObject";

    StringRedisTemplate redisTemplate;
    ObjectMapper objectMapper;
    String topic;

    public RedisEventPublisher(StringRedisTemplate redisTemplate, ObjectMapper objectMapper) {
        this.redisTemplate = redisTemplate;
        this.objectMapper = objectMapper;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    @Override
    public void publish(Object event) {
        publish(topic, event);
    }

    @Override
    public void publish(String destination, Object event) {
        log.debug("Sending: {}", event);
        Map<String, String> message = new HashMap<>();
        message.put(EVENT_CLASS_KEY, event.getClass().getCanonicalName());
        try {
            message.put(EVENT_OBJECT_KEY, objectMapper.writeValueAsString(event));
        } catch(JsonProcessingException x) {
            throw new RuntimeException(x);
        }
        StringRecord record = StreamRecords.string(message).withStreamKey(destination);
        redisTemplate.opsForStream().add(record);
    }

}
