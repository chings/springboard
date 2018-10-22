package springboard.mybatis;

import com.baomidou.mybatisplus.core.enums.IEnum;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

@Configuration
public class MyBatisPersistenceConfig  implements InitializingBean {

    @Autowired(required = false)
    ObjectMapper objectMapper;

    @Override
    public void afterPropertiesSet() throws Exception {
        if(objectMapper == null) return;
        SimpleModule module = new SimpleModule("IEnum Serializer");
        module.addSerializer(new StdSerializer<IEnum>(IEnum.class) {
            @Override
            public void serialize(IEnum iEnum, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
                jsonGenerator.writeStartObject();
                jsonGenerator.writeStringField("name", iEnum.toString());
                jsonGenerator.writeNumberField("value", Integer.valueOf(iEnum.getValue().toString()));
                jsonGenerator.writeEndObject();
            }
        });
        objectMapper.registerModule(module);
    }

}
