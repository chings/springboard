package springboard.example.web;

import com.baomidou.mybatisplus.core.enums.IEnum;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springboard.dubbo.annotation.EnableDubboConsumer;
import springboard.rocketmq.annotation.EnableRocketMQEventBus;
import springboard.shiro.annotation.EnableShiroSecurity;
import springboard.swagger.annotation.EnableSwaggerDocumentation;

import java.io.IOException;

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