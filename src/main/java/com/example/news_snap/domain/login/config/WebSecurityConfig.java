package com.example.news_snap.domain.login.config;

import com.example.news_snap.domain.login.security.JwtAuthentificationFilter;
import com.example.news_snap.domain.login.security.OAuthUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

//http://localhost:8080/oauth2/authorization/naver
@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
@Slf4j
public class WebSecurityConfig {

    @Autowired
    JwtAuthentificationFilter jwtAuthentificationFilter;
    @Autowired
    OAuthUserService oAuthUserService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, JwtAuthentificationFilter jwtAuthentificationFilter) throws Exception {

        // CSRF 비활성화
        http.csrf(AbstractHttpConfigurer::disable);

        // HTTP 기본 인증 비활성화
        http.httpBasic(AbstractHttpConfigurer::disable);

        // 세션 관리 설정: STATELESS
        http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        // 경로에 대한 접근 권한 설정
        http.authorizeHttpRequests(auth -> auth
                .requestMatchers("/**", "/api/v1/auth/**","/api/v1/oauth2/**").permitAll()
                .anyRequest().authenticated());

        // OAuth2 로그인 설정
        http.oauth2Login(oauth2 -> oauth2
                .authorizationEndpoint(authorization -> authorization
                        .baseUri("/oauth2/authorization") // OAuth2 인증 서버의 엔드포인트
                )

                .redirectionEndpoint(redirection -> redirection
                        .baseUri("/api/v1/oauth2/callback/*") // OAuth2 인증 후 리디렉션 받을 엔드포인트
                )
                .userInfoEndpoint(userInfo -> userInfo
                        .userService(oAuthUserService) // 사용자 정보 서비스 설정
                )
        );

        // JWT 인증 필터 추가
        http.addFilterAfter(jwtAuthentificationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}


/*
@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
@Slf4j
public class WebSecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, JwtAuthentificationFilter jwtAuthentificationFilter) throws Exception {

        http.csrf(AbstractHttpConfigurer::disable)          //csrf 보호 비능 비활성화
                .httpBasic(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                //현재 모든 path에 대해 인증없이 허용
                .authorizeHttpRequests(auth -> auth.requestMatchers("/**","/api/v1/auth/**").permitAll()).and().oauth2Login();
        /*
                //추후 변경 예정
                .authorizeHttpRequests(auth -> auth.requestMatchers("/", "/api/v1/auth/**"
                ).permitAll().anyRequest().authenticated());


        http.addFilterAfter(jwtAuthentificationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

}

 */