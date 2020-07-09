package com.zhifa.security.config.oauth2Config;

import com.zhifa.security.service.impl.UserDetailsServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Slf4j
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();  //密码模式需要注入认证管理器
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder());//加密
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //关闭跨站请求防护
        http.csrf().disable()
                //前后端分离采用JWT 不需要session
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        //httpBasic登录方式
        http.httpBasic()
                .and()
                .authorizeRequests()
                //.antMatchers("/login/**").permitAll()//表示访问 /login 下面的接口，都放行
                // .antMatchers("/logout/**").permitAll()//表示访问 /logout 下面的接口，都放行
                .antMatchers("/api/**").permitAll()//表示访问 /api 下面的接口，都放行
                .antMatchers("/oauth/**").permitAll()//表示访问 /oauth 下面的接口，都放行
                .antMatchers("/test/**").permitAll()//表示访问 /test 下面的接口，都放行
                //任何请求
                .anyRequest()
                //需要身份认证
                .authenticated();

    }

    @Override
    public void configure(WebSecurity web) {
        //将项目中静态资源路径开放出来
        web.ignoring()
                .antMatchers("/css/**", "/fonts/**", "/img/**", "/js/**");
    }

}
