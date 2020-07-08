package com.zhifa.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.junit.jupiter.api.Test;

import java.util.Date;

public class JwtTest {

    //加密的秘钥
    private final String secretKey = "secretKey";

    @Test
    public void testToken() {
        String token = Jwts.builder()
                //主题 放入用户名
                .setSubject("zhifa")
                // 自定义属性 放入用户拥有请求权限
                .claim("authorities", "admin")
                //  失效时间
                .setExpiration(new Date(System.currentTimeMillis() + 7 * 60 * 1000))
                //  签名算法和密钥
                .signWith(SignatureAlgorithm.HS512, secretKey)
                .compact();
        System.out.println(token);
        try {
            //解析token
            Claims claims = Jwts.parser()
                    .setSigningKey(secretKey)
                    .parseClaimsJws(token)
                    .getBody();
            System.out.println(claims);
            //获取用户名
            String username = claims.getSubject();
            System.out.println("username:" + username);
            //获取权限
            String authority = claims.get("authorities").toString();
            System.out.println("权限：" + authority);
        } catch (ExpiredJwtException e) {
            System.out.println("jwt异常");
        } catch (Exception e) {
            System.out.println("异常");
        }
    }
}
