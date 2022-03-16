package site.dunhanson.tablestore.spring.boot.starter.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 分页对象
 * 2020-01-07
 * @version  0.0.1
 * @since 0.0.1
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

    @Override
    public String toString() {
        return "PageInfo{" +
                "size=" + size +
                ", current=" + current +
                ", index=" + index +
                ", pages=" + pages +
                ", total=" + total +
                '}';
    }
}
