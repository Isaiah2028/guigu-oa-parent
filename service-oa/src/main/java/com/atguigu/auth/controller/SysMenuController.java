package com.atguigu.auth.controller;


import com.atguigu.auth.service.SysMenuService;
import com.atguigu.model.system.SysMenu;
import com.atguigu.common.result.Result;
import com.atguigu.vo.system.AssginMenuVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 菜单表 前端控制器
 * </p>
 *
 * @author IsaiahLu
 * @since 2023-03-13
 */

@Api(tags = "菜单管理接口")
@RestController
@RequestMapping(value = "/admin/system/sysMenu", produces = "application/json")
public class SysMenuController {
    @Autowired
    private SysMenuService sysMenuService;


    @ApiOperation(value = "获取菜单")
    @GetMapping("findNodes")
    public Result findNode() {

        List<SysMenu> list = sysMenuService.findNodes();
        return Result.ok(list);

    }

    @ApiOperation(value = "新增菜单")
    @PostMapping("save")
    public Result save(@RequestBody SysMenu permission) {
        sysMenuService.save(permission);
        return Result.ok();
    }

    @ApiOperation(value = "修改菜单")
    @PutMapping("update")
    public Result updateById(@RequestBody SysMenu permission) {
        sysMenuService.updateById(permission);
        return Result.ok();
    }

    @ApiOperation(value = "删除菜单")
    @DeleteMapping("remove/{id}")
    public Result remove(@PathVariable Long id) {

        //删除菜单，如果有子菜单，不能删除


        sysMenuService.removeMenuById(id);
        return Result.ok();
    }


    //菜单分派接口

    //查询所有菜单和角色分配的菜单
    @GetMapping("/toAssign/{roleId}")
    public Result toAssign(@PathVariable Long roleId) {

        List<SysMenu> list = sysMenuService.findMenuByRoleId(roleId);

        return Result.ok(list);
    }

    @ApiOperation(value = "给角色分配权限")
    @PostMapping("/doAssign")
    public Result doAssign(@RequestBody AssginMenuVo assginMenuVo) {

        sysMenuService.doAssign(assginMenuVo);
        return Result.ok();
    }


}

