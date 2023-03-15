package com.atguigu.system.test;

import com.atguigu.model.system.SysRole;
import com.atguigu.system.service.SysRoleService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

/**
 * @author gwq
 * @create 2023-02-22 14:31
 */
@SpringBootTest
public class SysRoleServiceTest {
    @Autowired
    SysRoleService service;
    @Test
    public void add(){
        SysRole sysRole = new SysRole();
        sysRole.setRoleName("张三");
        sysRole.setRoleCode("test...");
        sysRole.setDescription("test描述");

        service.save(sysRole);
    }
    @Test
    public void findAll(){
        List<SysRole> list = service.list();
        for (SysRole role :
                list) {
            System.out.println(role);
        }
    }
    @Test
    public void delete(){
        QueryWrapper<SysRole> wrapper=new QueryWrapper<>();
        wrapper.eq("role_name","张三");
        List<SysRole> list = service.list(wrapper);
        for (SysRole role :
                list) {
            service.removeById(role.getId());
        }
    }
}
