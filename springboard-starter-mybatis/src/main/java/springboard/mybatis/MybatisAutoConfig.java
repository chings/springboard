package springboard.mybatis;

import com.baomidou.mybatisplus.spring.boot.starter.MybatisPlusAutoConfiguration;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

@Configuration
@ConditionalOnClass(SqlSessionFactory.class)
@AutoConfigureAfter(MybatisPlusAutoConfiguration.class)
public class MybatisAutoConfig {

    @Autowired
    SqlSessionFactory sqlSessionFactory;

    @PostConstruct
    public void sqlSessionFactory() {
        sqlSessionFactory.getConfiguration().getTypeHandlerRegistry().setDefaultEnumTypeHandler(AutoEnumTypeHandler.class);
    }

}
