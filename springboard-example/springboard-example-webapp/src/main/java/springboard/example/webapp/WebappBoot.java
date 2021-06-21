package springboard.example.webapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springboard.redis.annotation.EnableRedisEventBus;
import springboard.shiro.annotation.EnableShiroSecurity;
import springboard.swagger.annotation.EnableSwaggerDocumentation;

@SpringBootApplication
@EnableRedisEventBus //@EnableRocketMQEventBus
@EnableShiroSecurity
@EnableSwaggerDocumentation
public class WebappBoot {

    public static void main(String[] args) {
        SpringApplication.run(WebappBoot.class, args);
    }

}