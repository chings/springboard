package springboard.dubbo.config;

import org.apache.dubbo.spring.boot.autoconfigure.DubboAutoConfiguration;
import org.apache.dubbo.spring.boot.autoconfigure.DubboRelaxedBinding2AutoConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableAutoConfiguration(exclude = { DubboAutoConfiguration.class, DubboRelaxedBinding2AutoConfiguration.class })
public class DisableDubboRPCConfig {

}
