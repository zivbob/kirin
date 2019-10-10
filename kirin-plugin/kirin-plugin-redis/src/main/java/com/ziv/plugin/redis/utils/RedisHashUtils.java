package com.ziv.plugin.redis.utils;

import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import javax.annotation.Resource;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

/**
 * redis哈希工具类
 *
 * @author ziv
 * @date 2109-10-10
 */
@Component
public class RedisHashUtils {
    @Resource
    private HashOperations<String, String, Object> hashOperations;

    /**
     * 设置键值对(永不过期)
     * @param key
     * @param value
     * @throws IllegalAccessException
     */
    public void set(String key, Object value) throws IllegalAccessException {
        Class cla = value.getClass();
        Field[] fields = cla.getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            hashOperations.put(key, field.getName(), field.get(value));
        }
    }

    /**
     * 获取值
     * @param key
     * @param clazz
     * @param <T>
     * @return T
     */
    public <T> T get(String key, Class<T> clazz) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        Constructor constructor = clazz.getConstructor();
        T t = (T)constructor.newInstance();
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            field.set(t, hashOperations.get(key, field.getName()));
        }
        return t;
    }
}
