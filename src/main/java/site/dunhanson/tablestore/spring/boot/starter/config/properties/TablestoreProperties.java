package site.dunhanson.tablestore.spring.boot.starter.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * tablestore基本配置
 * <pre>
 * tablestore:
 *   end-point: '******'
 *   access-key-id: '******'
 *   access-key-secret: '******'
 *   instance-name: '******'
 * </pre>
 * @author dunhanson
 * @version  0.0.1
 * @since 0.0.1
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
