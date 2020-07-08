package com.zhifa.security.config.constance;

//常量类
public class SecurityConstance {
    public static String withClient = "client";
    public static String authorizedGrantTypes = "password";
    public static String secret = "secret";
    public static String scopes = "all";
    public static String checkTokenAccess = "isAuthenticated()";
    public static String jwtSigningKey = "secretKey";
    public static Long jwtActiveTime = 7 * 60 * 60 * 1000l;//毫秒

}
