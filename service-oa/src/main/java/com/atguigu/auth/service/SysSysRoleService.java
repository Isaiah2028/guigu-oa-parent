package com.atguigu.auth.service;

import com.atguigu.model.system.SysRole;
import com.atguigu.vo.system.AssginRoleVo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * @Author: IsaiahLu
 * @date: 2023/3/9 23:56
 */
public interface SysSysRoleService extends IService<SysRole> {


    /**
     * 查询所有角色，和当前用户所属角色
     * @param userId
     * @return
     */
    Map<String, Object> findRoleDataByUserId(Long userId);


    /**
     * 为用户分配角色
     * @param assginRoleVo
     */
    void doAssign(AssginRoleVo assginRoleVo);
}
