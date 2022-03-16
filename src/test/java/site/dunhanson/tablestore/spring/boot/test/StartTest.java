package site.dunhanson.tablestore.spring.boot.test;

import com.alicloud.openservices.tablestore.model.ColumnValue;
import com.alicloud.openservices.tablestore.model.search.query.TermQuery;
import com.alicloud.openservices.tablestore.model.search.sort.FieldSort;
import com.alicloud.openservices.tablestore.model.search.sort.SortOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import site.dunhanson.tablestore.spring.boot.starter.core.TablestoreTemplate;
import site.dunhanson.tablestore.spring.boot.starter.entity.Condition;
import site.dunhanson.tablestore.spring.boot.starter.entity.Page;
import site.dunhanson.tablestore.spring.boot.starter.entity.PageInfo;
import site.dunhanson.tablestore.spring.boot.test.entity.Archives;
import javax.annotation.Resource;
import java.util.Collections;

/**
 * 开始代码
 * @author dunhanson
 * @since 2021/12/17
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class StartTest {
    @Resource
    private TablestoreTemplate tablestoreTemplate;

    @Test
    public void startQuery() {
        // query
        TermQuery query = new TermQuery();
        query.setFieldName("id");
        query.setTerm(ColumnValue.fromLong(1000L));
        // search
        PageInfo<Archives> pageInfo = tablestoreTemplate.search(Archives.class, query);
        pageInfo.getRecords().forEach(System.out::println);
    }

    @Test
    public void testCondition() {
        // query
        TermQuery query = new TermQuery();
        query.setFieldName("id");
        query.setTerm(ColumnValue.fromString("1000"));
        // condition
        Condition condition = new Condition();
        condition.setPage(new Page(1, 30));
        condition.setQuery(query);
        condition.setReturnColumns(Collections.singletonList("id"));
        condition.setSorts(Collections.singletonList(new FieldSort("id", SortOrder.DESC)));
        // search and return pageInfo
        PageInfo<Archives> pageInfo = tablestoreTemplate.search(Archives.class, condition);
        pageInfo.getRecords().forEach(System.out::println);
    }
}
