package springboard.rocketmq;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RocketMQEventSubscriberConfig {

    @Bean
    RocketMQEventSubscriber eventSubscriber() {
        return new RocketMQEventSubscriber();
    }

}
