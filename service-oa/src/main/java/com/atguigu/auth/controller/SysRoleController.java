package com.atguigu.auth.controller;

import com.atguigu.auth.service.SysSysRoleService;
import com.atguigu.model.system.SysRole;
import com.atguigu.common.result.Result;
import com.atguigu.vo.system.AssginRoleVo;
import com.atguigu.vo.system.SysRoleQueryVo;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

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

/*
        try {
            int a = 10 / 0;
        } catch (Exception e) {
            throw new GuiguException(200, "出现自定义异常...");
        }
*/
        return "sisi";
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
        System.out.println("分页数据：" + pageModel);
        return Result.ok(pageModel);
    }

    @ApiOperation("添加角色")
    @RequestMapping(path = "/addRole", method = RequestMethod.POST)
    public Result addRole(@RequestBody SysRole sysRole) {

        System.out.println("添加参数：" + sysRole);

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
    @RequestMapping(path = "/update", method = RequestMethod.PUT)
    public Result updateRole(@RequestBody SysRole sysRole) {

        boolean isUpdated = sysSysRoleService.updateById(sysRole);
        if (isUpdated) {
            return Result.ok(sysRole);
        } else {
            return Result.fail("修改角色失败...");
        }
    }

    @ApiOperation("根据id删除")
    @RequestMapping(path = "/remove/{id}", method = RequestMethod.DELETE)
    public Result deleteById(@PathVariable("id") Long id) {

//        Long.parseLong(id);
//        long paramId = Long.parseLong(id);;
        // String paramId = id.toString();
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



    //查询所有角色，和当前用户所属角色
    @ApiOperation("获取角色")
    @RequestMapping(path = "/toAssign/{userId}", method = RequestMethod.GET)
    public Result toAssign(@PathVariable("userId") Long userId){

       Map<String,Object> map =  sysSysRoleService.findRoleDataByUserId(userId);

       return Result.ok(map);
    }

    //为用户分配角色
    @ApiOperation("为用户分配角色")
    @RequestMapping(path = "/doAssign", method = RequestMethod.POST)
    public Result doAssign(@RequestBody AssginRoleVo assginRoleVo){

       sysSysRoleService.doAssign(assginRoleVo);


        return Result.ok();
    }


}
