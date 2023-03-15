package com.atguigu.system.controller;

import com.atguigu.common.result.Result;
import com.atguigu.model.system.SysRole;
import com.atguigu.model.vo.AssginRoleVo;
import com.atguigu.model.vo.SysRoleQueryVo;
import com.atguigu.system.exception.GuiguException;
import com.atguigu.system.service.SysRoleService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @author gwq
 * @create 2023-02-22 14:55
 */
@Api(tags = "角色管理")
@RestController
@RequestMapping("/admin/system/sysRole")
public class SysRoleController {
    @Autowired
    SysRoleService sysRoleService;

    @ApiOperation(value = "获取全部角色列表")
    @GetMapping("findAll")
    public Result findAll(){
        return Result.ok(sysRoleService.list());
    }
    @ApiOperation(value = "根据id删除角色")
    @DeleteMapping("remove/{id}")
    public Result remove(@PathVariable(value = "id") Long id){
        if (sysRoleService.removeById(id)){
            return Result.ok();
        }else {
            return Result.fail();
        }
    }
    @ApiOperation(value = "分页查询")
    @GetMapping("{page}/{limit}")
    public Result index(
            @ApiParam(name = "page",value = "当前页码",required = true)
            @PathVariable("page") Long page,
            @ApiParam(name = "limit",value = "当前记录数",required = true)
            @PathVariable("limit") Long limit,
            SysRoleQueryVo roleQueryVo){
        Page<SysRole> pageParam=new Page<>(page,limit);
        IPage<SysRole> pageModel = sysRoleService.selectPage(pageParam, roleQueryVo);
        return Result.ok(pageModel);
    }
    @ApiOperation("添加角色")
    @PostMapping("save")
    public Result saveRole(@RequestBody SysRole sysRole){
        boolean isSuccess = sysRoleService.save(sysRole);
        if (isSuccess){
            return Result.ok();
        }else {
            return Result.fail();
        }
    }
    @ApiOperation("根据id查询")
    @GetMapping("/findById/{id}")
    public Result findById(@PathVariable("id") Long id){
        SysRole role = sysRoleService.getById(id);
        return Result.ok(role);
    }
    @ApiOperation("修改角色")
    @PutMapping("/update")
    public Result update(@RequestBody SysRole sysRole){
        boolean isSuccess = sysRoleService.updateById(sysRole);
        if (isSuccess){
            return Result.ok();
        }else {
            return Result.fail();
        }
    }
    @ApiOperation("批量删除")
    @DeleteMapping("/batchRemove")
    public Result batchRemove(@RequestBody List<Long> ids){
        boolean isSuccess = sysRoleService.removeByIds(ids);
        if (isSuccess){
            return Result.ok();
        }else {
            return Result.fail();
        }
    }
    @ApiOperation("获取用户所有权限")
    @GetMapping("/toAssign/{userId}")
    public Result toAssign(@PathVariable String userId){
        Map<String,Object> roleMap = sysRoleService.getRolesByUserId(userId);
        return Result.ok(roleMap);
    }
    @ApiOperation("设置用户所有权限")
    @PostMapping("doAssign")
    public Result doAssign(@RequestBody AssginRoleVo assginRoleVo){
        sysRoleService.doAssign(assginRoleVo);
        return Result.ok();
    }


}
