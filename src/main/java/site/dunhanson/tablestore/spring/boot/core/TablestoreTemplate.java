package site.dunhanson.tablestore.spring.boot.core;

import cn.hutool.core.util.ClassUtil;
import cn.hutool.core.util.PageUtil;
import com.alicloud.openservices.tablestore.SyncClient;
import com.alicloud.openservices.tablestore.model.*;
import com.alicloud.openservices.tablestore.model.search.SearchQuery;
import com.alicloud.openservices.tablestore.model.search.SearchRequest;
import com.alicloud.openservices.tablestore.model.search.SearchResponse;
import com.alicloud.openservices.tablestore.model.search.query.Query;
import com.alicloud.openservices.tablestore.model.search.sort.Sort;
import com.google.gson.*;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import site.dunhanson.tablestore.spring.boot.annotation.Table;
import site.dunhanson.tablestore.spring.boot.constant.TablestoreConstant;
import site.dunhanson.tablestore.spring.boot.entity.Condition;
import site.dunhanson.tablestore.spring.boot.entity.Page;
import site.dunhanson.tablestore.spring.boot.entity.PageInfo;
import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;
import static site.dunhanson.tablestore.spring.boot.util.TablestoreUtils.toLowerUnderscore;

/**
 * TablestoreTemplate
 * @author dunhanson
 * @since 2021-12-07
 */
@AllArgsConstructor
@Slf4j
public class TablestoreTemplate {
    /**
     * SyncClient
     */
    private SyncClient syncClient;
    /**
     * gson
     */
    private Gson gson;

    /**
     * 搜索
     * @param clazz 需要转换的泛型类
     * @param condition 条件
     * @param <T> 泛型
     * @return 分页信息对象
     */
    public <T> PageInfo<T> search(Class<T> clazz, Condition condition) {
        Page page = Optional.ofNullable(condition.getPage()).orElse(new Page());
        Query query = condition.getQuery();
        List<Sort.Sorter> sorts = condition.getSorts();
        List<String> returnColumns = condition.getReturnColumns();
        return search(clazz, page, query, sorts, returnColumns);
    }

    /**
     * 查询
     * @param clazz 需要转换的泛型类
     * @param query Query
     * @param <T> 泛型
     * @return 分页信息对象
     */
    public <T> PageInfo<T> search(Class<T> clazz, Query query) {
        return search(clazz, new Page(), query);
    }

    /**
     * 查询
     * @param clazz 需要转换的泛型类
     * @param page 分页对象
     * @param query Query
     * @param <T> 泛型
     * @return 分页信息对象
     */
    public <T> PageInfo<T> search(Class<T> clazz, Page page, Query query) {
        return search(clazz, page, query, null);
    }

    /**
     * 查询
     * @param clazz 需要转换的泛型类
     * @param page 分页对象
     * @param query Query
     * @param returnColumns 查询需要返回的字段集合
     * @param <T> 泛型
     * @return 分页信息对象
     */
    public <T> PageInfo<T> search(Class<T> clazz, Page page, Query query, List<String> returnColumns) {
        return this.search(clazz, page, query,null, returnColumns);
    }

    /**
     * 查询
     * @param clazz 需要转换的泛型类
     * @param page 分页对象
     * @param query Query
     * @param sorts 排序集合
     * @param returnColumns v
     * @param <T> 泛型
     * @return 分页信息对象
     */
    public <T> PageInfo<T> search(Class<T> clazz, Page page, Query query, List<Sort.Sorter> sorts, List<String> returnColumns) {
        // 当前页
        int pageNo = Optional.ofNullable(page.getPageNo()).orElse(TablestoreConstant.DEFAULT_PAGE_NO);
        // 分页大小
        int pageSize = Optional.ofNullable(page.getPageSize()).orElse(TablestoreConstant.DEFAULT_PAGE_SIZE);
        // 大于100属于超出tableStore官方设置的查询大小，进行手动分页获取需要的大小
        if(pageSize > TablestoreConstant.MAX_PAGE_SIZE) {
            // 默认分页大小
            int defaultPageSize = TablestoreConstant.MAX_PAGE_SIZE;
            // 需要获取的记录大小
            int needToGetNum = pageSize;
            // 实际能够查询到的记录大小
            int actualTotalNum;
            // 总记录数
            int total;
            pageNo = 1;
            pageSize = 1;
            PageInfo<T> temp = this.search(clazz, new Page(pageNo, pageSize), query, sorts, returnColumns);
            actualTotalNum = temp.getTotal();
            // 实际能够查询到的记录大小 < 需要获取的记录大小
            if(actualTotalNum == 0) {
                temp.setTotal(0);
                temp.setPages(0);
                temp.setRecords(Collections.emptyList());
                return temp;
            } else {
                total = Math.min(actualTotalNum, needToGetNum);
            }
            // 实际结果集合，追加分页获取的结果集合到actualList
            List<T> records = new ArrayList<>();
            int pages = PageUtil.totalPage(total, defaultPageSize);
            for(pageNo = 1; pageNo <= pages; pageNo++) {
                PageInfo<T> loopPageInfo = this.search(clazz, new Page(pageNo, defaultPageSize), query, sorts, returnColumns);
                records.addAll(loopPageInfo.getRecords());
            }
            PageInfo<T> pageInfo = new PageInfo<>();
            // 设置当前页
            pageInfo.setCurrent(pageNo);
            // 设置当前下标
            pageInfo.setIndex(PageUtil.getStart(pageNo - 1, pageSize));
            // 添加实际结果集合
            pageInfo.setRecords(records);
            // 设置总记录数
            pageInfo.setTotal(actualTotalNum);
            // 设置分页大小
            pageInfo.setSize(pageSize);
            // 设置总页数
            pageInfo.setPages(PageUtil.totalPage(actualTotalNum, pageSize));
            return pageInfo;
        }

        // SearchQuery
        SearchQuery searchQuery = new SearchQuery();
        // 查询条件
        searchQuery.setQuery(query);
        // 排序
        searchQuery.setSort(CollectionUtils.isEmpty(sorts) ? null : new Sort(sorts));
        // 设置分页大小
        int limit = pageSize;
        searchQuery.setLimit(limit);
        // 设置偏移量
        int offset = PageUtil.getStart(pageNo - 1, pageSize);
        searchQuery.setOffset(offset);
        // 设置获取总记录数
        searchQuery.setGetTotalCount(true);
        // SearchResponse
        Table table = clazz.getAnnotation(Table.class);
        // SearchRequest
        SearchRequest searchRequest = new SearchRequest(table.tableName(), table.indexName(), searchQuery);
        // 返回字段
        SearchRequest.ColumnsToGet columnsToGet = new SearchRequest.ColumnsToGet();
        if(CollectionUtils.isEmpty(returnColumns)) {
            columnsToGet.setReturnAll(true);
            searchRequest.setColumnsToGet(columnsToGet);
        } else {
            columnsToGet.setReturnAll(false);
            columnsToGet.setColumns(returnColumns);
            searchRequest.setColumnsToGet(columnsToGet);
        }
        // SearchResponse
        SearchResponse response = syncClient.search(searchRequest);

        // 分页结果对象
        // 获取结果
        List<T> records = rowsToBeans(response.getRows(), clazz);
        // 分页对象
        PageInfo<T> pageInfo = new PageInfo<>();
        // 设置当前下标
        pageInfo.setIndex(PageUtil.getStart(pageNo - 1, pageSize));
        // 设置返回记录
        pageInfo.setRecords(records);
        // 设置总记录数
        pageInfo.setTotal(Long.valueOf(response.getTotalCount()).intValue());
        // 设置当前页
        pageInfo.setCurrent(pageNo);
        // 设置分页大小
        pageInfo.setSize(pageSize);
        // 设置总页数
        pageInfo.setPages(PageUtil.totalPage(pageInfo.getTotal(), pageSize));
        return pageInfo;
    }

    /**
     * rows转换成beans
     * @param rows 行对象集合
     * @param clazz 需要转换成对象的类
     * @param <T> 泛型
     * @return 对象List集合
     */
    private <T> List<T> rowsToBeans(List<Row> rows, Class<T> clazz) {
        List<T> list = new ArrayList<>();
        if(rows == null) {
            return list;
        }
        return rows.stream().map(row-> rowToBean(row, clazz)).collect(Collectors.toList());
    }

    /**
     * 行转换成对象
     * @param row 行
     * @param clazz 类
     * @param <T> 泛型
     * @return 对象
     */
    private <T> T rowToBean(Row row, Class<T> clazz) {
        T t = null;
        try {
            t = clazz.newInstance();
            PrimaryKey primaryKey = row.getPrimaryKey();
            for(Field field : ClassUtil.getDeclaredFields(clazz)) {
                field.setAccessible(true);
                // Column名称
                String columnName = toLowerUnderscore(field.getName());
                // 主键
                PrimaryKeyColumn primaryKeyColumn = primaryKey.getPrimaryKeyColumn(columnName);
                if(primaryKeyColumn != null) {
                    field.set(t, primaryKeyColumn.getValue().toColumnValue().getValue());
                    continue;
                }
                // Column
                Column column = row.getLatestColumn(columnName);
                if(column == null) {
                    continue;
                }
                // Column值
                ColumnValue value = column.getValue();
                if(value == null) {
                    continue;
                }
                Class<?> type = field.getType();
                if(ClassUtil.isBasicType(type) && type == Integer.class) {
                    setValue(t, field, Long.valueOf(column.getValue().asLong()).intValue());
                } else if(ClassUtil.isBasicType(type) || type == String.class) {
                    // 基本类型或者String类型
                    setValue(t, field, column.getValue().getValue());
                } else {
                    // 复合类型
                    String json = column.getValue().asString();
                    setValue(t, field, gson.fromJson(json, field.getAnnotatedType().getType()));
                }
            }
        } catch (Exception e) {
            log.error("rowToBean fail {}", e.getMessage());
        }
        return t;
    }

    /**
     * 对象属性赋值
     * @param t 对象
     * @param field 属性
     * @param value 值
     * @param <T> 泛型
     */
    public <T> void setValue(T t, Field field, Object value) {
        try {
            field.set(t, value);
        } catch (IllegalAccessException e) {
            log.error("setValue fail:{}, filed:{}, value:{}", e.getMessage(), field.getName(), value);
        }
    }

}
