package com.binge.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.fasterxml.jackson.databind.type.MapType;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * 佛祖保佑  永无BUG
 *
 * @author: HuangJiaBin
 * @date: 2020年 12月21日
 * @description:
 **/
@Component
public class JsonUtil {

    private static ObjectMapper objectMapper = new ObjectMapper();

    public String obj2Str(Object obj) {
        if (obj instanceof String) {
            return (String) obj;
        }
        String s = "";
        try {
            s = objectMapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return s;
    }

    public <T> T str2Obj(String jsonStr, Class<T> t) {
        try {
            return objectMapper.readValue(jsonStr, t);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    public <T> List<T> str2List(String listJsonStr, Class<T> t) {
        CollectionType collectionType = objectMapper.getTypeFactory().constructCollectionType(List.class, t);
        try {
            return objectMapper.readValue(listJsonStr, collectionType);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    /*
     * 字符串转Map
     * */

    public <T, K> Map<T, K> str2Map(String mapJsonStr, Class<T> keyClass, Class<K> valueClass) {
        MapType mapType = objectMapper.getTypeFactory().constructMapType(Map.class, keyClass, valueClass);
        try {
            Map<T, K> map = objectMapper.readValue(mapJsonStr, mapType);
            return map;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }


}
