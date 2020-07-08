package com.zhifa.security.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zhifa.security.entity.UserInfo;

public interface UserInfoMapper extends BaseMapper<UserInfo> {
    UserInfo findByUsername(String username);
}