package com.atguigu.system.test;

import com.atguigu.model.system.SysRole;
import com.atguigu.system.mapper.SysRoleMapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;

/**
 * @author gwq
 * @create 2023-02-20 17:08
 */
@SpringBootTest
public class SysRoleMapperTest {
    @Autowired
    SysRoleMapper sysRoleMapper;

    @Test
    public void findAll(){
        List<SysRole> list = sysRoleMapper.selectList(null);
        for (SysRole role :
                list) {
            System.out.println(role);
        }
    }
    @Test
    public void add(){
        SysRole sysRole = new SysRole();
        sysRole.setRoleName("测试角色2");
        sysRole.setRoleCode("testManager2");
        sysRole.setDescription("用于测试add");
        int rows = sysRoleMapper.insert(sysRole);
        System.out.println(rows);
    }
    @Test
    public void update(){
        SysRole sysRole = sysRoleMapper.selectById("1627843478285201410");
        sysRole.setDescription("尚硅谷用于测试add");
        sysRoleMapper.updateById(sysRole);
    }
    @Test
    public void deleteById(){
        sysRoleMapper.deleteById("1627843478285201410");
    }
    @Test
    public void deleteBatchIds(){
        sysRoleMapper.deleteBatchIds(Arrays.asList("1627843478285201409","1627843478285201411"));
    }

    @Test
    public void select(){
        QueryWrapper<SysRole> wrapper = new QueryWrapper<>();
        wrapper.eq("role_name","用户管理员");
        List<SysRole> sysRoles = sysRoleMapper.selectList(wrapper);
        for (SysRole role :
                sysRoles) {
            System.out.println(role);
        }
    }
    @Test
    public void testDelete(){
        QueryWrapper<SysRole> wrapper = new QueryWrapper<>();
        wrapper.like("role_name","测试角色");
        sysRoleMapper.delete(wrapper);
    }
}
