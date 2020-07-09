package com.zhifa.security.service.impl;

import com.zhifa.security.dto.SecurityUserDetails;
import com.zhifa.security.entity.UserInfo;
import com.zhifa.security.mapper.UserInfoMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

/*
 * UserDetailsService 有安全框架提供，只要重写loadUserByUsername方法即可
 *
 * */
@Service
@Slf4j
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private UserInfoMapper userInfoMapper;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    /**
     * 从数据库中获取用户信息，返回一个 UserDetails 对象，
     *
     * @param username
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //通过用户名获取用户
        UserInfo user = userInfoMapper.findByUsername(username);
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));//数据库是明文这里转换一下
        //将 user 对象转化为 UserDetails 对象
        log.warn("loadUserByUsername: {}", username);
        log.warn("user: {}", user);
        return new SecurityUserDetails(user);

    }
}
