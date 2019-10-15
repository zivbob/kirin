package com.ziv.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ziv.common.token.AuthorisationInfo;
import com.ziv.entity.SysUser;

/**
 * 业务逻辑类
 *
 * @author ziv
 * @data 2019-10-11 09:53:22
 */
public interface SysUserService extends IService<SysUser> {

    /**
     * 添加用户
     * @param user 用户信息
     */
    void add(SysUser user);

    /**
     * 查询分页
     * @param pageNo 页码
     * @param pageSize 页面显示上限
     * @return Page<SysUser>
     */
    Page<SysUser> findPage(Integer pageNo, Integer pageSize);

    /**
     * 登录
     * @param userName 用户名
     * @return SysUser
     */
    SysUser selectByUserName(String userName);
}