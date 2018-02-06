package springboard.example.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import springboard.example.model.StormtrooperService;
import springboard.example.service.DefaultStormtrooperService;
import springboard.shiro.annotation.EnableShiroSecurity;
import springboard.swagger.annotation.EnableSwaggerDocumentation;
import springboard.web.annotation.EnableRestWebMVC;

@SpringBootApplication
@EnableRestWebMVC
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