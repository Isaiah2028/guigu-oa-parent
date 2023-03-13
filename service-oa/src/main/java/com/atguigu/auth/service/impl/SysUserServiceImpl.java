package com.atguigu.auth.service.impl;

import com.atguigu.model.system.SysUser;
import com.atguigu.auth.mapper.SysUserMapper;
import com.atguigu.auth.service.SysUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author IsaiahLu
 * @since 2023-03-12
 */
@Service
@Slf4j
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {


    /**
     * 更新用户状态
     *
     * @param id     用户id
     * @param status 用户状态
     */
    @Override
    public void updateStatus(Long id, Integer status) {

        //根据userId查询用户对象
        SysUser sysUser = baseMapper.selectById(id);
        log.info("修改status前：" + sysUser);


        //设置用户状态值
//        if (status != 0 || status != 1){ //数据不合法
//            return;
//        }else {
//            sysUser.setStatus(status);
//        }

        sysUser.setStatus(status);

        //调用方法进行修改
        baseMapper.updateById(sysUser);

    }
}
