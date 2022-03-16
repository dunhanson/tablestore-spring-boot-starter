# tablestore-spring-boot-starter

## 开始

**依赖**

```xml
<dependency>
    <groupId>site.dunhanson</groupId>
    <artifactId>tablestore-spring-boot-starter</artifactId>
    <version>0.0.3</version>
</dependency>
```

**application.yml**

```yml
tablestore:
  end-point: 'XXXXXX'
  access-key-id: 'XXXXXX'
  access-key-secret: 'XXXXXX'
  instance-name: 'XXXXXX'
```

**java**

实体类

```java
@Data
@Table(tableName = "archives", indexName = "archives_index")
public class Archives {
    @PrimaryKey("id")
    private String id;
    private String title;
    private String content;
    private String createTime;
    private String updateTime;
}
```

查询

```java
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

```

## 参考

[表格存储 Tablestore](https://help.aliyun.com/product/27278.html)