package com.zhifa.security;

import com.zhifa.security.config.constance.SecurityConstance;
import com.zhifa.security.util.JwtTokenUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;
import java.util.Date;

public class JwtTest {

    //加密的秘钥
    private final String secretKey = SecurityConstance.jwtSigningKey;

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
        token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyX25hbWUiOiJhZG1pbiIsInNjb3BlIjpbImFsbCJdLCJpZCI6bnVsbCwiZXhwIjoxNTk0MzQ2NzkzLCJhdXRob3JpdGllcyI6W3siYXV0aG9yaXR5IjoiYWRtaW4ifV0sImp0aSI6IjFhOGFlZjMzLTAwNmMtNDFjNC1hYjIxLTI3MGUyNTIzMmQ1NCIsImNsaWVudF9pZCI6ImNsaWVudCIsInVzZXJuYW1lIjoiYWRtaW4ifQ.xmHv-c6wKcb0Ef51vMao56nG_Rak56BJUuCQDZvOrCk";
        try {
            //解析token
            Claims claims = Jwts.parser()
                    .setSigningKey(secretKey.getBytes(StandardCharsets.UTF_8))
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
            e.printStackTrace();
            System.out.println("异常");
        }
    }

    public static void main(String[] args) {
        String token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzY29wZSI6WyJhbGwiXSwiaWQiOjEsImV4cCI6MTU5NDM0ODY5NiwiYXV0aG9yaXRpZXMiOlsic3lzIiwiYWRtaW4iXSwianRpIjoiMmE5NjdmZDUtMTk2Yy00MzkyLWJkOTAtMTRjOTFjZWEwZDQ5IiwiY2xpZW50X2lkIjoiY2xpZW50IiwidXNlcm5hbWUiOiJhZG1pbiJ9.ILaPkb1QWQ1nW1x9onReDaLfdeuNvGG9nPwfzf0gZVU";
        JwtTokenUtil.parseToken(token);

    }
}
