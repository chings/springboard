package springboard.example.service;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import springboard.dubbo.annotation.EnableDubboService;
import springboard.mybatis.annotation.EnableMyBatisPersistence;

@SpringBootApplication
@EnableMyBatisPersistence("springboard.example.dao")
@EnableDubboService("springboard.example.service")
public class Bootstrap {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    public static void main(String[] args) {
        new SpringApplicationBuilder(Bootstrap.class)
                .web(WebApplicationType.NONE)
                .run(args);
    }

}
