package com.yuhuayuan.core.service.redis;

import com.alibaba.fastjson.TypeReference;
import redis.clients.jedis.JedisPool;

import java.lang.reflect.Type;
import java.util.Map;
import java.util.Set;

/**
 * Created by cl on 2017/3/1.
 */
public interface RedisCacheService {
    boolean exists(String key);
    boolean hexists(String key, String field);
    long incr(String key, int ex);
    boolean hdel(String key, String... fields);
    boolean hset(String key, String field, Object value);
    <T> T hget(String key, String field, Class<T> type);
    <T> T hget(String key, String field, TypeReference<T> type);
    Set<String> hkeys(String key);
    Map<String, String> hgetall(String key);
    boolean put(String key, Object val, int ex);
    boolean put(String key, Object val);
    boolean putex(String key, Object val, int expire);
    <T> T get(String key, Type type);
    String get(String key)throws Exception;
    void set(String key, String value)throws Exception;
    <T> T get(String key, Class<T> type);
    <T> T get(String key, TypeReference<T> type);
    boolean del(String... key);
    Set<String> keys(String prefix);
    Long sadd(String key, String... members);
    Set<String> smembers(String key);
    Long scard(String key);
    Long lpush(String key, String... members);
    String lpop(String key);
    Long expire(String key, int seconds);
    boolean sismember(String key, String member);
    int getDefaultExpire();
    void setDefaultExpire(int defaultExpire);
    JedisPool getJedisPool();
    void setJedisPool(JedisPool jedisPool);
}