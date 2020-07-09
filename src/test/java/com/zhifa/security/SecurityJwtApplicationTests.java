package com.zhifa.security;

import com.zhifa.security.config.constance.SecurityConstance;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootTest
class SecurityJwtApplicationTests {
    @Autowired
    public PasswordEncoder passwordEncoder;
    @Test
    void contextLoads() {
        System.out.println(passwordEncoder.encode(SecurityConstance.secret));
    }

}
