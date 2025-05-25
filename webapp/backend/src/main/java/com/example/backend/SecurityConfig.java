package com.example.backend;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .cors(Customizer.withDefaults())
                .csrf(AbstractHttpConfigurer::disable) // Disable CSRF để frontend React gọi API dễ hơn
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/product/**").permitAll() // Cho phép không đăng nhập với API
                        .anyRequest().authenticated() // Các URL khác vẫn cần login
                )
                .formLogin(Customizer.withDefaults()); // Cho phép login form mặc định

        return http.build();
    }
}
