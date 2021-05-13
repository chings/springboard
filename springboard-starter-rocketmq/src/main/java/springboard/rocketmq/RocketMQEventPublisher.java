package springboard.rocketmq;

import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import springboard.lang.EventPublisher;

import java.util.HashMap;
import java.util.Map;

public class RocketMQEventPublisher implements EventPublisher {

    private static final Logger log = LoggerFactory.getLogger(RocketMQEventPublisher.class);

    public static final String MESSAGE_CLASS_KEY = "_class";

    RocketMQTemplate rocketMQTemplate;
    String topic;

    public RocketMQEventPublisher(RocketMQTemplate rocketMQTemplate) {
        this.rocketMQTemplate = rocketMQTemplate;
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
        log.debug("Sending: {}", event);
        rocketMQTemplate.convertAndSend(topic, event, headers(MESSAGE_CLASS_KEY, event.getClass().getCanonicalName()));
    }

    @Override
    public void publish(String topic, Object event) {
        log.debug("Sending: {}", event);
        rocketMQTemplate.convertAndSend(topic, event, headers(MESSAGE_CLASS_KEY, event.getClass().getCanonicalName()));
    }

}
