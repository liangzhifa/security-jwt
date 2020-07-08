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

    @GetMapping("/test/parsingToken")
    public UserInfo parsingToken(@RequestParam("token") String token) {
        return JwtTokenUtil.parseToken(token);
    }



}
