package springboard.rocketmq;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.common.UtilAll;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.spring.annotation.ConsumeMode;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.apache.rocketmq.spring.support.RocketMQConsumerLifecycleListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.util.StringUtils;

import java.io.IOException;

import static springboard.rocketmq.RocketMQEventPublisher.EVENT_CLASS_KEY;

@RocketMQMessageListener(topic="${rocketmq.event-subscriber.topic}", consumerGroup="${rocketmq.event-subscriber.group}", consumeMode = ConsumeMode.ORDERLY)
public class RocketMQEventSubscriber implements RocketMQListener<MessageExt>, RocketMQConsumerLifecycleListener, ApplicationEventPublisherAware {

    private static final Logger log = LoggerFactory.getLogger(RocketMQEventSubscriber.class);

    ObjectMapper objectMapper;
    ApplicationEventPublisher localEventPublisher;

    public RocketMQEventSubscriber(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        localEventPublisher = applicationEventPublisher;
    }

    @Override
    public void prepareStart(Object o) {
        DefaultMQPushConsumer consumer = (DefaultMQPushConsumer)o;
        consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_TIMESTAMP);
        consumer.setConsumeTimestamp(UtilAll.timeMillisToHumanString3(System.currentTimeMillis()));
    }

    @Override
    public void onMessage(MessageExt message) {
        log.debug("Received: {}", message);
        Object event = new String(message.getBody());
        String eventClass = message.getUserProperty(EVENT_CLASS_KEY);
        if(StringUtils.hasText(eventClass)) {
            try {
                event = objectMapper.readValue((String)event, Class.forName(eventClass));
                log.debug("Deserialized: {}", event);
            } catch(ClassNotFoundException | IOException x) {
                throw new RuntimeException(x);
            }
            localEventPublisher.publishEvent(event);
        }
    }

}