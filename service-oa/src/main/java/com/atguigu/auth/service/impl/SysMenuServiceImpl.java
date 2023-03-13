package com.atguigu.auth.service.impl;

import com.atguigu.auth.mapper.SysMenuMapper;
import com.atguigu.auth.service.SysMenuService;
import com.atguigu.auth.util.MenuHelper;
import com.atguigu.common.config.exception.GuiguException;
import com.atguigu.model.system.SysMenu;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

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
        wrapper.eq(SysMenu::getParentId,id);

        Integer selectCount = baseMapper.selectCount(wrapper);

        if (selectCount > 0){
             throw  new GuiguException(201,"该菜单下有子菜单，菜单不能删除");
        }
        baseMapper.deleteById(id);

    }
}
