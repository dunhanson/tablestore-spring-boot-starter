package site.dunhanson.tablestore.spring.boot.starter.config.properties;

import com.alicloud.openservices.tablestore.model.RetryStrategy;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * tablestore的相关配置
 * @author dunhanson
 * @version  0.0.1
 * @since 0.0.1
 */
@Data
@ConfigurationProperties(prefix = "tablestore.client-configuration")
public class ClientConfigurationProperties {
    /**
     * 设置HttpAsyncClient的IOReactor的线程数(因为采用的是异步IO，所以不需要配置大量线程，每个线程都能提供大量并发)。
     */
    private int ioThreadCount;
    /**
     * 设置允许打开的最大HTTP连接数(连接数影响并发度，需要根据QPS、单个请求的时间、机器配置等因素来确定一个合理的值)。
     */
    private int maxConnections;
    /**
     * 设置通过打开的连接传输数据的超时时间（单位：毫秒）。 0表示无限等待（但不推荐使用）。受系统超时时间的限制。
     */
    private int socketTimeoutInMillisecond;
    /**
     * 设置建立连接的超时时间（单位：毫秒）。0表示无限等待（但不推荐使用）。受系统超时时间的限制。
     */
    private int connectionTimeoutInMillisecond;
    /**
     * 设置用于执行错误重试的线程池的线程的个数。该线程池为一个ScheduledExecutorService。
     */
    private int retryThreadCount;
    /**
     * 设置是否需要对请求数据进行压缩。
     */
    private boolean enableRequestCompression;
    /**
     * 是否需要告知Tablestore对返回的响应内容进行压缩。
     */
    private boolean enableResponseCompression;
    /**
     * 是否需要对响应进行验证， 如果需要验证，Client会验证头信息完整性、结果是否过期、授权信息是否正确。
     */
    private boolean enableResponseValidation;
    /**
     * 是否需要对响应的内容做MD5校验， 如果需要校验Client会计算响应数据的MD5值并与返回的响应头中的x-ots-contentmd5头的值进行比对。
     */
    private boolean enableResponseContentMD5Checking;
    /**
     * 设置Tablestore的请求重试策略。
     */
    private RetryStrategy retryStrategy;
    /**
     * 设置服务端Tracer的时间阈值(单位：毫秒)。 当一个请求在服务端的执行时间超过该阈值时，SDK会收到服务端的tracer信息并记录。 该功能依赖于服务端相关配置。
     */
    private int timeThresholdOfServerTracer;
    /**
     * 设置一个时间阈值(单位：毫秒)。 当一个请求的总执行时间(包含重试占用的时间)超过该阈值时，SDK会记录一条WARN级别的日志。 该功能依赖于日志相关的配置。
     */
    private int timeThresholdOfTraceLogger;
    /**
     * 设置代理服务器主机地址。
     */
    private String proxyHost;
    /**
     * 设置代理服务器端口。
     */
    private int proxyPort;
    /**
     * 设置代理服务器验证的用户名。
     */
    private String proxyUsername;
    /**
     * 设置代理服务器验证的密码。
     */
    private String proxyPassword;
    /**
     * 设置访问NTLM验证的代理服务器的Windows域名（可选）。
     */
    private String proxyDomain;
    /**
     * 设置NTLM代理服务器的Windows工作站名称。 （可选，如果代理服务器非NTLM，不需要设置该参数）。
     */
    private String proxyWorkstation;
    /**
     * 设置同步Client内等待异步调用返回的最大超时时间。
     */
    private long syncClientWaitFutureTimeoutInMillis;
}
