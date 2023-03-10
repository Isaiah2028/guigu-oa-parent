package com.atguigu.auth.service.impl;

import com.atguigu.auth.mapper.SysRoleMapper;
import com.atguigu.auth.service.SysSysRoleService;
import com.atguigu.model.system.SysRole;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author: IsaiahLu
 * @date: 2023/3/9 23:56
 */
@Service
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements SysSysRoleService {

/*
    service继承了ServiceImpl后 已经把注入mapper的工作交给了ServiceImpl<>去完成
    @Autowired
    SysRoleMapper sysRoleMapper;*/

}
