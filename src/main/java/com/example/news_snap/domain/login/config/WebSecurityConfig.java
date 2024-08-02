package com.example.news_snap.domain.login.config;

import com.example.news_snap.domain.login.security.JwtAuthentificationFilter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
@Slf4j
public class WebSecurityConfig {

    //@Override
    //protected void configure(HttpSecurity http) throws Exception {}

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, JwtAuthentificationFilter jwtAuthentificationFilter) throws Exception {

        http.csrf(AbstractHttpConfigurer::disable)          //csrf 보호 비능 비활성화
                .httpBasic(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                //현재 모든 path에 대해 인증없이 허용
                .authorizeHttpRequests(auth -> auth.requestMatchers("/**").permitAll());
                //추후 변경 예정
                /*
                .authorizeHttpRequests(auth -> auth.requestMatchers("/", "/api/v1/auth/**"
                ).permitAll().anyRequest().authenticated());

                 */
        http.addFilterAfter(jwtAuthentificationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

}
