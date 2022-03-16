package site.dunhanson.tablestore.spring.boot.starter.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * tablestore表字段注解
 * @author dunhanson
 * @version  0.0.1
 * @since 0.0.1
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Column {
    /**
     * 属性名称
     * @return 字段
     */
    String name() default "";
}
