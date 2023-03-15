package com.atguigu.auth.mapper;

import com.atguigu.model.system.SysMenu;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 菜单表 Mapper 接口
 * </p>
 *
 * @author IsaiahLu
 * @since 2023-03-13
 */
@Mapper
public interface SysMenuMapper extends BaseMapper<SysMenu> {

    /**
     * 非管理员，根据userId查询可以操作按钮列表
     * @param userId userId
     * @return
     */
    List<SysMenu> findMenuListByUserId(@Param("userId") Long userId);
}
