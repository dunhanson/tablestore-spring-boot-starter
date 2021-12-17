# tablestore-spring-boot-starter

## 开始

引入依赖

```xml
<dependency>
    <groupId>site.dunhanson</groupId>
    <artifactId>tablestore-spring-boot-starter</artifactId>
    <version>0.0.1</version>
</dependency>
```

spring boot yml

```yml
aliyun:
  tablestore:
    end-point: 'XXXXXX'
    access-key-id: 'XXXXXX'
    access-key-secret: 'XXXXXX'
    instance-name: 'XXXXXX'
```

java示例

```java
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

```

## 参考

[表格存储 Tablestore](https://help.aliyun.com/product/27278.html)