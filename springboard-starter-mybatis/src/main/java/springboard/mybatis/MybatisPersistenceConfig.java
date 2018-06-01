package springboard.mybatis;

import com.baomidou.dynamic.datasource.DynamicDataSourceProvider;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MybatisPersistenceConfig {

    @ConditionalOnMissingBean
    @Bean
    public DynamicDataSourceProvider DynamicDataSourceProvider(ApplicationContext context) {
        return new BeanDataSourceProvider(context);
    }

}
