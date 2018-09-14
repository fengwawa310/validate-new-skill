package cn.com.cintel.validatenewskill.controller.v1.redisNotification;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import javax.annotation.Resource;

/**
 * 修改配置文件redis.conf中的：notify-keyspace-events Ex，默认为notify-keyspace-events ""
 */
@RestController
public class TestJedis {

    @Resource
    JedisPool jedisPool;

    @PostMapping(value = "testJedis")
    public void testJedis(){
        Jedis jedis = jedisPool.getResource();
        jedis.set("expired", "redis的key过期监控");
        jedis.expire("expired", 3);
    }

}
