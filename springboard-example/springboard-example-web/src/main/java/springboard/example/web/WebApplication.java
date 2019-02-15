package springboard.example.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springboard.rocketmq.annotation.EnableRocketMQEventBus;
import springboard.shiro.annotation.EnableShiroSecurity;
import springboard.swagger.annotation.EnableSwaggerDocumentation;

@SpringBootApplication
@EnableRocketMQEventBus
@EnableShiroSecurity
@EnableSwaggerDocumentation
public class WebApplication {

    public static void main(String[] args) {
        SpringApplication.run(WebApplication.class, args);
    }

}