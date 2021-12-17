package site.dunhanson.tablestore.spring.boot.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

/**
 * 分页对象
 * 2020-01-07
 * @author dunhanson
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageInfo<T> {
    /**
     * 分页
     */
    private Integer size;
    /**
     * 现页
     */
    private Integer current;
    /**
     * 记录
     */
    private Integer index;
    /**
     * 页数
     */
    private Integer pages;
    /**
     * 总数
     */
    private Integer total;
    /**
     * 数据
     */
    private List<T> records;
}
