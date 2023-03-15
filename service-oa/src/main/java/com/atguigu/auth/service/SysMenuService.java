package com.atguigu.auth.service;

import com.atguigu.model.system.SysMenu;
import com.atguigu.vo.system.AssginMenuVo;
import com.atguigu.vo.system.RouterVo;
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

    /**
     * 根据菜单id查询所有菜单列表
     * @param roleId
     * @return 菜单列表集合
     */
    List<SysMenu> findMenuByRoleId(Long roleId);

    /**
     * 为角色分配菜单
     * @param assginMenuVo  前端入参（角色id,菜单id）
     */
    void doAssign(AssginMenuVo assginMenuVo);


    /**
     * 根据用户id获取用户可以操作菜单列表
     * @param userId
     * @return
     */
    List<RouterVo> findUserMenuListByUserId(Long userId);

    /**
     * 根据id获取用户可以操作按钮列表
     * @param userId
     * @return
     */
    List<String> findUserPermsByUserId(Long userId);

}
