package site.dunhanson.tablestore.spring.boot.util;

import com.google.common.base.CaseFormat;

/**
 * 格式化工具类
 * @author dunhanson
 * @since 2021-12-07
 */
public class BasicUtils {
    /**
     * 小写下划线转小写驼峰
     * @param str 字符串
     * @return 处理过的字符串
     */
    public static String lowerUnderscoreToLowerCamel(String str) {
        return CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, str);
    }
}
