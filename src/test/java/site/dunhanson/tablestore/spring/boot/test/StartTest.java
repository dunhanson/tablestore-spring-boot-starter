package site.dunhanson.tablestore.spring.boot.test;

import com.alicloud.openservices.tablestore.model.ColumnValue;
import com.alicloud.openservices.tablestore.model.search.query.TermQuery;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import site.dunhanson.tablestore.spring.boot.core.TablestoreTemplate;
import site.dunhanson.tablestore.spring.boot.entity.PageInfo;
import site.dunhanson.tablestore.spring.boot.test.entity.Archives;
import javax.annotation.Resource;

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
    public void start() {
        // query
        TermQuery query = new TermQuery();
        query.setFieldName("id");
        query.setTerm(ColumnValue.fromString("d201c4b1-6db6-4e06-b5db-cbb796b2e56b"));
        // search
        PageInfo<Archives> pageInfo = tablestoreTemplate.search(Archives.class, query);
        pageInfo.getRecords().forEach(System.out::println);
    }
}
