package com.atguigu.system.util;


import com.atguigu.model.system.SysMenu;

import java.util.ArrayList;
import java.util.List;

/**
 * @author gwq
 * @create 2023-03-07 15:53
 */
public class MenuHepler {
    //生成树状
    public List<SysMenu> buildTree(List<SysMenu> list) {
        List<SysMenu> tree = new ArrayList<>();
        for (SysMenu sysMenu : list) {
            if ((sysMenu.getParentId().longValue() == 0)) {
                tree.add(findChildren(sysMenu, list));
            }
        }
        return tree;
    }

    private SysMenu findChildren(SysMenu sysMenu, List<SysMenu> list) {
        sysMenu.setChildren(new ArrayList<>());
        for (SysMenu menu : list) {
            if (menu.getParentId().longValue()==Long.parseLong(sysMenu.getId())) {
                sysMenu.getChildren().add(findChildren(menu,list));
            }
        }
        return sysMenu;
    }

}
