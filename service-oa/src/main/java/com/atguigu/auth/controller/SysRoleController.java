package com.atguigu.auth.controller;

import com.atguigu.auth.service.SysSysRoleService;
import com.atguigu.model.system.SysRole;
import com.atguigu.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Author: IsaiahLu
 * @date: 2023/3/10 0:08
 */
@RestController
@RequestMapping(value = "/admin/system/sysRole", produces = "application/json")
public class SysRoleController {

    @Autowired
    SysSysRoleService sysSysRoleService;



    @RequestMapping(path = "/test", method = RequestMethod.GET)
    public String test() {
        return "wusisi";
    }

    @RequestMapping(path = "/findAll", method = RequestMethod.GET)
    public Result findAll(){

        List<SysRole> list = sysSysRoleService.list();
        System.out.println("list: " + list);

        return Result.ok(list);
    }



}
