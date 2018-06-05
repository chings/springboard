package springboard.example.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springboard.dubbo.annotation.EnableDubboConsumer;
import springboard.rocketmq.annotation.EnableRocketMQEventBus;
import springboard.shiro.annotation.EnableShiroSecurity;
import springboard.swagger.annotation.EnableSwaggerDocumentation;

@SpringBootApplication
@EnableDubboConsumer("springboard.example.web")
@EnableRocketMQEventBus
@EnableShiroSecurity
@EnableSwaggerDocumentation
public class WebApplication {

    public static void main(String[] args) {
        SpringApplication.run(WebApplication.class, args);
    }

}