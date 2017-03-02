package com.yuhuayuan.core.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.yuhuayuan.core.service.redis.RedisCacheService;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.exceptions.JedisConnectionException;

import java.lang.reflect.Type;
import java.util.Map;
import java.util.Set;


@Component
public class RedisCacheServiceImpl implements RedisCacheService{

  
    private JedisPool jedisPool;

   
    private int defaultExpire = 3600;

 
    public boolean exists(String key) {
        Jedis jedis = null;
        try {
            jedis = getJedisPool().getResource();
            return jedis.exists(key);
        } catch (JedisConnectionException e) {
            if (null != jedis) {
                getJedisPool().returnBrokenResource(jedis);
                jedis = null;
            }
            return false;
        } finally {
            if (null != jedis)
                getJedisPool().returnResource(jedis);

        }
    }

  
    public boolean hexists(String key, String field) {
        Jedis jedis = null;
        try {
            jedis = getJedisPool().getResource();
            return jedis.hexists(key, field);
        } catch (JedisConnectionException e) {
            if (null != jedis) {
                getJedisPool().returnBrokenResource(jedis);
                jedis = null;
            }
            return false;
        } finally {
            if (null != jedis)
                getJedisPool().returnResource(jedis);

        }
    }


    public long incr(String key, int ex) {
        Jedis jedis = null;
        try {
            jedis = getJedisPool().getResource();
            long v = jedis.incr(key);
            if (ex > 0)
                jedis.expire(key, ex);
            return v;
        } catch (JedisConnectionException e) {
            if (null != jedis) {
                getJedisPool().returnBrokenResource(jedis);
                jedis = null;
            }
            return 0;
        } finally {
            if (null != jedis)
                getJedisPool().returnResource(jedis);
        }
    }

    /**
     * 删除
     * 
     * @param key
     * @param fields
     * @return
     */
    public boolean hdel(String key, String... fields) {
        Jedis jedis = null;
        try {
            jedis = getJedisPool().getResource();
            jedis.hdel(key, fields);
        } catch (JedisConnectionException e) {
            if (null != jedis) {
                getJedisPool().returnBrokenResource(jedis);
                jedis = null;
            }
            return false;
        } finally {
            if (null != jedis)
                getJedisPool().returnResource(jedis);
        }
        return true;
    }

    /**
     * 设置
     * 
     * @param key
     * @param field
     * @param value
     * @return
     */
    public boolean hset(String key, String field, Object value) {
        Jedis jedis = null;
        try {
            jedis = getJedisPool().getResource();
            jedis.hset(key.getBytes(), field.getBytes(), JSON.toJSONBytes(value));
        } catch (JedisConnectionException e) {
            if (null != jedis) {
                getJedisPool().returnBrokenResource(jedis);
                jedis = null;
            }
            return false;
        } finally {
            if (null != jedis)
                getJedisPool().returnResource(jedis);
        }
        return true;
    }

    /**
     * 获取
     * 
     * @param key
     * @param field
     * @param type
     * @return
     */
    public <T> T hget(String key, String field, Class<T> type) {
        Jedis jedis = null;
        try {
            jedis = getJedisPool().getResource();
            String text = jedis.hget(key, field);
            return JSON.parseObject(text, type);
        } catch (JedisConnectionException e) {
            if (null != jedis) {
                getJedisPool().returnBrokenResource(jedis);
                jedis = null;
            }
            return null;
        } finally {
            if (null != jedis)
                getJedisPool().returnResource(jedis);
        }
    }

    /**
     * 获取某个值
     * 
     * @param key
     * @param field
     * @param type
     * @return
     */
    public <T> T hget(String key, String field, TypeReference<T> type) {
        Jedis jedis = null;
        try {
            jedis = getJedisPool().getResource();
            String text = jedis.hget(key, field);
            return JSON.parseObject(text, type);
        } catch (JedisConnectionException e) {
            if (null != jedis) {
                getJedisPool().returnBrokenResource(jedis);
                jedis = null;
            }
            return null;
        } finally {
            if (null != jedis)
                getJedisPool().returnResource(jedis);
        }
    }

    public Set<String> hkeys(String key){
        Jedis jedis = null;
        try {
            jedis = getJedisPool().getResource();
            return jedis.hkeys(key);
        } catch (JedisConnectionException e) {
            if (null != jedis) {
                getJedisPool().returnBrokenResource(jedis);
                jedis = null;
            }
            return null;
        } finally {
            if (null != jedis)
                getJedisPool().returnResource(jedis);
        }
    }

    /**
     * 获取全部
     * 
     * @param key
     * @return
     */
    public Map<String, String> hgetall(String key) {
        Jedis jedis = null;
        try {
            jedis = getJedisPool().getResource();
            return jedis.hgetAll(key);
        } catch (JedisConnectionException e) {
            if (null != jedis) {
                getJedisPool().returnBrokenResource(jedis);
                jedis = null;
            }
            return null;
        } finally {
            if (null != jedis)
                getJedisPool().returnResource(jedis);
        }
    }

//    public Map<String, String> hmget(String key, String... fields) {
//        Jedis jedis = null;
//        try {
//            jedis = jedisPool.getResource();
//            List<String> l = jedis.hmget(key, fields);
//            Map<String, String> map = new HashMap<>(fields.length);
//            for (int i = 0, j = fields.length; i < j; i++) {
//                map.put(fields[i], l.get(i));
//            }
//            return map;
//        } catch (JedisConnectionException e) {
//            if (null != jedis) {
//                jedisPool.returnBrokenResource(jedis);
//                jedis = null;
//            }
//            return new HashMap<>();
//        } finally {
//            if (null != jedis)
//                jedisPool.returnResource(jedis);
//        }
//    }

    // ------------------for hash-------

    // 单个的值
    public boolean put(String key, Object val, int ex) {
        Jedis jedis = null;
        try {
            jedis = getJedisPool().getResource();
            jedis.setex(key.getBytes(), ex, JSON.toJSONBytes(val));
        } catch (JedisConnectionException e) {
            if (null != jedis) {
                getJedisPool().returnBrokenResource(jedis);
                jedis = null;
            }
            return false;
        } finally {
            if (null != jedis)
                getJedisPool().returnResource(jedis);
        }
        return true;
    }

    public boolean put(String key, Object val) {
        return put(key, val, getDefaultExpire());
    }
    
    
    public boolean putex(String key, Object val, int expire) {
        return put(key, val, expire);
    }

    public <T> T get(String key, Type type) {
        Jedis jedis = null;
        try {
            jedis = getJedisPool().getResource();
            byte[] bytes = jedis.get(key.getBytes());
            if (bytes == null)
                return null;
            return JSON.parseObject(bytes, type);
        } catch (JedisConnectionException e) {
            if (null != jedis) {
                getJedisPool().returnBrokenResource(jedis);
                jedis = null;
            }
            return null;
        } finally {
            if (null != jedis)
                getJedisPool().returnResource(jedis);
        }
    }

//    public <S, T> Map<S, T> mget(String prefix, S[] keys, Class<T> type) {
//        Jedis jedis = null;
//        Map<S, T> ret = new HashMap<S, T>(keys.length);
//        try {
//            jedis = jedisPool.getResource();
//            String[] strKeys = new String[keys.length];
//            for (int i = 0, j = keys.length; i < j; i++)
//                strKeys[i] = prefix + keys[i];
//
//            List<String> strRet = jedis.mget(strKeys);
//            for (int i = 0, j = keys.length; i < j; i++) {
//                String val = strRet.get(i);
//                ret.put(keys[i], val == null ? null : JSON.parseObject(val, type));
//            }
//
//        } catch (JedisConnectionException e) {
//            if (null != jedis) {
//                jedisPool.returnBrokenResource(jedis);
//                jedis = null;
//            }
//            return new HashMap<>();
//        } finally {
//            if (null != jedis)
//                jedisPool.returnResource(jedis);
//        }
//        return ret;
//    }
    public String get(String key) throws Exception
    {
    	Jedis jedis = null;
        try {
        	jedis = getJedisPool().getResource();
           return jedis.get(key);
        } catch (Exception e) {
            throw e;     
        } finally {
        	  if (null != jedis) {
                  getJedisPool().returnBrokenResource(jedis);
                  jedis = null;
              }
        }
    }
    
    public void set(String key, String value) throws Exception
    {
    	Jedis jedis = null;
        try {
        	jedis = getJedisPool().getResource();
            jedis.set(key, value);
        } catch (Exception e) {
            throw e;     
        } finally {
        	  if (null != jedis) {
                  getJedisPool().returnBrokenResource(jedis);
                  jedis = null;
              }
        }
    }
    

    public <T> T get(String key, Class<T> type) {
        Jedis jedis = null;
        try {
            jedis = getJedisPool().getResource();
            byte[] bytes = jedis.get(key.getBytes());
            
            if (bytes == null)
                return null;
            return JSON.parseObject(bytes, type);
        } catch (JedisConnectionException e) {
            if (null != jedis) {
                getJedisPool().returnBrokenResource(jedis);
                jedis = null;
            }
            return null;
        } finally {
            if (null != jedis)
                getJedisPool().returnResource(jedis);
        }
    }

    public <T> T get(String key, TypeReference<T> type) {
        Jedis jedis = null;
        try {
            jedis = getJedisPool().getResource();
            String text = jedis.get(key);
            if (text == null)
                return null;
            return JSON.parseObject(text, type);
        } catch (JedisConnectionException e) {
            if (null != jedis) {
                getJedisPool().returnBrokenResource(jedis);
                jedis = null;
            }
            return null;
        } finally {
            if (null != jedis)
                getJedisPool().returnResource(jedis);
        }
    }

    public boolean del(String... key) {
        Jedis jedis = null;
        try {
            jedis = getJedisPool().getResource();
            for (String k : key)
                jedis.del(k);
            return true;
        } catch (JedisConnectionException e) {
            if (null != jedis) {
                getJedisPool().returnBrokenResource(jedis);
                jedis = null;
            }
            return false;
        } finally {
            if (null != jedis)
                getJedisPool().returnResource(jedis);
        }
    }

    
    public Set<String> keys(String prefix) {
        Jedis jedis = null;
        try {
            jedis = getJedisPool().getResource();
            Set<String> v = jedis.keys(prefix + '*');
            return v;
        } catch (JedisConnectionException e) {
            if (null != jedis) {
                getJedisPool().returnBrokenResource(jedis);
                jedis = null;
            }
            return null;
        } finally {
            if (null != jedis)
                getJedisPool().returnResource(jedis);
        }
    }

   
    public Long sadd(String key, String... members) {
        Jedis jedis = null;
        try {
            jedis = getJedisPool().getResource();
            Long v = jedis.sadd(key, members);
            return v;
        } catch (JedisConnectionException e) {
            if (null != jedis) {
                getJedisPool().returnBrokenResource(jedis);
                jedis = null;
            }
            return null;
        } finally {
            if (null != jedis)
                getJedisPool().returnResource(jedis);
        }
    }

  
    public Set<String> smembers(String key) {
        Jedis jedis = null;
        try {
            jedis = getJedisPool().getResource();
            Set<String> v = jedis.smembers(key);
            return v;
        } catch (JedisConnectionException e) {
            if (null != jedis) {
                getJedisPool().returnBrokenResource(jedis);
                jedis = null;
            }
            return null;
        } finally {
            if (null != jedis)
                getJedisPool().returnResource(jedis);
        }
    }

    
    public Long scard(String key) {
        Jedis jedis = null;
        try {
            jedis = getJedisPool().getResource();
            Long v = jedis.scard(key);
            return v;
        } catch (JedisConnectionException e) {
            if (null != jedis) {
                getJedisPool().returnBrokenResource(jedis);
                jedis = null;
            }
            return null;
        } finally {
            if (null != jedis)
                getJedisPool().returnResource(jedis);
        }
    }


    public Long lpush(String key, String... members) {
        Jedis jedis = null;
        try {
            jedis = getJedisPool().getResource();
            return jedis.lpush(key, members);
        } catch (JedisConnectionException e) {
            if (null != jedis) {
                getJedisPool().returnBrokenResource(jedis);
                jedis = null;
            }
            return null;
        } finally {
            if (null != jedis)
                getJedisPool().returnResource(jedis);
        }
    }


    public String lpop(String key) {
        Jedis jedis = null;
        try {
            jedis = getJedisPool().getResource();
            return jedis.lpop(key);
        } catch (JedisConnectionException e) {
            if (null != jedis) {
                getJedisPool().returnBrokenResource(jedis);
                jedis = null;
            }
            return null;
        } finally {
            if (null != jedis)
                getJedisPool().returnResource(jedis);
        }
    }

   
    public Long expire(String key, int seconds) {
        Jedis jedis = null;
        try {
            jedis = getJedisPool().getResource();
            return jedis.expire(key.getBytes(), seconds);
        } catch (JedisConnectionException e) {
            if (null != jedis) {
                getJedisPool().returnBrokenResource(jedis);
                jedis = null;
            }
            return 0L;
        } finally {
            if (null != jedis)
                getJedisPool().returnResource(jedis);
        }
    }

    
    public boolean sismember(String key, String member) {
        Jedis jedis = null;
        try {
            jedis = getJedisPool().getResource();
            return jedis.sismember(key.getBytes(), member.getBytes());
        } catch (JedisConnectionException e) {
            if (null != jedis) {
                getJedisPool().returnBrokenResource(jedis);
                jedis = null;
            }
            return false;
        } finally {
            if (null != jedis)
                getJedisPool().returnResource(jedis);
        }
    }


	public int getDefaultExpire() {
		return defaultExpire;
	}


	public void setDefaultExpire(int defaultExpire) {
		this.defaultExpire = defaultExpire;
	}


	public JedisPool getJedisPool() {
		return jedisPool;
	}


	public void setJedisPool(JedisPool jedisPool) {
		this.jedisPool = jedisPool;
	}
}