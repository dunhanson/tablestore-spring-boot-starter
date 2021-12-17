package site.dunhanson.tablestore.spring.boot.util;

import cn.hutool.core.bean.BeanUtil;
import com.alicloud.openservices.tablestore.model.ColumnValue;
import com.alicloud.openservices.tablestore.model.PrimaryKeyBuilder;
import com.alicloud.openservices.tablestore.model.PrimaryKeyValue;
import com.alicloud.openservices.tablestore.model.RowPutChange;
import com.google.common.base.CaseFormat;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import site.dunhanson.tablestore.spring.boot.annotation.PrimaryKey;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * Tablestore工具类
 * @author dunhanson
 * @since 2021-12-07
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

}
