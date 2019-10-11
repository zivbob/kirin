package com.ziv.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

/**
 * 系统用户实体类
 *
 * @author ziv
 * @since 2019-10-11
 */
@Data
@TableName(value = "sys_user")
public class SysUser {
    /**
     * 主键
     */
    private Integer userID;
    
    /**
     * 业务主键
     */
    @TableId(type = IdType.ID_WORKER_STR)
    private String userKey;
    
    /**
     * 用户名
     */
    @TableField(whereStrategy = FieldStrategy.NOT_EMPTY)
    private String userName;
    
    /**
     * 密码
     */
    @TableField
    private String password;
    
    /**
     * 验证码
     */
    @TableField
    private String verificationCode;
    
    /**
     * 删除标识 0-未删除 1-删除
     */
    @TableField
    @TableLogic(value = "0", delval = "1")
    private Object deleteFlag;

}