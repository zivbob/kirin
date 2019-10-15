package com.ziv.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ziv.common.token.AuthorisationInfo;
import com.ziv.dao.SysUserMapper;
import com.ziv.entity.SysUser;
import com.ziv.service.SysUserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {

    @Resource
    private SysUserMapper sysUserMapper;

    /**
     * 添加用户信息
     * @param user 用户信息
     */
    @Override
    public void add(SysUser user) {
        sysUserMapper.insert(user);
    }

    @Override
    public Page<SysUser> findPage(Integer pageNo, Integer pageSize) {
        return sysUserMapper.findPage(new Page<>(pageNo, pageSize));
    }

    @Override
    public SysUser selectByUserName(String userName) {
        return sysUserMapper.selectByUserName(userName);
    }
}
