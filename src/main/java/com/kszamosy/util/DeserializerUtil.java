package com.kszamosy.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.kszamosy.exception.InternalErrorException;
import com.kszamosy.model.resource.NbaApiResponseBody;
import com.kszamosy.model.resource.NbaApiResponseResource;

public class DeserializerUtil {

    private static final ObjectMapper mapper = new ObjectMapper().registerModule(new JavaTimeModule());

    public static <T> T deserializeSimple(String body, Class<T> clazz) {
        var type = mapper.getTypeFactory().constructType(clazz);
        return deserialize(body, type);
    }

    public static <T extends NbaApiResponseBody> NbaApiResponseResource<T> deserializeWithParam(String body, Class<T> clazz) {
        var type = mapper.getTypeFactory().constructParametricType(NbaApiResponseResource.class, clazz);
        return deserialize(body, type);
    }

    private static <T> T deserialize(String body, JavaType type) {
        try {
            return mapper.readValue(body, type);
        } catch (JsonProcessingException e) {
            throw new InternalErrorException("Error during deserialization", e);
        }
    }
}
