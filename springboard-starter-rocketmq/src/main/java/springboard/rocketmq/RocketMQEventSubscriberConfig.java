package springboard.rocketmq;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RocketMQEventSubscriberConfig extends RocketMQCommonConfig {

    @Bean
    RocketMQEventSubscriber eventSubscriber() {
        return new RocketMQEventSubscriber(objectMapper());
    }

}
