package com.ssafy.utf.config;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    public void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.authorizeRequests()
                .antMatchers("/api/user/socialLogin").permitAll()
                .antMatchers("/api/user/join").permitAll()
                .antMatchers("/api/statistics/current/*").permitAll()
                .antMatchers("/api/statistics/end/*").permitAll()
                .antMatchers("/api/statistics/record/understand/*").permitAll()
                .antMatchers("/api/statistics/focus/current/*").permitAll()
                .anyRequest().authenticated();

        httpSecurity.csrf().disable();
    }
}
