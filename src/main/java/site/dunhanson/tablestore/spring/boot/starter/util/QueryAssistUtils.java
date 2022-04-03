package site.dunhanson.tablestore.spring.boot.starter.util;

import com.alicloud.openservices.tablestore.model.ColumnValue;
import com.alicloud.openservices.tablestore.model.search.query.*;
import site.dunhanson.tablestore.spring.boot.starter.constant.BoolQueryType;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 搜索公用工具类
 * @author dunhanson
 * @version 2021-10-27
 */
public class QueryAssistUtils {
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");


    /**
     * 精准查询
     * @param fields 字段集合
     * @param value 查询值
     * @return 精准查询集合
     */
    public static List<Query> getTermQuery(List<String> fields, Object value) {
        return fields.stream().map(field -> getTermsQuery(field, value)).collect(Collectors.toList());
    }

    /**
     * 匹配查询
     * @param fields 字段集合
     * @param text 文本
     * @return 匹配查询集合
     */
    public static List<Query> getMatchQuery(List<String> fields, String text) {
        return fields.stream().map(field -> getMatchQuery(field, text)).collect(Collectors.toList());
    }

    /**
     * 匹配查询
     * @param field 字段
     * @param text 文本
     * @return Query
     */
    public static Query getMatchQuery(String field, String text) {
        MatchQuery query = new MatchQuery();
        query.setFieldName(field);
        query.setText(text);
        return query;
    }

    /**
     * 短语匹配查询
     * @param fields 字段集合
     * @param text 文本
     * @return 短语匹配查询集合
     */
    public static List<Query> getMatchPhraseQuery(List<String> fields, String text) {
        return fields.stream().map(field -> getMatchPhraseQuery(field, text)).collect(Collectors.toList());
    }

    /**
     * 短语匹配查询
     * @param field 字段
     * @param text 文本
     * @return 短语匹配查询集合
     */
    public static Query getMatchPhraseQuery(String field, String text) {
        MatchPhraseQuery query = new MatchPhraseQuery();
        query.setFieldName(field);
        query.setText(text);
        return query;
    }

    /**
     * 获取前缀查询
     * @param field 字段
     * @param text 文本
     * @return Query
     */
    public static Query getPrefixQuery(String field, String text) {
        PrefixQuery query = new PrefixQuery();
        query.setFieldName(field);
        query.setPrefix(text);
        return query;
    }

    /**
     * TermsQuery
     * @param field 字段
     * @param value 值
     * @return TermsQuery
     */
    public static Query getTermsQuery(String field, Object value) {
        TermQuery termQuery = new TermQuery();
        termQuery.setFieldName(field);
        if(value instanceof String) {
            termQuery.setTerm(ColumnValue.fromString((String)value));
        } else if(value instanceof Long) {
            termQuery.setTerm(ColumnValue.fromLong((Long)value));
        } else if(value instanceof Double) {
            termQuery.setTerm(ColumnValue.fromDouble((Double)value));
        } else if(value instanceof Boolean) {
            termQuery.setTerm(ColumnValue.fromBoolean((Boolean)value));
        } else if(value instanceof byte[]) {
            termQuery.setTerm(ColumnValue.fromBinary((byte[])value));
        }
        return termQuery;
    }

    /**
     * 嵌套TermsQuery
     * @param query Query
     * @param path 路径
     * @return Query
     */
    public static Query getNestedQuery(Query query, String path) {
        NestedQuery nestedQuery = new NestedQuery();
        nestedQuery.setPath(path);
        nestedQuery.setQuery(query);
        nestedQuery.setScoreMode(ScoreMode.None);
        return nestedQuery;
    }

    /**
     * 获取BoolQuery查询
     * @param queries 查询集合
     * @param queryType BoolQuery查询类型
     * @param minimumShouldMatch 定义了至少满足几个should子句
     * @return BoolQuery
     */
    public static BoolQuery getBoolQuery(List<Query> queries, BoolQueryType queryType, Integer minimumShouldMatch) {
        BoolQuery boolQuery = new BoolQuery();
        if(BoolQueryType.SHOULD == queryType) {
            // 文档应该至少匹配一个should
            boolQuery.setShouldQueries(queries);
        } else if(BoolQueryType.MUST == queryType) {
            // 文档必须完全匹配所有的子query
            boolQuery.setMustQueries(queries);
        } else if(BoolQueryType.MUST_NOT == queryType) {
            // 文档必须不能匹配任何子query
            boolQuery.setMustNotQueries(queries);
        } else if(BoolQueryType.FILTER == queryType) {
            // 文档必须完全匹配所有的子filter
            boolQuery.setFilterQueries(queries);
        } else {
            boolQuery = null;
        }
        if(minimumShouldMatch != null && boolQuery != null) {
            // 定义了至少满足几个should子句
            boolQuery.setMinimumShouldMatch(minimumShouldMatch);
        }
        return boolQuery;
    }

    /**
     * 获取BoolQuery查询
     * @param queries 查询集合
     * @param queryType BoolQuery查询类型
     * @return BoolQuery
     */
    public static BoolQuery getBoolQuery(List<Query> queries, BoolQueryType queryType) {
        return getBoolQuery(queries, queryType, null);
    }

}
