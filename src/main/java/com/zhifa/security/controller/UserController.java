package com.zhifa.security.controller;

import com.zhifa.security.entity.UserInfo;
import com.zhifa.security.mapper.UserInfoMapper;
import com.zhifa.security.util.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserController {

    @Autowired
    private UserInfoMapper userInfoMapper;

    @GetMapping({"/getAll","/"})
    public List<UserInfo> allUserInfo() {
        return userInfoMapper.selectList(null);
    }

    /*
    本项目是认证服务器 只是做认证工作 返回token 不做其他工作
    资源服务器用本项目同一个jwt工具 就能解析token信息 写个拦截器解析token的信息，
    放到req里面，后面controller 就能知道是哪个用户请求的了
    * 这里这是模仿 资源服务器的请求
    * */
    @GetMapping("/test/parsingToken")
    public UserInfo parsingToken(@RequestParam("token") String token) {
        return JwtTokenUtil.parseToken(token);
    }



}
