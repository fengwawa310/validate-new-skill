package cn.com.cintel.validatenewskill.controller.v1.redisNotification;

import io.lettuce.core.RedisClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import javax.annotation.Resource;
import java.util.logging.Logger;

@RestController
public class Subscriber {

	Logger logger = Logger.getLogger("subscriber");

	@Resource
	JedisPool jedisPool;

	@PostMapping(value = "/subscriber")
	public void subscriber(){
		Jedis jedis = jedisPool.getResource();
		RedisMsgPubSubListener listener = new RedisMsgPubSubListener();
		jedis.psubscribe(listener, "__key*__:*");

	}

}
