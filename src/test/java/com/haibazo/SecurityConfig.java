package com.haibazo;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)  // Disable CSRF globally (not recommended)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/product/**").permitAll()  // Disable CSRF for specific endpoints
                        .anyRequest().authenticated()
                );
        return http.build();
    }
}
