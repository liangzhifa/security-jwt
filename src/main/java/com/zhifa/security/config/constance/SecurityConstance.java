package com.zhifa.security.config.constance;

//常量类
public class SecurityConstance {
    public static String client_id = "client";
    public static String passwordTypes = "password";
    public static String authorization_codeTypes = "authorization_code";
    public static String refresh_tokenTypes = "refresh_token";
    public static String redirectUri = "http://localhost:8080/test/callback";
    public static String secret = "secret";
    public static String scopes = "all";
    public static String checkTokenAccess = "isAuthenticated()";
    public static String permitAll = "permitAll()";
    public static String jwtSigningKey = "secretKey";
    public static long jwtActiveTime_millisecond = 7 * 60 * 60 * 1000l;//毫秒
    public static long jwtActiveTime_day = 7;//天
    public static String authorization = "Authorization";
    public static String bearer = "bearer";
    public static String token = "token";
    //http://localhost:8080/oauth/authorize?client_id=client&client_secret=secret&response_type=code&redirect_uri=http://www.baidu.com

}
