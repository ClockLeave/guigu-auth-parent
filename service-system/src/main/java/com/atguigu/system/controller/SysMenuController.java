package com.atguigu.system.controller;


import com.atguigu.common.result.Result;
import com.atguigu.model.system.SysMenu;
import com.atguigu.model.vo.AssginMenuVo;
import com.atguigu.system.service.SysMenuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 菜单表 前端控制器
 * </p>
 *
 * @author atguigu
 * @since 2023-03-07
 */
@Api(tags = "菜单管理")
@RestController
@RequestMapping("/admin/system/sysMenu")
public class SysMenuController {
    @Autowired
    SysMenuService sysMenuService;

    @ApiOperation("菜单列表")
    @GetMapping("findNodes")
    public Result findNodes(){
        List<SysMenu> list = sysMenuService.findNodes();
        return Result.ok(list);
    }

    @ApiOperation("添加菜单")
    @PostMapping("save")
    public Result save(@RequestBody SysMenu sysMenu){
        sysMenuService.save(sysMenu);
        return Result.ok();
    }

    @ApiOperation("根据id查询")
    @GetMapping("findNode/{id}")
    public Result findNode(@PathVariable String id){
        SysMenu sysMenu = sysMenuService.getById(id);
        return Result.ok(sysMenu);

    }

    @ApiOperation("修改菜单")
    @PostMapping("update")
    public Result update(@RequestBody SysMenu sysMenu){
        sysMenuService.updateById(sysMenu);
        return Result.ok();
    }

    @ApiOperation("删除菜单")
    @DeleteMapping("remove/{id}")
    public Result remove(@PathVariable String id){
        sysMenuService.removeMenuById(id);
        return Result.ok();
    }


    @ApiOperation(value = "根据角色获取菜单")
    @GetMapping("/toAssign/{roleId}")
    public Result toAssign(@PathVariable String roleId){

        List<SysMenu> menus = sysMenuService.findMenuByRoleId(roleId);
        return Result.ok(menus);
    }

    @ApiOperation(value = "给角色分配权限")
    @PostMapping("/doAssign")
    public Result doAssign(@RequestBody AssginMenuVo assginMenuVo){
        sysMenuService.doAssign(assginMenuVo);
        return Result.ok();
    }
}

