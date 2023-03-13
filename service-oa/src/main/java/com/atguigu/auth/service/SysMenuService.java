package com.atguigu.auth.service;

import com.atguigu.model.system.SysMenu;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 菜单表 服务类
 * </p>
 *
 * @author IsaiahLu
 * @since 2023-03-13
 */
public interface SysMenuService extends IService<SysMenu> {

    /**
     * 查询所有菜单
     * @return
     */
    List<SysMenu> findNodes();

    /**
     * 删除该菜单，如果有子菜单，则不能删除
     * @param id
     */
    void removeMenuById(Long id);
}
