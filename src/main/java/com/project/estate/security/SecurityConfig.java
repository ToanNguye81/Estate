package com.project.estate.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

//  cấu hình các bộ lọc bảo mật cho ứng dụng, bao gồm thêm bộ lọc xử lý token JWT và vô hiệu hóa tính năng CSRF.
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private JwtRequestFilter jwtRequestFilter;

    // Phương thức cấu hình bảo mật HTTP
    @Override
    public void configure(HttpSecurity http) throws Exception {
        /*
         * Thêm JwtRequestFilter vào danh sách các bộ lọc trước
         * UsernamePasswordAuthenticationFilter
         */
        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class)
                .csrf().disable();// Vô hiệu hóa tính năng CSRF (Cross-Site Request Forgery)
    }
}
