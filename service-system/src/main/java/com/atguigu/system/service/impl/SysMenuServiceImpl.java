package com.atguigu.system.service.impl;


import com.atguigu.model.system.SysMenu;
import com.atguigu.model.system.SysRoleMenu;
import com.atguigu.model.vo.AssginMenuVo;
import com.atguigu.system.exception.GuiguException;
import com.atguigu.system.mapper.SysMenuMapper;
import com.atguigu.system.mapper.SysRoleMenuMapper;
import com.atguigu.system.service.SysMenuService;
import com.atguigu.system.util.MenuHepler;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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

    @Autowired
    SysRoleMenuMapper sysRoleMenuMapper;
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

    @Override
    public List<SysMenu> findMenuByRoleId(String roleId) {

        QueryWrapper<SysMenu> wrapperMenu = new QueryWrapper<>();
        wrapperMenu.eq("status",1);
        List<SysMenu> sysMenus = baseMapper.selectList(wrapperMenu);
        QueryWrapper<SysRoleMenu> wrapperRoleMenu = new QueryWrapper<>();
        wrapperRoleMenu.eq("role_id",roleId);
        List<SysRoleMenu> sysRoleMenus = sysRoleMenuMapper.selectList(wrapperRoleMenu);
        ArrayList<String> menusId = new ArrayList<>();
        for (SysRoleMenu sysRoleMenu : sysRoleMenus) {
            menusId.add(sysRoleMenu.getMenuId());
        }

        ArrayList<SysMenu> menus = new ArrayList<>();
        for (SysMenu sysMenu : sysMenus) {
            sysMenu.setSelect(menusId.contains(sysMenu.getId()));
        }
        return MenuHepler.buildTree(sysMenus);
    }

    @Override
    public void doAssign(AssginMenuVo assginMenuVo) {
        QueryWrapper<SysRoleMenu> wrapperRoleMenu = new QueryWrapper<>();
        wrapperRoleMenu.eq("role_id",assginMenuVo.getRoleId());

        sysRoleMenuMapper.delete(wrapperRoleMenu);

        for (String menuId : assginMenuVo.getMenuIdList()) {
            SysRoleMenu sysRoleMenu = new SysRoleMenu();
            sysRoleMenu.setRoleId(assginMenuVo.getRoleId());
            sysRoleMenu.setMenuId(menuId);
            sysRoleMenuMapper.insert(sysRoleMenu);
        }


    }

}
