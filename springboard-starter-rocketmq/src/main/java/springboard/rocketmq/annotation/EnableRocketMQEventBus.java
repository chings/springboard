package springboard.rocketmq.annotation;

import org.springframework.context.annotation.Import;
import springboard.rocketmq.RocketMQEventPublisherConfig;
import springboard.rocketmq.RocketMQEventSubscriberConfig;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import({ RocketMQEventSubscriberConfig.class, RocketMQEventPublisherConfig.class })
public @interface EnableRocketMQEventBus { }
