package site.dunhanson.tablestore.spring.boot.starter.constant;

/**
 * BoolQuery查询类型
 * @author dunhanson
 * @since 2022/3/17
 */
public enum BoolQueryType {
    /**
     * ShouldQueries
     */
    SHOULD,
    /**
     * MustQueries
     */
    MUST,
    /**
     * MustNotQueries
     */
    MUST_NOT,
    /**
     * FilterQueries
     */
    FILTER
}
