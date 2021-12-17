package site.dunhanson.tablestore.spring.boot.entity;

import com.alicloud.openservices.tablestore.model.search.query.Query;
import com.alicloud.openservices.tablestore.model.search.sort.Sort;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

/**
 * 搜索条件
 * @author dunhanson
 * @since 2021-12-02
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Condition {
    /**
     * 分页对象
     */
    private Page page;
    /**
     * 查询条件
     */
    private Query query;
    /**
     * 排序
     */
    private List<Sort.Sorter> sorts;
    /**
     * 返回字段
     */
    private List<String> returnColumns;
}
