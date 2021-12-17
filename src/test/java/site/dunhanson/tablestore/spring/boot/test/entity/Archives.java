package site.dunhanson.tablestore.spring.boot.test.entity;

import lombok.Data;
import site.dunhanson.tablestore.spring.boot.annotation.Table;

/**
 * 档案
 * @author dunhanson
 * @since 2021/12/17
 */
@Data
@Table(tableName = "archives", indexName = {"archives_index"})
public class Archives {
    private String id;
    private String title;
    private String content;
    private String createTime;
    private String updateTime;
}
