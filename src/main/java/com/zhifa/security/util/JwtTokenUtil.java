package com.zhifa.security.util;

import com.zhifa.security.config.constance.SecurityConstance;
import com.zhifa.security.entity.UserInfo;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;

public class JwtTokenUtil {
    public static String getToken(UserInfo userInfo) {
        String token = Jwts.builder()
                //主题 放入用户名
                .setSubject(userInfo.getUsername())
                // 自定义属性 放入用户拥有请求权限
                .claim("id",userInfo.getId())
                //我这写死，后面可以从userInfo封装
                .claim("authorities", "admin")
                //  失效时间
                .setExpiration(new Date(System.currentTimeMillis() + 7 * 60 * 1000))
                //  签名算法和密钥
                .signWith(SignatureAlgorithm.HS512, SecurityConstance.jwtSigningKey)
                .compact();
        return token;
    }
    public static UserInfo parseToken(String token){
        UserInfo userInfo = null;
        try {
            //解析token
            Claims claims = Jwts.parser()
                    .setSigningKey(SecurityConstance.jwtSigningKey)
                    .parseClaimsJws(token)
                    .getBody();
            System.out.println(claims);
            //获取用户名
            String username = claims.getSubject();
            //获取权限
            String authority = claims.get("authorities").toString();
            Integer id = (Integer) claims.get("id");
            userInfo = UserInfo.builder().id(id).username(username).build();
        } catch (ExpiredJwtException e) {
            System.out.println("jwt异常");
        } catch (Exception e) {
            System.out.println("异常");
        }
        return userInfo;
    }
}