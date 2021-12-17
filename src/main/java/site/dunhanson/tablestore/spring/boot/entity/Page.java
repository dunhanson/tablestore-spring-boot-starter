package site.dunhanson.tablestore.spring.boot.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import site.dunhanson.tablestore.spring.boot.constant.TablestoreConstant;

/**
 * 分页对象
 * @author dunhanson
 * @since 2021-12-03
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
