package site.dunhanson.tablestore.spring.boot.starter.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * tablestore表注解
 * @author dunhanson
 * @version  0.0.1
 * @since 0.0.1
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
