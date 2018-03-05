package com.kmv.geological.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

/**
 *
 * @author khosro.makari@gmail.com
 */
public class JacksonMapperConfig {

    private JacksonMapperConfig() {

    }

    private static final ObjectMapper objectMapper = new ObjectMapper();

    static {

        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

    }

    public static ObjectMapper getObjectMapper() {
        return objectMapper;
    }
}
