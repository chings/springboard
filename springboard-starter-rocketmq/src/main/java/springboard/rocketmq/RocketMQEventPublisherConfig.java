package springboard.rocketmq;

import org.apache.rocketmq.spring.starter.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springboard.lang.EventPublisher;

@Configuration
public class RocketMQEventPublisherConfig {

    @Autowired
    RocketMQTemplate rocketMQTemplate;

    @Bean
    @ConfigurationProperties("spring.rocketmq.producer")
    EventPublisher eventPublisher() {
        return new RocketMQEventPublisher(rocketMQTemplate);
    }

}
