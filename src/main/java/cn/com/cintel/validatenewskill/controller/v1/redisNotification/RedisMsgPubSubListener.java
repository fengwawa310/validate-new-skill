package cn.com.cintel.validatenewskill.controller.v1.redisNotification;

import cn.com.cintel.validatenewskill.config.RedisConfig;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPubSub;

import javax.annotation.Resource;

public class RedisMsgPubSubListener extends JedisPubSub {

    @Override
    public void unsubscribe() {
        super.unsubscribe();
    }

    @Override
    public void unsubscribe(String... channels) {
        super.unsubscribe(channels);
    }

    @Override
    public void subscribe(String... channels) {
        super.subscribe(channels);
    }

    @Override
    public void psubscribe(String... patterns) {
        super.psubscribe(patterns);
    }

    @Override
    public void punsubscribe() {
        super.punsubscribe();
    }

    @Override
    public void punsubscribe(String... patterns) {
        super.punsubscribe(patterns);
    }

    @Override
    public void onMessage(String channel, String message) {
        System.out.println("channel:" + channel + "receives message :" + message);
        this.unsubscribe();
    }

    /**
     * key过期监控
     * @param pattern
     * @param channel
     * @param message
     */
    @Override
    public void onPMessage(String pattern, String channel, String message) {
        System.out.println("onPMessage pattern "
                + pattern + " " + channel + " 过期的key是：" + message);

        Jedis jedis = null;
        try {
             jedis = new Jedis("192.168.2.145",5020);
        }finally {
            jedis.close();
        }

        String redis_message = jedis.get(message);
        System.out.println("redis_message============"+redis_message);
    }

    @Override
    public void onSubscribe(String channel, int subscribedChannels) {
        System.out.println("channel:" + channel + " is been subscribed:" + subscribedChannels);
    }

    @Override
    public void onPUnsubscribe(String pattern, int subscribedChannels) {

    }

    @Override
    public void onPSubscribe(String pattern, int subscribedChannels) {
        System.out.println("onPSubscribe "
                + pattern + " " + subscribedChannels);
    }

    @Override
    public void onUnsubscribe(String channel, int subscribedChannels) {
        System.out.println("channel:" + channel + "is been unsubscribed:" + subscribedChannels);
    }
}
