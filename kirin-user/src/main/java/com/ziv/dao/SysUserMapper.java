package com.ziv.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ziv.entity.SysUser;
import org.apache.ibatis.annotations.Mapper;

/**
 * 数据持久层接口
 *
 * @author ziv
 * @data 2019-10-11
 */
@Mapper
public interface SysUserMapper extends BaseMapper<SysUser> {

    /**
     * 查询分页
     * @param page 分页参数
     * @return Page<SysUser>
     */
    Page<SysUser> findPage(Page<SysUser> page);

    /**
     * 通过用户名查询用户信息
     * @param userName 用户名
     * @return SysUser
     */
    SysUser selectByUserName(String userName);
}