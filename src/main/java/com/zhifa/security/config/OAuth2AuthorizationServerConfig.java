package com.zhifa.security.config;

import com.zhifa.security.config.constance.SecurityConstance;
import com.zhifa.security.converter.CustomJwtAccessTokenConverter;
import com.zhifa.security.service.impl.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

@Configuration
@EnableAuthorizationServer// 开启认证服务 主要配置客户端的访问信息
//授权服务器
public class OAuth2AuthorizationServerConfig  extends AuthorizationServerConfigurerAdapter  {
    @Autowired
    private AuthenticationManager authenticationManager;//密码模式需要注入认证管理器
    @Autowired
    public PasswordEncoder passwordEncoder;
    @Autowired
    private UserDetailsServiceImpl userDetailsService;
    //自定义的token转换 在里面设置用户信息
    @Autowired
    private CustomJwtAccessTokenConverter converter;

    @Bean
    public TokenStore tokenStore() {
        return new JwtTokenStore(accessTokenConverter());
    }

    @Bean
    public JwtAccessTokenConverter accessTokenConverter() {//自定义的token转换 在里面设置用户信息
        //CustomJwtAccessTokenConverter converter = new CustomJwtAccessTokenConverter();
        converter.setSigningKey(SecurityConstance.jwtSigningKey);
        return converter;
    }

    @Bean
    @Primary
    public DefaultTokenServices tokenServices() {
        DefaultTokenServices defaultTokenServices = new DefaultTokenServices();
        defaultTokenServices.setTokenStore(tokenStore());
        defaultTokenServices.setSupportRefreshToken(true);
        return defaultTokenServices;
    }
    //配置客户端
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        //@formatter:off
        clients.inMemory()
                .withClient(SecurityConstance.withClient)
                .secret(passwordEncoder.encode(SecurityConstance.secret))
                .authorizedGrantTypes(SecurityConstance.authorizedGrantTypes) //主要是这里，开始了密码模式
                .scopes(SecurityConstance.scopes);
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        //endpoints.authenticationManager(authenticationManager);//密码模式必须添加authenticationManager
        //目的让框架帮忙验证用户的登录账号密码是否正确
        endpoints.userDetailsService(userDetailsService);

        endpoints.tokenStore(tokenStore())
                .accessTokenConverter(accessTokenConverter())
                .authenticationManager(authenticationManager);//密码模式必须添加authenticationManager
    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        security.allowFormAuthenticationForClients()
                .checkTokenAccess(SecurityConstance.checkTokenAccess);
    }
}
