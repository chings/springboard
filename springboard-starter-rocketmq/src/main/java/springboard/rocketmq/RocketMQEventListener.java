package springboard.rocketmq;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.common.UtilAll;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.spring.starter.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.starter.core.RocketMQListener;
import org.apache.rocketmq.spring.starter.core.RocketMQPushConsumerLifecycleListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.util.StringUtils;

import java.io.IOException;

import static springboard.rocketmq.RocketMQEventPublisher.MESSAGE_CLASS_KEY;

@RocketMQMessageListener(topic="${spring.rocketmq.consumer.topic}", consumerGroup="${spring.rocketmq.consumer.group}")
public class RocketMQEventListener implements RocketMQListener<MessageExt>, RocketMQPushConsumerLifecycleListener {

    private static Logger log = LoggerFactory.getLogger(RocketMQEventListener.class);

    ApplicationEventPublisher eventPublisher;
    ObjectMapper objectMapper;

    public RocketMQEventListener(ApplicationEventPublisher eventPublisher, ObjectMapper objectMapper) {
        this.eventPublisher = eventPublisher;
        this.objectMapper = objectMapper;
    }

    public void onMessage(MessageExt message) {
        log.debug("Received: {}", message);
        Object event = new String(message.getBody());
        String messageClass = getUserProperty(message, MESSAGE_CLASS_KEY);
        if(StringUtils.hasText(messageClass)) {
            try {
                event = objectMapper.readValue((String)event, Class.forName(messageClass));
                log.debug("Deserialized: {}", event);
            } catch(ClassNotFoundException | IOException x) {
                throw new RuntimeException(x);
            }
        }
        eventPublisher.publishEvent(event);
    }

    static String getUserProperty(MessageExt message, String key) {
        String result = message.getUserProperty(key);
        if(StringUtils.isEmpty(result)) result = message.getProperty("USERS_" + key);
        return result;
    }

    @Override
    public void prepareStart(DefaultMQPushConsumer consumer) {
        consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_TIMESTAMP);
        consumer.setConsumeTimestamp(UtilAll.timeMillisToHumanString3(System.currentTimeMillis()));
    }

}