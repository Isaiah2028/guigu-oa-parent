package com.atguigu.auth.service;

import com.atguigu.model.system.SysUser;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author IsaiahLu
 * @since 2023-03-12
 */
public interface SysUserService extends IService<SysUser> {

    /**
     * 更新用户状态
     * @param id
     * @param status
     */
     void updateStatus(Long id, Integer status);

}
