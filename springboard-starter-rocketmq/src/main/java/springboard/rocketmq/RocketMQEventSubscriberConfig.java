package springboard.rocketmq;

import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RocketMQEventSubscriberConfig {

    @Autowired
    RocketMQTemplate rocketMQTemplate;

    @Bean
    public RocketMQListener<MessageExt> rocketMQListener() {
        return new RocketMQEventSubscriber(rocketMQTemplate);
    }

}
