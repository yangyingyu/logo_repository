package cn.itsource.logo.util;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/*
* 工具类的封装：主要封装1.连接池的封装  2 api--get/set的 封装
*
*
* */
public class RedisUtil {
    static JedisPool jedisPool =null;
    static {
        GenericObjectPoolConfig poolConfig =new JedisPoolConfig();
        //参数设置
        poolConfig.setMaxTotal(30);//设置最大连接数
        poolConfig.setMaxIdle(10);//设置最大空闲数
        poolConfig.setMaxWaitMillis(3*1000); //超时时间
        poolConfig.setTestOnBorrow(true); //借的时候进行测试

        String host ="127.0.0.1";
        int port = 6379;
        int timeout=5*1000;
        String password ="root";
        jedisPool =new JedisPool(poolConfig ,host ,port ,timeout ,password ) ;}
    //对jedis 的api进行封装
    public static void set(String key,String value){
        //获取jedis实例
        Jedis jedis = jedisPool.getResource();
        //操作api
        try {
            jedis.set(key , value );
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            //关闭资源
            jedis.close();
        }
    }

    public static String get(String key) {
        //获取jedis实例
        Jedis jedis = jedisPool.getResource();
        //操作api
        try {
            return jedis.get(key);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            //关闭资源
            jedis.close();
        }
    }
}

















