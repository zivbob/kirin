package com.ziv.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ziv.entity.SysUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import com.ziv.service.SysUserService;
import response.JsonResult;

/**
 * 控制类
 *
 * @author ziv
 * @since 2019-10-11
 */
@Slf4j
@RestController
@RequestMapping("sys/user")
public class SysUserController {
    
    @Resource
    private SysUserService sysUserService;

    @PostMapping
    public JsonResult add(@RequestBody SysUser user) {
        sysUserService.add(user);
        return JsonResult.success();
    }

    @DeleteMapping
    public JsonResult delete(String key) {
        sysUserService.removeById(key);
        return JsonResult.success();
    }

    @GetMapping(value = "page")
    public JsonResult findPage(Integer pageNo, Integer pageSize) {
        Page<SysUser> page = sysUserService.findPage(pageNo, pageSize);
        return JsonResult.success(page);
    }
}