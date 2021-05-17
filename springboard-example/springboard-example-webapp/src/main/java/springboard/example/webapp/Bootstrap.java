package springboard.example.webapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springboard.rocketmq.annotation.EnableRocketMQEventBus;
import springboard.shiro.annotation.EnableShiroSecurity;
import springboard.swagger.annotation.EnableSwaggerDocumentation;

@SpringBootApplication
@EnableRocketMQEventBus
@EnableShiroSecurity
@EnableSwaggerDocumentation
public class Bootstrap {

    public static void main(String[] args) {
        SpringApplication.run(Bootstrap.class, args);
    }

}