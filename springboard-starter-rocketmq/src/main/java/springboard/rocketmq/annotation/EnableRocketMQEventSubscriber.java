package springboard.rocketmq.annotation;

import org.springframework.context.annotation.Import;
import springboard.rocketmq.RocketMQEventSubscriberConfig;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@Import(RocketMQEventSubscriberConfig.class)
public @interface EnableRocketMQEventSubscriber { }
