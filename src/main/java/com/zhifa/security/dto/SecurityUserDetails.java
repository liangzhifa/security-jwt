package com.zhifa.security.dto;

import com.zhifa.security.entity.UserInfo;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

@ToString
public class SecurityUserDetails extends UserInfo implements UserDetails {
    //这里封装用户返回出去的信息
    private UserInfo user;
    public SecurityUserDetails(UserInfo user) {
        this.user = user;
        if (user != null) {
            this.setId(user.getId());
            this.setUsername(user.getUsername());
            this.setPassword(user.getPassword());

        }
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        //理想型返回 admin 权限，可自已处理这块
        //"ROLE_A, ROLE_B, ROLE_C"
        String role = "";
        if (user.getUsername().equals("admin")) {
            role = "admin,sys";
        }else {
            role = "general_user";
        }
        return AuthorityUtils.commaSeparatedStringToAuthorityList(role);
    }

    /**
     * 账户是否过期
     *
     * @return
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * 是否禁用
     *
     * @return
     */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /**
     * 密码是否过期
     *
     * @return
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * 是否启用
     *
     * @return
     */
    @Override
    public boolean isEnabled() {
        return true;
    }
}
