package com.zhifa.security.converter;

import com.zhifa.security.dto.SecurityUserDetails;
import com.zhifa.security.entity.UserInfo;
import com.zhifa.security.mapper.UserInfoMapper;
import com.zhifa.security.util.JwtTokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.stereotype.Component;

import java.util.HashMap;

/**
 * 对JwtAccessTokenConverter 的 enhance进行重写，加入自定义的信息
 */
@Component
@Slf4j
public class CustomJwtAccessTokenConverter extends JwtAccessTokenConverter {

    @Autowired
    private UserInfoMapper userInfoMapper;

    private static final String BEARER_PRIFIX = "bearer ";

    //这个是token增强器，想让jwt token携带额外的信息在这里处理
    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {

        return super.enhance(accessToken, authentication);
    }


    //只有用户名密码正确才能走
    //主要是资源服务器解析时一定要有bearer这个头才认为是一个oauth请求，但不知道为啥指定jwt后这个头就不见了，特意加上去
    @Override
    protected String encode(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
        String token = "";
        if (accessToken instanceof DefaultOAuth2AccessToken) {
            Object principal = authentication.getPrincipal();

            //这个principal是当时登录后存到securiy的东东，一般是用户实体，自己debug一下就知道了
            //我这就是dto包里的 SecurityUserDetails
            if (principal instanceof SecurityUserDetails) {
                SecurityUserDetails securityUserDetails = (SecurityUserDetails) principal;

                HashMap<String, Object> map = new HashMap<>();
                //jwt默认已经自带用户名，无需再次加入
                map.put("nick_name", securityUserDetails.getUsername());
                ((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(map);
                //通过用户名获取用户
                UserInfo user = userInfoMapper.findByUsername(securityUserDetails.getUsername());
                log.error("jwt 要封装的 user: {}",user);
                token = JwtTokenUtil.getToken(user);
            }
        }
        //return BEARER_PRIFIX + super.encode(accessToken, authentication);
        //我们把用户的信息封装在这里
        //return BEARER_PRIFIX + "super.encode(accessToken, authentication)";
        return token;

    }
}
