package com.bishijie.alert.util;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;

/**
 * @author Rick
 */
public class JSONUtil {

    /**
     * fastJson
     */
    private static ObjectMapper mapper = new ObjectMapper();



    /**
     * @param object
     * @return
     */
    public static String toJSON(Object object) throws Exception {
        return mapper.writeValueAsString(object);
    }





    /**
     * @param jsonString
     * @param clazz
     * @return
     * @throws Exception
     */
    public static <T> T toObject(String jsonString, Class<T> clazz) throws Exception {
        return mapper.readValue(jsonString, clazz);
    }



    /**
     * @param jsonString
     * @return
     * @throws Exception
     */
    public static JsonNode read(String jsonString) throws Exception {
        return mapper.readTree(jsonString);
    }

    /**
     * @param jsonString
     * @param clazz
     * @return
     * @throws Exception
     */
    public static <T> List<T> toList(String jsonString, Class<T> clazz) throws Exception {
        JavaType javaType = mapper.getTypeFactory().constructCollectionType(List.class, clazz);
        return mapper.readValue(jsonString, javaType);
    }




}
