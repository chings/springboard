package springboard.example.web;

import com.alibaba.dubbo.config.annotation.Reference;
import org.apache.shiro.realm.Realm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import springboard.dubbo.annotation.EnableDubboConsumer;
import springboard.example.model.AdminService;
import springboard.example.model.StormtrooperService;
import springboard.example.web.component.AdminRealm;
import springboard.shiro.annotation.EnableShiroSecurity;
import springboard.swagger.annotation.EnableSwaggerDocumentation;

@SpringBootApplication
@EnableDubboConsumer("springboard.example.web")
@EnableShiroSecurity
@EnableSwaggerDocumentation
public class WebApplication {

    public static void main(String[] args) {
        SpringApplication.run(WebApplication.class, args);
    }

}