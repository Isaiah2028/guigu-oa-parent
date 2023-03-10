package com.atguigu.auth;

import com.atguigu.auth.mapper.SysRoleMapper;
import com.atguigu.auth.service.SysSysRoleService;
import com.atguigu.model.system.SysRole;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

/**
 * @Author: IsaiahLu
 * @date: 2023/3/9 21:42
 */
@SpringBootTest
public class SysRoleMapperTest {

    @Autowired
    private SysRoleMapper sysRoleMapper;

    @Autowired
    private SysSysRoleService sysSysRoleService;


    @Test
    public void testSelectList() {

        System.out.println(("----- selectAll method test ------"));

        List<SysRole> roleList = sysRoleMapper.selectList(null);

        System.out.println("roleList:" + roleList);

        roleList.forEach(System.out::print);
    }


    @Test
    public void testAddSystemRole() {

        System.out.println(("----- AddSystemRole method test ------"));

        SysRole sysRole = new SysRole();
        sysRole.setRoleName("角色管理员");
        sysRole.setRoleCode("role");
        sysRole.setDescription("角色管理员");

        int influentRow = sysRoleMapper.insert(sysRole);

        System.out.println("影响行数:"+influentRow);
        System.out.println("行id:" + sysRole.getId());
    }

    @Test
    public void testUpdate() {

        System.out.println(("----- updateRole method test ------"));

        SysRole sysRole = sysRoleMapper.selectById(9L);

        sysRole.setRoleName("atguitu角色管理员");

        int influentRow = sysRoleMapper.updateById(sysRole);
        System.out.println("影响行数:"+influentRow);
        System.out.println("行id:" + sysRole.getId());
    }

    @Test
    public void testDelete() {

        System.out.println(("----- testDelete method test ------"));

        int sysRole = sysRoleMapper.deleteById(9L);
        System.out.println("影响行数:"+sysRole);

    }


    /*test mybatis封装Service */

    @Test
    public void testServiceImpl(){
        List<SysRole> sysRoles = sysSysRoleService.list();
        System.out.println("extends IService<SysRole>:::{} "+sysRoles);


    }
}
