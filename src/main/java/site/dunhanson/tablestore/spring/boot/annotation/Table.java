package site.dunhanson.tablestore.spring.boot.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 表名注解
 * @author dunhanson
 * @since 2021-12-07
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Table {
    /**
     * 表名
     * @return 表名
     */
    String tableName() default "";

    /**
     * 索引名称
     * @return 索引名称
     */
    String indexName() default "";
}
