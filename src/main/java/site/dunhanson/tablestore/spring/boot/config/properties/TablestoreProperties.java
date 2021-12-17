package site.dunhanson.tablestore.spring.boot.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * tablestore基本配置
 * @author dunhanson
 * @since 2021-12-07
 */
@Data
@ConfigurationProperties(prefix = "tablestore")
public class TablestoreProperties {
    /**
     * Tablestore服务的endpoint
     */
    private String endPoint;
    /**
     * 访问Tablestore服务的Access ID
     */
    private String accessKeyId;
    /**
     * 访问Tablestore服务的Access Key
     */
    private String accessKeySecret;
    /**
     * 访问Tablestore服务的实例名称
     */
    private String instanceName;
    /**
     * ClientConfiguration
     */
    private ClientConfigurationProperties clientConfiguration;
}
