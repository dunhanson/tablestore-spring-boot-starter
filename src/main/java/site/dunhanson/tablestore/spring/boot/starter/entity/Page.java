package site.dunhanson.tablestore.spring.boot.starter.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import site.dunhanson.tablestore.spring.boot.starter.constant.TablestoreConstant;

/**
 * 分页对象
 * @author dunhanson
 * @version  0.0.1
 * @since 0.0.1
 */
@Data
@AllArgsConstructor
public class Page {
    private Integer pageNo;
    private Integer pageSize;

    public Page() {
        this.pageNo = TablestoreConstant.DEFAULT_PAGE_NO;
        this.pageSize = TablestoreConstant.DEFAULT_PAGE_SIZE;
    }

    public static Page single() {
        return new Page(1,1);
    }
}
