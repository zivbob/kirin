package com.ziv.controller;

import com.ziv.common.response.JsonResult;
import com.ziv.entity.SysUser;
import com.ziv.plugin.redis.utils.RedisHashUtils;
import com.ziv.plugin.redis.utils.RedisUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.annotation.Resource;

@RefreshScope
@RestController
@RequestMapping(value = "test")
public class Test {

    @Value("${useLocalCache}")
    private String useLocalCache;

    @Value("${cache}")
    private String cache;

    @Resource
    private RedisUtils redisUtils;

    @Resource
    private RedisHashUtils redisHashUtils;

    @RequestMapping(value = "useLocalCache")
    public JsonResult getUseLocalCache() {
        return JsonResult.success(useLocalCache);
    }

    @RequestMapping(value = "cache")
    public JsonResult getCache() {
        return JsonResult.success(cache);
    }

    @RequestMapping(value = "redisSet")
    public JsonResult redisSet(String str) {
        redisUtils.set(str, str);
        return JsonResult.success();
    }

    @RequestMapping(value = "redisSetObject")
    public JsonResult redisSetObject(String name, String password) {
        SysUser user = new SysUser();
        user.setName(name);
        user.setPassword(password);
        redisUtils.set(name, user);
        return JsonResult.success();
    }

    @RequestMapping(value = "redisGet")
    public JsonResult redisGet(String key) {
        String value = redisUtils.get(key);
        return JsonResult.success(value);
    }

    @RequestMapping(value = "redisGetObject")
    public JsonResult redisGetObject(String name) {
        SysUser user = redisUtils.get(name, SysUser.class);
        return JsonResult.success(user);
    }

    @RequestMapping(value = "redisGetExpire")
    public JsonResult redisGetExpire(String name) {
        return JsonResult.success(redisUtils.getExpire(name));
    }

    @RequestMapping(value = "redisSetExpire")
    public JsonResult redisSetExpire(String name, Long expire) {
        redisUtils.setExpire(name, expire);
        return JsonResult.success();
    }

    @RequestMapping(value = "redisSetHash")
    public JsonResult redisSetHash(String name, String password) throws IllegalAccessException {
        SysUser user = new SysUser();
        user.setName(name);
        user.setPassword(password);
        redisHashUtils.set(name, user);
        return JsonResult.success();
    }

    @RequestMapping(value = "redisGetHash")
    public JsonResult redisSetHash(String name) throws IllegalAccessException {
        try {
            return JsonResult.success(redisHashUtils.get(name, SysUser.class));
        } catch (Exception e) {
            return JsonResult.error(e.getMessage());
        }
    }

    @RequestMapping(value = "delete")
    public JsonResult delete(String name) throws IllegalAccessException {
        redisUtils.delete(name);
        return JsonResult.success();
    }
}