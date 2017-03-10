package com.yuhuayuan.tool.json;


import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang.StringUtils;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

/**
 * Created by cl on 2017/3/8.
 */

public final class JsonUtils {
    private static final ObjectMapper OBJ_MAPPER = new ObjectMapper();

    public static String toJsonString(Object obj) {
        try {
            return OBJ_MAPPER.writeValueAsString(obj);
        } catch (JsonProcessingException var2) {
            throw new RuntimeException("parse to json String error", var2);
        }
    }

    public static <T> T parseObject(String jsonString, Class<T> clazz) {
        try {
            return OBJ_MAPPER.readValue(jsonString, clazz);
        } catch (IOException var3) {
            throw new RuntimeException("parse to object error", var3);
        }
    }

    public static <T> List<T> convertToList(String jsonString, Class<T> clazz) {
        if(StringUtils.isEmpty(jsonString)) {
            return Collections.emptyList();
        } else {
            try {
                return (List)OBJ_MAPPER.readValue(jsonString, OBJ_MAPPER.getTypeFactory().constructCollectionType(List.class, clazz));
            } catch (IOException var3) {
                throw new RuntimeException("parse to list error", var3);
            }
        }
    }

    private JsonUtils() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }

    static {
        OBJ_MAPPER.setSerializationInclusion(Include.NON_NULL);
        OBJ_MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }
}
