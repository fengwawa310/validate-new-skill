package cn.com.cintel.validatenewskill.controller.v1;

import cn.com.cintel.validatenewskill.support.utils.IDGenerator;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * @Company Name:
 * @Author: sky
 * @CreatDate: 2018/9/7 13:43
 * @ClassName: cn.com.cintel.validatenewskill.controller.v1
 * @Description: 8位数的订单号生成，考虑分布式锁，使用redis
 * @Modified By:
 * @ModifyDate: 2018/9/7 13:43
 */
@RestController
public class ResCodeGenerater {

    @Resource
    JedisPool jedisPool;

    private static final String TASK_ID = "redis_res_code";
    private static final String RES_CODE = "res_code";
    Jedis jedis;

    @GetMapping(value = "/getResCode",produces = "application/json;charset=utf-8")
    public String getResCode(){
        String resCode = "";
        jedis = jedisPool.getResource();

        //生成8为随机增长的预约单号
        boolean lock = false;
        String random_value = "";
        try{
            //获取锁
            Map<String,Object> map = getLock(TASK_ID);
            lock = (boolean) map.get("lock");
            random_value = (String) map.get("random_value");

            System.out.println("lock:::"+lock);
            if(lock){
                //生成预约单号
                int resIncreate = generateRangeRadomInt(1,9);

                //根据redis的自增生成下一个预约号
                jedis.incrBy(RES_CODE,resIncreate);
                System.out.println(jedis.get(RES_CODE));

                //构造8位数
                resCode = makeResCode(jedis.get(RES_CODE));
            }
        }finally{
            if(lock){
                //释放锁
                releaseLock(TASK_ID,random_value);
            }
        }


        jedis.close();
        return resCode;
    }

    //生成任何区间的整数
    public int generateRangeRadomInt(int min,int max){
        Random random = new Random();
        int s = random.nextInt(max)%(max-min+1) + min;
        return s;
    }

    //生成8位预约号
    public String makeResCode(String res_ori){
        int ori_length = res_ori.length();
        //需要补0的位数
        int still = 8 - ori_length;

        StringBuffer sb = new StringBuffer();
        for (int i=0; i < still; i++){
            sb.append("0");
        }
        sb.append(res_ori);
        return sb.toString();
    }


    //获取锁状态
    public Map<String,Object> getLock(String taskId){

        Jedis jedis = jedisPool.getResource();
        Map<String,Object> map = new HashMap<>();

        if(jedis.exists(taskId)){
            jedis.close();
            map.put("lock",false);
            return map;
        }else{
            String random_value = IDGenerator.getorderNo();
            /**
             * 存储数据到缓存中，并制定过期时间和当Key存在时是否覆盖。
             *
             * @param key
             * @param value
             * @param nxxx
             * nxxx的值只能取NX或者XX，如果取NX，则只有当key不存在是才进行set，如果取XX，则只有当key已经存在时才进行set
             * @param expx expx的值只能取EX或者PX，代表数据过期时间的单位，EX代表秒，PX代表毫秒。
             * @param time 过期时间，单位是expx所代表的单位。
             * String set(String key, String value, String nxxx, String expx, long time);
             * @return
             */
            jedis.set(taskId,random_value,"NX","PX",2000);

            map.put("lock",true);
            map.put("random_value",random_value);

            jedis.close();
            return map;
        }
    }

    //释放锁
    public void releaseLock(String taskId,String random_value){
        Jedis jedis = jedisPool.getResource();

        //随机数为了防止误删除taskId
        if (jedis.get(taskId) == random_value){
            jedis.del(taskId);
        }

        jedis.close();
    }


}
