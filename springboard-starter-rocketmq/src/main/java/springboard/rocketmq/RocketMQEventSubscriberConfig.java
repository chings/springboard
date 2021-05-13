package springboard.rocketmq;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RocketMQEventSubscriberConfig {

    @Autowired
    ObjectMapper objectMapper;

    @Bean
    RocketMQEventSubscriber eventSubscriber() {
        return new RocketMQEventSubscriber(objectMapper);
    }

}
