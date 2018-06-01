package springboard.mybatis;

import com.baomidou.dynamic.datasource.DynamicDataSourceProvider;
import org.springframework.context.ApplicationContext;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

public class BeanDataSourceProvider implements DynamicDataSourceProvider {

    ApplicationContext context;

    public BeanDataSourceProvider(ApplicationContext context) {
        this.context = context;
    }

    @Override
    public DataSource loadMasterDataSource() {
        return context.getBean(DataSource.class);
    }

    @Override
    public Map<String, DataSource> loadSlaveDataSource() {
        String[] beanNames = context.getBeanNamesForType(DataSource.class);
        Map<String, DataSource> result = new HashMap<>();
        for(String beanName : beanNames) {
            result.put(beanName, (DataSource)context.getBean(beanName));
        }
        return result;
    }

}