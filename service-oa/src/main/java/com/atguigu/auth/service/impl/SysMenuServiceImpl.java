package com.atguigu.auth.service.impl;

import com.atguigu.auth.mapper.SysMenuMapper;
import com.atguigu.auth.service.SysMenuService;
import com.atguigu.auth.service.SysRoleMenuService;
import com.atguigu.auth.util.MenuHelper;
import com.atguigu.common.config.exception.GuiguException;
import com.atguigu.model.system.SysMenu;
import com.atguigu.model.system.SysRoleMenu;
import com.atguigu.vo.system.AssginMenuVo;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 菜单表 服务实现类
 * </p>
 *
 * @author IsaiahLu
 * @since 2023-03-13
 */
@Service
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenu> implements SysMenuService {


    @Autowired
    private SysRoleMenuService sysRoleMenuService;

    /**
     * @return
     */
    @Override
    public List<SysMenu> findNodes() {
        //查询所有数据
        List<SysMenu> sysMenuList = baseMapper.selectList(null);
        //递归，构建成树形结构
        List<SysMenu> resultList = MenuHelper.BuildTree(sysMenuList);
        return resultList;
    }

    /**
     * 删除该菜单，如果有子菜单，则不能删除
     *
     * @param id
     */
    @Override
    public void removeMenuById(Long id) {

        //判断当前菜单是否有子菜单
        LambdaQueryWrapper<SysMenu> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysMenu::getParentId, id);

        Integer selectCount = baseMapper.selectCount(wrapper);

        if (selectCount > 0) {
            throw new GuiguException(201, "该菜单下有子菜单，菜单不能删除");
        }
        baseMapper.deleteById(id);

    }

    /**
     * 查询所有菜单和角色分配的菜单
     *
     * @param roleId 菜单id
     * @return 菜单列表集合
     */
    @Override
    public List<SysMenu> findMenuByRoleId(Long roleId) {

        //1查询所有菜单 添加条件 status = 1 表示菜单可用

        LambdaQueryWrapper<SysMenu> wrapperSysMenu = new LambdaQueryWrapper<>();
        wrapperSysMenu.eq(SysMenu::getStatus, 1);
        List<SysMenu> allSysMenuList = baseMapper.selectList(wrapperSysMenu);

        //2根据角色id (roleId)对应的菜单id  角色菜单关系表李 角色id对应的所有菜单id

        LambdaQueryWrapper<SysRoleMenu> wrapperSysRoleMenu = new LambdaQueryWrapper<>();
        wrapperSysRoleMenu.eq(SysRoleMenu::getRoleId, roleId);
        List<SysRoleMenu> sysRoleMenuList = sysRoleMenuService.list(wrapperSysRoleMenu);


        //3根据获取的菜单id 获取对应的菜单对象
        List<Long> menuIdList = sysRoleMenuList.stream().map(c -> c.getMenuId()).collect(Collectors.toList());

        //3.1拿着菜单id 和所有菜单集合里面id进行比较，如果相同，封装起来
        allSysMenuList.stream().forEach(item -> {
            if (menuIdList.contains(item.getId())) {
                item.setSelect(true);
            } else {
                item.setSelect(false);
            }
        });
        //返回规定树形显示格式菜单列表
        List<SysMenu> sysMenuList = MenuHelper.BuildTree(allSysMenuList);

        return sysMenuList;
    }

    /**
     * 为角色分配菜单
     *
     * @param assginMenuVo 前端入参（角色id,菜单id）
     */
    @Override
    public void doAssign(AssginMenuVo assginMenuVo) {

        //1根据角色id 删除菜单角色表，分配数据
        LambdaQueryWrapper<SysRoleMenu> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysRoleMenu::getRoleId, assginMenuVo.getRoleId());
        sysRoleMenuService.remove(wrapper);


        //2从参数里面获取角色新分配菜单id列表，进行遍历，把每个id数据添加到菜单角色关系表

        List<Long> menuIdList = assginMenuVo.getMenuIdList();
//        menuIdList.stream().forEach(roleId -> baseMapper.insert());

        for (Long menuId : menuIdList) {
            if (StringUtils.isEmpty(menuId)) {
                continue;
            }
            SysRoleMenu sysRoleMenu = new SysRoleMenu();
            sysRoleMenu.setMenuId(menuId);
            sysRoleMenu.setRoleId(assginMenuVo.getRoleId());
            sysRoleMenuService.save(sysRoleMenu);
        }
    }

}
