package biblioteca.cadastros.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

public class TestesConfig {

    private static ObjectMapper mapper = new ObjectMapper();

    static {
        mapper.registerModule(new JavaTimeModule());
    }
    public static String objectToJson(Object value) throws Exception {
        return mapper.writeValueAsString(value);
    }
}
