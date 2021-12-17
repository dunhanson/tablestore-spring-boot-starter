package site.dunhanson.tablestore.spring.boot.config;

import cn.hutool.core.bean.BeanUtil;
import com.alicloud.openservices.tablestore.ClientConfiguration;
import com.alicloud.openservices.tablestore.SyncClient;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import site.dunhanson.tablestore.spring.boot.config.properties.ClientConfigurationProperties;
import site.dunhanson.tablestore.spring.boot.config.properties.TablestoreProperties;
import site.dunhanson.tablestore.spring.boot.core.TablestoreTemplate;
import javax.annotation.Resource;

/**
 * tablestore的初始化配置
 * @author dunhanson
 * @since 2021-12-07
 */
@Configuration
@EnableConfigurationProperties(value = {TablestoreProperties.class, ClientConfigurationProperties.class})
public class TablestoreAutoConfiguration {
    @Resource
    private TablestoreProperties tablestoreProperties;

    /**
     * 同步实例
     * @return SyncClient
     */
    @Bean
    public SyncClient syncClient() {
        ClientConfigurationProperties properties = tablestoreProperties.getClientConfiguration();
        String endPoint = tablestoreProperties.getEndPoint();
        String accessKeyId = tablestoreProperties.getAccessKeyId();
        String accessKeySecret = tablestoreProperties.getAccessKeySecret();
        String instanceName = tablestoreProperties.getInstanceName();
        if(properties == null) {
            return new SyncClient(endPoint, accessKeyId, accessKeySecret, instanceName);
        }
        ClientConfiguration configuration = new ClientConfiguration();
        BeanUtil.copyProperties(properties, configuration);
        return new SyncClient(endPoint, accessKeyId, accessKeySecret, instanceName, configuration);
    }

    @Bean
    public TablestoreTemplate tablestoreTemplate(SyncClient syncClient) {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES);
        Gson gson = gsonBuilder.create();
        return new TablestoreTemplate(syncClient, gson);
    }

}
