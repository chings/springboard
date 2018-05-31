package springboard.example.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import springboard.mybatis.annotation.EnableMybatisPersistence;

import javax.sql.DataSource;

@SpringBootApplication
@EnableMybatisPersistence("springboard.example.dao")
//@EnableDubboProvider("springboard.example.service")
public class ServiceApplication {

    @Bean(name = "springboardDataSource")
    @ConfigurationProperties(prefix = "datasource.springboard")
    @Primary
    public DataSource dataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    public static void main(String[] args) {
        SpringApplication.run(ServiceApplication.class, args);
    }

}
