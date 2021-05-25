package springboard.rocketmq;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springboard.lang.EventPublisher;

@Configuration
public class RocketMQEventPublisherConfig {

    @Autowired
    RocketMQTemplate rocketMQTemplate;

    @Bean
    @ConditionalOnMissingBean
    ObjectMapper objectMapper() {
        return new ObjectMapper();
    }

    @Bean
    @ConfigurationProperties("rocketmq.event-publisher")
    EventPublisher eventPublisher() {
        return new RocketMQEventPublisher(rocketMQTemplate, objectMapper());
    }

}
