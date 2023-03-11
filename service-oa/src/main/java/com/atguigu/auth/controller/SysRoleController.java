package com.atguigu.auth.controller;

import com.atguigu.auth.service.SysSysRoleService;
import com.atguigu.common.config.exception.GuiguException;
import com.atguigu.model.system.SysRole;
import com.atguigu.result.Result;
import com.atguigu.vo.system.SysRoleQueryVo;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author: IsaiahLu
 * @date: 2023/3/10 0:08
 */
@Api(tags = "角色管理接口")
@RestController
@RequestMapping(value = "/admin/system/sysRole", produces = "application/json")
public class SysRoleController {

    @Autowired
    SysSysRoleService sysSysRoleService;


    @ApiOperation("测试接口")
    @RequestMapping(path = "/test", method = RequestMethod.GET)
    public String test() {

        try {
            int a = 10/0;
        }catch (Exception e){
            throw new  GuiguException(200,"出现自定义异常...");
        }

        return "wusisi";
    }

    @ApiOperation("查询所有角色")
    @RequestMapping(path = "/findAll", method = RequestMethod.GET)
    public Result findAll() {
        List<SysRole> list = sysSysRoleService.list();
        System.out.println("list: " + list);
        return Result.ok(list);
    }


    @ApiOperation("分页查询角色")
    @RequestMapping(path = "/{page}/{limit}", method = RequestMethod.GET)
    public Result pageQueryRole(@PathVariable Long page,
                                @PathVariable Long limit,
                                SysRoleQueryVo sysRoleQueryVo) {

        Page<SysRole> pageParam = new Page<>(page, limit);
        LambdaQueryWrapper<SysRole> wrapper = new LambdaQueryWrapper<SysRole>();
        String roleName = sysRoleQueryVo.getRoleName();

        //条件查询参数不为空
        if (!StringUtils.isBlank(roleName)) {
            wrapper.like(SysRole::getRoleName, roleName);
        }
        IPage<SysRole> pageModel = sysSysRoleService.page(pageParam, wrapper);

        return Result.ok(pageModel);
    }

    @ApiOperation("添加角色")
    @RequestMapping(path = "/addRole", method = RequestMethod.POST)
    public Result addRole(@RequestBody SysRole sysRole) {

        boolean isAdded = sysSysRoleService.save(sysRole);
        if (isAdded) {
            return Result.ok();
        } else {
            return Result.fail();
        }
    }

    @ApiOperation("根据id查询角色")
    @RequestMapping(path = "/getRole/{id}", method = RequestMethod.GET)
    public Result queryById(@PathVariable Long id) {

        SysRole sysRole = sysSysRoleService.getById(Long.valueOf(id));

        if (!ObjectUtils.isEmpty(sysRole)) {

            return Result.ok(sysRole);
        } else {
            return Result.fail("id错误或用户不存在...");
        }

    }

    @ApiOperation("修改角色")
    @RequestMapping(path = "/update", method = RequestMethod.POST)
    public Result updateRole(@RequestBody SysRole sysRole) {

        boolean isUpdated = sysSysRoleService.updateById(sysRole);
        if (isUpdated) {
            return Result.ok(sysRole);
        } else {
            return Result.fail("修改角色失败...");
        }
    }

    @ApiOperation("根据id删除")
    @RequestMapping(path = "/delete/{id}", method = RequestMethod.GET)
    public Result deleteById(@PathVariable Long id) {

        boolean isDeleted = sysSysRoleService.removeById(id);
        if (isDeleted) {
            return Result.ok(isDeleted);
        } else {
            return Result.fail("删除失败...");
        }
    }

    @ApiOperation("根据批量id删除")
    @RequestMapping(path = "/deleteByIds", method = RequestMethod.DELETE)
    public Result deleteByIds(@RequestBody List<Long> idList) {

        boolean isDeleted = sysSysRoleService.removeByIds(idList);
        if (isDeleted) {
            return Result.ok(isDeleted);
        } else {
            return Result.fail("批量删除失败...");
        }

    }


}
