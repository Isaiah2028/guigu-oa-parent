package com.atguigu.auth.controller;

import com.atguigu.auth.service.SysMenuService;
import com.atguigu.auth.service.SysUserService;
import com.atguigu.auth.util.JwtHelper;
import com.atguigu.common.config.exception.GuiguException;
import com.atguigu.common.util.MD5;
import com.atguigu.model.system.SysUser;
import com.atguigu.common.result.Result;
import com.atguigu.vo.system.LoginVo;
import com.atguigu.vo.system.RouterVo;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import io.jsonwebtoken.Jwt;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: IsaiahLu
 * @date: 2023/3/11 15:24
 */
@Api(tags = "后台登录管理接口")
@RestController
@RequestMapping(value = "/admin/system/index", produces = "application/json")
public class IndexController {

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private SysMenuService sysMenuService;


    @ApiOperation("登录接口")
    @RequestMapping(path = "/login", method = RequestMethod.POST)
    public Result login(@RequestBody LoginVo loginVo) {

//        Map<String, Object> map = new HashMap<>();
//        map.put("token","admin");
//        return Result.ok(map);
//
        //获取输入的用户名和密码
        //根据用户名查询数据库
        Long start = System.currentTimeMillis();

        String username = loginVo.getUsername();

        LambdaQueryWrapper<SysUser> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysUser::getUsername, username);

        SysUser sysUser = sysUserService.getOne(wrapper);

        //用户信息是否存在
        if (ObjectUtils.isEmpty(sysUser)) {
            throw new GuiguException(201, "用户不存在");
        }
        //判断密码
        String passwordDB = sysUser.getPassword(); //数据库中密码
        String passwordInput = loginVo.getPassword(); //输入的密码
        String encrypt = MD5.encrypt(passwordInput);
        if (!passwordDB.equals(encrypt)) {
            throw new GuiguException(201, "密码错误");
        }
        //用户是否被禁用
        if (sysUser.getStatus().intValue() == 0) {
            throw new GuiguException(201, "用户已被禁用");
        }

        //使用jwt生成token 写入响应头
        String token = JwtHelper.createToken(sysUser.getId(), sysUser.getUsername());
        //返回
        Map<String, Object> map = new HashMap<>();
        map.put("token", token);

        System.out.println("登录接口耗时" + (System.currentTimeMillis() - start));

        return Result.ok(map);
    }

    /**
     * 用户信息
     * @return
     */
    @ApiOperation("用户信息接口")
    @RequestMapping(path = "/info", method = RequestMethod.GET)
    public Result info(HttpServletRequest request) {

        //1从请求头中获取用户信息 获取请求头中的token字符串
        String token = request.getHeader("token");

        //2从token字符串中获取用户id,或者用户名称
        Long userId =JwtHelper.getUserId(token);

        //3根据用户id查询数据库 把用户信息获取出来
        SysUser sysUser = sysUserService.getById(userId);

        //4根据用户id获取用户可以操作菜单列表
        //查询数据库 动态构建路由结构 进行显示
        List<RouterVo> routerLis = sysMenuService.findUserMenuListByUserId(userId);

        //5 根据id获取用户可以操作按钮列表
        List<String> permsList = sysMenuService.findUserPermsByUserId(userId);

        //返回相应数据

        //返回用户可以操作菜单
        //返回用户可以操作按钮

        Map<String, Object> map = new HashMap<>();
        map.put("roles", "[admin]");
        map.put("name", sysUser.getName());
        map.put("avatar", "https://oss.aliyuncs.com/aliyun_id_photo_bucket/default_handsome.jpg");
        map.put("routers",routerLis);
        map.put("buttons",permsList);

        return Result.ok(map);
    }

    /**
     * 退出
     * @return
     */
    @ApiOperation("用户退出接口")
    @RequestMapping(path = "/logout", method = RequestMethod.POST)
    public Result logout() {
        return Result.ok();
    }

}
