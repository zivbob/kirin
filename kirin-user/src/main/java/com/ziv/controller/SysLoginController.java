package com.ziv.controller;

import com.ziv.common.response.JsonResult;
import com.ziv.common.token.AuthorisationInfo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 系统登录控制类
 *
 * @author ziv
 * @date 2109-10-14
 */
@RestController
@RequestMapping(value = "sys")
public class SysLoginController {

    @GetMapping(value = "login")
    public JsonResult<AuthorisationInfo> login (String userName, String paswword) {
        JsonResult result;
        try {

            result = JsonResult.success();
        } catch (Exception e) {
            result = JsonResult.error(e.getMessage());
        }
        return result;
    }
}
