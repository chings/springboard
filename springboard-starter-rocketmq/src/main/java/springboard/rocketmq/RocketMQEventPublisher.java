package springboard.rocketmq;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import springboard.databind.ObjectMappers;
import springboard.lang.EventPublisher;

import java.util.HashMap;
import java.util.Map;

public class RocketMQEventPublisher implements EventPublisher {

    private static final Logger log = LoggerFactory.getLogger(RocketMQEventPublisher.class);

    public static final String EVENT_CLASS_KEY = "EventClass";

    RocketMQTemplate rocketMQTemplate;
    ObjectMapper objectMapper;
    String topic;

    public RocketMQEventPublisher(RocketMQTemplate rocketMQTemplate) {
        this.rocketMQTemplate = rocketMQTemplate;
        this.objectMapper = ObjectMappers.generic();
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    static Map<String, Object> headers(final Object... keyValuePairs) {
        return new HashMap<String, Object>() {{
            for(int i = 0; i + 1 < keyValuePairs.length; i += 2) {
                put(keyValuePairs[i].toString(), keyValuePairs[i + 1]);
            }
        }};
    }

    @Override
    public void publish(Object event) {
        publish(topic, event);
    }

    @Override
    public void publish(String topic, Object event) {
        log.debug("Sending: {}", event);
        Message<String> message = null;
        try {
            message = MessageBuilder.withPayload(objectMapper.writeValueAsString(event))
                    .setHeader(EVENT_CLASS_KEY, event.getClass().getCanonicalName())
                    .build();
        } catch (JsonProcessingException x) {
            throw new RuntimeException(x);
        }
        rocketMQTemplate.send(topic, message);
    }

}
