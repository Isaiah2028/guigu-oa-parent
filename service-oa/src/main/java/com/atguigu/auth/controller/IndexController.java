package com.atguigu.auth.controller;

import com.atguigu.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: IsaiahLu
 * @date: 2023/3/11 15:24
 */
@Api(tags = "后台登录管理接口")
@RestController
@RequestMapping(value = "/admin/system/index", produces = "application/json")
public class IndexController {

    @ApiOperation("登录接口")
    @RequestMapping(path = "/login", method = RequestMethod.POST)
    public Result login() {
        Map<String, Object> map = new HashMap<>();
        map.put("token","admin");
        return Result.ok(map);
    }

    /**
     * 用户信息
     * @return
     */
    @ApiOperation("用户信息接口")
    @RequestMapping(path = "/info", method = RequestMethod.GET)
    public Result info() {
        Map<String, Object> map = new HashMap<>();
        map.put("roles","[admin]");
        map.put("name","admin");
        map.put("avatar","https://oss.aliyuncs.com/aliyun_id_photo_bucket/default_handsome.jpg");
        return Result.ok(map);
    }

    /**
     * 退出
     * @return
     */
    @ApiOperation("用户退出接口")
    @RequestMapping(path = "/logout", method = RequestMethod.POST)
    public Result logout(){
        return Result.ok();
    }

}
