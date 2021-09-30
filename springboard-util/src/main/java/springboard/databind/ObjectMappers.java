package springboard.databind;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

public enum ObjectMappers {
    INSTANCE;

    private final ObjectMapper objectMapper;

    private ObjectMappers() {
        this.objectMapper = create();
    }

    public ObjectMapper get() {
        return this.objectMapper;
    }

    private static ObjectMapper create() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.enable(SerializationFeature.WRITE_ENUMS_USING_TO_STRING);
        objectMapper.enable(DeserializationFeature.READ_ENUMS_USING_TO_STRING);
        return objectMapper;
    }

    public static ObjectMapper generic() {
        return ObjectMappers.INSTANCE.get();
    }

}
