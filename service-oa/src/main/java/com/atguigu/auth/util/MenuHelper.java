package com.atguigu.auth.util;

import com.atguigu.model.system.SysMenu;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: IsaiahLu
 * @date: 2023/3/13 14:24
 */
public class MenuHelper {

    public static List<SysMenu> BuildTree(List<SysMenu> sysMenuList) {
        //创建list集合 用于最终数据
        List<SysMenu> tress = new ArrayList<>();

        //遍历所有菜单数据
        for (SysMenu sysMenu : sysMenuList) {

            //递归入口 parentId = 0
            if (sysMenu.getParentId().longValue() == 0) {
                tress.add(getChildren(sysMenu, sysMenuList));
            }
        }
        return tress;
    }

    public static SysMenu getChildren(SysMenu sysMenu, List<SysMenu> sysMenuList) {

        sysMenu.setChildren(new ArrayList<SysMenu>());

        //遍历所有菜单的数据，判断id 和parentId 的关系
        for (SysMenu menu : sysMenuList) {
            if (sysMenu.getId().longValue() == menu.getParentId().longValue()){
                //数据为空情况
                if (sysMenu.getChildren() == null){
                    sysMenu.setChildren(new ArrayList<>());
                }
                sysMenu.getChildren().add( getChildren(menu,sysMenuList));
            }
        }
        return sysMenu;
    }
}
