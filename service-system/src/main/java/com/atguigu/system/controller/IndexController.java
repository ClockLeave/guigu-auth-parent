package com.atguigu.system.controller;

import com.atguigu.common.result.Result;
import com.atguigu.common.utils.JwtHelper;
import com.atguigu.common.utils.MD5;
import com.atguigu.model.system.SysUser;
import com.atguigu.model.vo.LoginVo;
import com.atguigu.model.vo.SysUserQueryVo;
import com.atguigu.system.exception.GuiguException;
import com.atguigu.system.service.SysUserService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @author gwq
 * @create 2023-02-23 17:23
 */
@Api(tags = "用户登录接口")
@RestController
@RequestMapping("/admin/system/index")
public class IndexController {
    @Autowired
    SysUserService sysUserService;
    //{"code":20000,"data":{"token":"admin-token"}}
    @PostMapping("/login")
    public Result login(@RequestBody LoginVo loginVo){

        String username = loginVo.getUsername();
        String password = MD5.encrypt(loginVo.getPassword());

        SysUser sysUser = sysUserService.getUserInfoByUsername(username);
        if (sysUser==null){
            throw  new GuiguException(20001,"用户名不存在");
        }
        if(!sysUser.getPassword().equals(password)){
            throw new GuiguException(20001,"密码错误");
        }
        if (sysUser.getStatus().intValue()==0){
            throw new GuiguException(20001,"用户已被禁用，请联系管理员");
        }

        String token = JwtHelper.createToken(sysUser.getId(), sysUser.getUsername());
        Map<String, Object> map = new HashMap<>();
        map.put("token",token);
        return Result.ok(map);
    }

    /*
    {
        "code":20000,
        "data":{
            "roles":["admin"],
            "introduction":"I am a super administrator",
            "avatar":"https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif",
            "name":"Super Admin"
            }
    }
    * */
    @GetMapping("/info")
    public Result info(HttpServletRequest request){
        String token = request.getHeader("token");
        String username = JwtHelper.getUsername(token);
        Map<String, Object> map =sysUserService.getUserInfo(username);
        return Result.ok(map);
    }
}
