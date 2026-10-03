package dev.chaunm.util;

import com.google.gson.Gson;
import com.google.gson.JsonParseException;

public class JsonUtil {
    private static final Gson GSON = new Gson();

    public static String toJson(Object obj) {
        return GSON.toJson(obj);
    }

    public static <T> T fromJson(String json, Class<T> clazz) {
        try {
            return GSON.fromJson(json, clazz);
        } catch (JsonParseException e) {
            throw new JsonException("Invalid JSON", e);
        }
    }

    public static <T> T convert(Object value, Class<T> clazz) {
        try {
            return GSON.fromJson(GSON.toJsonTree(value), clazz);
        } catch (JsonParseException e) {
            throw new JsonException("Cannot convert value to " + clazz.getSimpleName(), e);
        }
    }
}
