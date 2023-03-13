package com.atguigu.auth.controller;

import com.atguigu.auth.service.SysUserService;
import com.atguigu.model.system.SysUser;
import com.atguigu.result.Result;
import com.atguigu.vo.system.SysUserQueryVo;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

/**
 * @Author: IsaiahLu
 * @date: 2023/3/12 16:29
 */
@Slf4j
@Api(tags = "用户管理接口")
@RestController
@RequestMapping(value = "/admin/system/sysUser", produces = "application/json")
public class SysUserController {

    @Autowired
    private SysUserService sysUserService;

    @ApiOperation(value = "测试接口")
    @RequestMapping(path = "/test", method = RequestMethod.GET)
    public Result test() {
        return Result.ok("测试成功");

    }

    @ApiOperation(value = "用户条件分页查询")
    @RequestMapping(path = "{page}/{limit}", method = RequestMethod.GET)
    public Result getUser(@PathVariable Long page,
                          @PathVariable Long limit,
                          SysUserQueryVo sysUserQueryVo) {

        log.info("page" + page);
        log.info("limit" + limit);
        log.info("UserVo" + sysUserQueryVo);

        //创建page对象
        Page<SysUser> pageParam = new Page<>(page, limit);
        //封装条件，判断条件值不为空
        LambdaQueryWrapper<SysUser> wrapper = new LambdaQueryWrapper<>();
        //获取条件值
        String username = sysUserQueryVo.getKeyword();
        String createTimeBegin = sysUserQueryVo.getCreateTimeBegin();
        String createTimeEnd = sysUserQueryVo.getCreateTimeEnd();
        //判断条件值不为空
        //like 模糊查询
        if (!StringUtils.isEmpty(username)) {
            wrapper.like(SysUser::getUsername, username);
        }
        //ge 大于等于
        if (!StringUtils.isEmpty(createTimeBegin)) {
            wrapper.ge(SysUser::getCreateTime, createTimeBegin);
        }
        //le 小于等于
        if (!StringUtils.isEmpty(createTimeEnd)) {
            wrapper.le(SysUser::getCreateTime, createTimeEnd);
        }

        //调用mp的方法实现条件分页查询
        IPage<SysUser> pageModel = sysUserService.page(pageParam, wrapper);
        return Result.ok(pageModel);

    }

    @ApiOperation(value = "根据id获取用户")
    @RequestMapping(path = "/get/{id}", method = RequestMethod.GET)
    public Result get(@PathVariable Long id) {
        SysUser user = sysUserService.getById(id);
        return Result.ok(user);
    }


    @ApiOperation(value = "添加用户")
    @RequestMapping(path = "/save", method = RequestMethod.POST)
    public Result add(@RequestBody SysUser user) {

        boolean isSaveid = sysUserService.save(user);
        if (isSaveid) {
            return Result.ok("用户保存成功");
        } else {
            return Result.fail("保存用户失败");
        }
    }

    @ApiOperation(value = "更新用户")
    @RequestMapping(path = "/update", method = RequestMethod.PUT)
    public Result updateById(@RequestBody SysUser sysUser) {
        boolean isupdated = sysUserService.updateById(sysUser);
        if (isupdated) {
            return Result.ok();
        } else {
            return Result.fail();
        }
    }

    @ApiOperation(value = "更新用户状态")
    @RequestMapping(path = "/updateStatus/{id}/{status}", method = RequestMethod.GET)
    public Result updateStatus(@PathVariable("id") Long id,
                               @PathVariable("status") Integer status) {

        sysUserService.updateStatus(id, status);
        return Result.ok();
    }


    @ApiOperation(value = "删除用户")
    @RequestMapping(path = "/remove/{id}", method = RequestMethod.DELETE)
    public Result removeById(@PathVariable("id") Long id) {
        boolean isupdated = sysUserService.removeById(id);
        if (isupdated) {
            return Result.ok();
        } else {
            return Result.fail();
        }
    }


}