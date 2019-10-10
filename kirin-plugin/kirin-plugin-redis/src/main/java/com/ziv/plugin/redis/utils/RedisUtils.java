package com.ziv.plugin.redis.utils;

import com.alibaba.fastjson.JSON;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;
import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * redis工具类
 *
 * @author ziv
 * @date 2019-10-09
 */
@Component
public class RedisUtils {

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @Resource
    private ValueOperations<String, String> valueOperations;

    /**
     * 默认过期时间/s
     */
    public final static long DEFAULT_EXPIRE = 60 * 30;

    /**
     * 不过期
     */
    public final static long NOT_EXPIRE = -1;

    /**
     * 设置过期时间
     * @param key key值
     * @param expire 过期时间
     */
    public void setExpire(String key, long expire){
        if(expire != NOT_EXPIRE){
            redisTemplate.expire(key, expire, TimeUnit.SECONDS);
        }
    }

    /**
     * 设置过期时间-默认时间
     * @param key key值
     */
    public void setExpire(String key){
        redisTemplate.expire(key, DEFAULT_EXPIRE, TimeUnit.SECONDS);
    }

    /**
     * 设置为永不过期
     * @param key key值
     */
    public void setNotExpire(String key) {
        redisTemplate.expire(key, NOT_EXPIRE, TimeUnit.SECONDS);
    }

    /**
     * 设置键值对(永不过期)
     * @param key 键
     * @param value 值
     */
    public void set(String key, Object value){
        valueOperations.set(key, toJson(value));
    }

    /**
     * 设置键值对-设置过期时间
     * @param key 键
     * @param value 值
     * @param expire 过期时间
     */
    public void setWithExpire(String key, Object value, long expire){
        set(key, value);
        if(expire != NOT_EXPIRE){
            redisTemplate.expire(key, expire, TimeUnit.SECONDS);
        }
    }

    /**
     * 设置键值对-默认过期时间
     * @param key 键
     * @param value 值
     */
    public void setWithDefaultExpire(String key, Object value) {
        set(key, value);
        redisTemplate.expire(key, DEFAULT_EXPIRE, TimeUnit.SECONDS);
    }

    /**
     * 获取值并刷新过期时间
     * @param key
     * @param clazz
     * @param expire 过期时间
     * @param <T>
     * @return T
     */
    public <T> T getWithExpire(String key, Class<T> clazz, long expire) {
        T t = get(key, clazz);
        if(expire != NOT_EXPIRE){
            redisTemplate.expire(key, expire, TimeUnit.SECONDS);
        }
        return t;
    }

    /**
     * 获取值
     * @param key
     * @param clazz
     * @param <T>
     * @return T
     */
    public <T> T get(String key, Class<T> clazz) {
        String value = valueOperations.get(key);
        return value == null ? null : fromJson(value, clazz);
    }

    /**
     * 获取值
     * @param key
     * @return String
     */
    public String get(String key) {
        String value = valueOperations.get(key);
        return value;
    }

    /**
     * 获取值并刷新过期时间
     * @param key
     * @param expire 过期时间
     * @return String
     */
    public String getWithExpire(String key, long expire) {
        String value = get(key);
        if(expire != NOT_EXPIRE){
            redisTemplate.expire(key, expire, TimeUnit.SECONDS);
        }
        return value;
    }

    /**
     * 获取过期时间
     * @param key
     * @return long
     */
    public long getExpire(String key) {
        return redisTemplate.getExpire(key, TimeUnit.SECONDS);
    }

    /**
     * 删除
     * @param key
     */
    public void delete(String key) {
        redisTemplate.delete(key);
    }

    /**
     * Object转成JSON数据
     * @param object 需要转换的对象
     * @return String
     */
    private String toJson(Object object){
        if(object instanceof Integer || object instanceof Long || object instanceof Float ||
                object instanceof Double || object instanceof Boolean || object instanceof String){
            return String.valueOf(object);
        }
        return JSON.toJSONString(object);
    }

    /**
     * JSON数据转成Object
     * @param json json字符串
     * @param clazz 类
     * @return T
     */
    private <T> T fromJson(String json, Class<T> clazz){
        return JSON.parseObject(json, clazz);
    }

}
