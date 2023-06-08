package com.example.MyBMS;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class BmsAuthConfiguration {

    @Autowired
    private DataSource dataSource;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(auth -> auth.anyRequest().authenticated());
        http.formLogin(form -> form.defaultSuccessUrl("/guest/myPage"));
        return http.build();
    }

    @Bean
    public UserDetailsManager userDetailsService() {
        JdbcUserDetailsManager users = new JdbcUserDetailsManager(dataSource);

        return users;
    }
    
}
