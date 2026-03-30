package com.flowbit.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {

    private final JwtFilter jwtFilter;

    public SecurityConfig(JwtFilter jwtFilter) {
        this.jwtFilter = jwtFilter;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                .csrf(csrf -> csrf.disable())

                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(

                                // 🔓 LOGIN & REGISTER (MUST BE OPEN)
                                "/api/employee/login",
                                "/api/employee/register",
                                "/api/client/login",
                                "/api/client/register",

                                // 🔓 FRONTEND FILES
                                "/login.html",
                                "/dashboard.html",
                                "/admin.html",
                                "/client.html",

                                // 🔓 STATIC FILES
                                "/js/**",
                                "/css/**",
                                "/images/**",
                                "/"

                        ).permitAll()

                        // 🔒 OTHER APIs NEED TOKEN
                        .anyRequest().authenticated()
                )

                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)

                .formLogin(form -> form.disable())
                .httpBasic(basic -> basic.disable());
        return http.build();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}