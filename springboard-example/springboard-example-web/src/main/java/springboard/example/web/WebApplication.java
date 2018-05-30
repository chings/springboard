package springboard.example.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import springboard.dubbo.annotation.EnableDubboConsumer;
import springboard.example.web.model.StormtrooperService;
import springboard.example.web.service.DefaultStormtrooperService;
import springboard.shiro.annotation.EnableShiroSecurity;
import springboard.swagger.annotation.EnableSwaggerDocumentation;

@SpringBootApplication
@EnableDubboConsumer("springboard.example.web")
@EnableShiroSecurity
@EnableSwaggerDocumentation
public class WebApplication {

    @Bean
    public StormtrooperService stormtrooperService() {
        return new DefaultStormtrooperService();
    }

    public static void main(String[] args) {
        SpringApplication.run(WebApplication.class, args);
    }

}