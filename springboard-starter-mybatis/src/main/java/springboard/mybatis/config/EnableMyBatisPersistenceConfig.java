package springboard.mybatis.config;

import com.baomidou.mybatisplus.autoconfigure.MybatisPlusProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class EnableMyBatisPersistenceConfig {

    @Bean
    @Primary //use "mybatis-config.xml" here as default configuration
    public MybatisPlusProperties mybatisPlusProperties() throws Exception {
        MybatisPlusProperties mybatisPlusProperties = new MybatisPlusProperties();
        mybatisPlusProperties.setConfigLocation("classpath:mybatis-config.xml");
        return mybatisPlusProperties;
    }

}
