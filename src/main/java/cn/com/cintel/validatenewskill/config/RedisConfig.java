package cn.com.cintel.validatenewskill.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * @Company Name:
 * @Author: sky
 * @CreatDate: 2018/9/7 13:52
 * @ClassName: cn.com.cintel.validatenewskill.config
 * @Description:
 * @Modified By:
 * @ModifyDate: 2018/9/7 13:52
 */
@Configuration
public class RedisConfig {

    @Value("${spring.redis.host}")
    String host;
    @Value("${spring.redis.port}")
    Integer port;

    /**
     * 单机版redis配置
     * @return
     */
    @Bean
    public JedisPool jedisPool() {
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxIdle(8);
        jedisPoolConfig.setMaxWaitMillis(100);
        return new JedisPool(host, port);
    }

}
