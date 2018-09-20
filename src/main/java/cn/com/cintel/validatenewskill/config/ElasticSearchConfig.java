package cn.com.cintel.validatenewskill.config;

import org.apache.commons.lang.StringUtils;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.elasticsearch.xpack.client.PreBuiltXPackTransportClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import javax.annotation.PostConstruct;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @Company Name:
 * @Author: sky
 * @CreatDate: 2018/9/18 16:09
 * @ClassName: cn.com.cintel.validatenewskill.config
 * @Description: ElasticSearch的相关配置
 * @Modified By:
 * @ModifyDate: 2018/9/18 16:09
 */
@Configuration
@EnableAutoConfiguration
@PropertySource(value = "classpath:/es.properties")
public class ElasticSearchConfig {

    @PostConstruct
    void init() {
        System.setProperty("es.set.netty.runtime.available.processors", "false");
    }

    @Value("${es.user}")
    private String user;
    @Value("${es.password}")
    private String password;
    @Value("${es.ip}")
    private String ip;
    @Value("${es.clusterName}")
    private String clusterName;
    @Value("${es.port}")
    private String port;

    @Bean
    public TransportClient transportClient(){
        Settings settings = Settings.builder()
                .put("cluster.name", clusterName)
                .put("xpack.security.transport.ssl.enabled", false)
                .put("xpack.security.user", user + ":" + password)
                .put("client.transport.sniff", true)
                .build();

        PreBuiltXPackTransportClient client = new PreBuiltXPackTransportClient(settings);
        if(StringUtils.isNotBlank(ip)) {
            String[] ips = ip.split(",");
            for (String _ip : ips) {
                TransportAddress node = null;
                try {
                    node = new TransportAddress(
                            InetAddress.getByName(_ip),
                            //tcp端口
                            Integer.parseInt(port)
                    );
                } catch (UnknownHostException e) {
                    e.printStackTrace();
                }

                client.addTransportAddress(node);
            }
        }

        return client;
    }
}
