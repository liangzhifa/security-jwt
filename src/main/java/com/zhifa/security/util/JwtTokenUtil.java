package com.zhifa.security.util;

import com.zhifa.security.config.constance.SecurityConstance;
import com.zhifa.security.entity.UserInfo;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.StandardCharsets;
import java.util.Date;

@Slf4j
public class JwtTokenUtil {
    public static UserInfo parseToken(String token) {
        UserInfo userInfo = null;
        try {
            //解析token
            Claims claims = Jwts.parser()
                    .setSigningKey(SecurityConstance.jwtSigningKey.getBytes(StandardCharsets.UTF_8))
                    .parseClaimsJws(token)
                    .getBody();
            //获取用户名
            //String username = claims.getSubject();
            String username = claims.get("username").toString();
            //获取权限
            String authority = claims.get("authorities").toString();
            log.warn("jwt authority :{}",authority);
            Integer id = (Integer) claims.get("id");
            userInfo = UserInfo.builder().id(id).username(username).build();
            log.warn("jwt claims :{}",claims);
            log.warn("jwt userInfo :{}",userInfo);
        } catch (ExpiredJwtException e) {
            log.error("jwt异常");
        } catch (Exception e) {
            log.error("其他异常");
        }
        return userInfo;
    }
}
