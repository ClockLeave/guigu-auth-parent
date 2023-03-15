package com.atguigu.system.service.impl;


import com.atguigu.model.system.SysMenu;
import com.atguigu.system.exception.GuiguException;
import com.atguigu.system.mapper.SysMenuMapper;
import com.atguigu.system.service.SysMenuService;
import com.atguigu.system.util.MenuHepler;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 菜单表 服务实现类
 * </p>
 *
 * @author atguigu
 * @since 2023-03-07
 */
@Service
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenu> implements SysMenuService {

    @Override
    public List<SysMenu> findNodes() {
        List<SysMenu> list = baseMapper.selectList(null);
        List<SysMenu> result = new MenuHepler().buildTree(list);
        return result;
    }

    @Override
    public void removeMenuById(String id) {
        QueryWrapper<SysMenu> wrapper = new QueryWrapper<>();
        wrapper.eq("parent_id", id);
        Integer count = baseMapper.selectCount(wrapper);
        if ((count > 0)) {
            throw new GuiguException(201, "请先删除子菜单");
        } else{
            baseMapper.deleteById(id);
        }
    }
}
