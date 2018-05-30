package springboard.mybatis;

import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

@Configuration
public class MybatisPersistenceConfig {

    @Autowired
    SqlSessionFactory sqlSessionFactory;

    @PostConstruct
    public void sqlSessionFactory() {
        sqlSessionFactory.getConfiguration().getTypeHandlerRegistry().setDefaultEnumTypeHandler(AutoEnumTypeHandler.class);
    }

}
