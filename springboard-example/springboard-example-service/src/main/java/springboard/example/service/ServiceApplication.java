package springboard.example.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import springboard.dubbo.annotation.EnableDubboProvider;
import springboard.mybatis.annotation.EnableMyBatisPersistence;

@SpringBootApplication
@EnableMyBatisPersistence("springboard.example.dao")
@EnableDubboProvider("springboard.example.service")
public class ServiceApplication {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    public static void main(String[] args) {
        SpringApplication.run(ServiceApplication.class, args);
    }

}
