package site.dunhanson.tablestore.spring.boot.starter.exception;

/**
 * tablestore异常类
 * @author dunhanson
 * @since 2022/3/16
 */
public class TablestoreException extends RuntimeException{
    public TablestoreException(String message) {
        super(message);
    }
}
