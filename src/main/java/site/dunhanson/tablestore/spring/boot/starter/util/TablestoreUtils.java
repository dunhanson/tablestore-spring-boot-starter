package site.dunhanson.tablestore.spring.boot.starter.util;

import com.google.common.base.CaseFormat;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import lombok.extern.slf4j.Slf4j;
import site.dunhanson.tablestore.spring.boot.starter.annotation.PrimaryKey;

import java.lang.reflect.Field;

/**
 * Tablestore工具类
 * @author dunhanson
 * @version  0.0.1
 * @since 0.0.1
 */
@Slf4j
public class TablestoreUtils {

    /**
     * 判断是否是JsonArray
     * @param jsonStr json字符串
     * @return 判断结果
     */
    public static boolean isJsonArray(String jsonStr) {
        try {
            JsonArray jsonArray = JsonParser.parseString(jsonStr).getAsJsonArray();
            return jsonArray != null;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 判断是否是JsonObject对象
     * @param jsonStr json字符串
     * @return 判断结果
     */
    public static boolean isJsonObject(String jsonStr) {
        try {
            return JsonParser.parseString(jsonStr).getAsJsonObject() != null;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 判断是否是json对象
     * @param jsonStr json字符串
     * @return 判断结果
     */
    public static boolean isJson(String jsonStr) {
        try {
            return JsonParser.parseString(jsonStr) != null;
        } catch (Exception e) {
            return false;
        }
    }


    /**
     * 解析成JSONArray
     * @param jsonStr json字符串
     * @return JSONArray
     */
    public static JsonArray parseArray(String jsonStr) {
        try {
            return JsonParser.parseString(jsonStr).getAsJsonArray();
        } catch (Exception e) {
            log.warn("jsonStr:{} parseArray fail message:{}", jsonStr, e.getMessage());
        }
        return null;
    }

    /**
     * 解析成JSONObject
     * @param jsonStr json字符串
     * @return JSONObject
     */
    public static JsonObject parseObj(String jsonStr) {
        try {
            return JsonParser.parseString(jsonStr).getAsJsonObject();
        } catch (Exception e) {
            log.warn("jsonStr:{} parseObj fail message:{}", jsonStr, e.getMessage());
        }
        return null;
    }

    /**
     * 小写下划线转换小写驼峰
     * @param text 小写下划线文本
     * @return 小写驼峰结果
     */
    public static String toLowerCamel(String text) {
        return CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, text);
    }

    /**
     * 小写驼峰转换小写下划线
     * @param text 小写驼峰
     * @return 小写下划线
     */
    public static String toLowerUnderscore(String text) {
        return CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, text);
    }

    /**
     * 获取主键值
     * @param entity 实体对象
     * @param <T> 泛型
     * @return 主键值
     */
    public static <T> Long getPrimaryKeyValue(T entity) {
        for (Field field : entity.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            PrimaryKey primaryKey = field.getAnnotation(PrimaryKey.class);
            if(primaryKey == null) {
                continue;
            }
            try {
                return (Long) field.get(entity);
            } catch (IllegalAccessException e) {
                log.warn(e.getMessage());
            }
        }
        return null;
    }

    /**
     * 获取主键
     * @param clazz Class
     * @param <T> 泛型
     * @return PrimaryKey
     */
    public static <T> PrimaryKey getPrimaryKey(Class<T> clazz) {
        for (Field field : clazz.getDeclaredFields()) {
            field.setAccessible(true);
            PrimaryKey primaryKey = field.getAnnotation(PrimaryKey.class);
            if(primaryKey == null) {
                continue;
            }
            return primaryKey;
        }
        return null;
    }

}
