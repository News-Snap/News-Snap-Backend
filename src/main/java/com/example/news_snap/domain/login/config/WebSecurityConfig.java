package com.example.news_snap.domain.login.config;

import com.example.news_snap.domain.login.security.oauth.CustomOAuth2UserService;
import com.example.news_snap.domain.login.security.JwtAuthentificationFilter;
import com.example.news_snap.domain.login.security.oauth.OAuthSuccessHandler;

import com.example.news_snap.domain.login.security.oauth.RedirectUrlCookieFilter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizationRequestRedirectFilter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.Http403ForbiddenEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

//http://localhost:8080/oauth2/authorization/naver
//http://localhost:8080/oauth2/authorization/google
//http://localhost:8080/oauth2/authorization/kakao
@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
@Slf4j
public class WebSecurityConfig {

    @Autowired
    private JwtAuthentificationFilter jwtAuthentificationFilter;
    @Autowired
    private CustomOAuth2UserService customOAuth2UserService;
    @Autowired
    private OAuthSuccessHandler oAuthSuccessHandler;
    @Autowired
    private RedirectUrlCookieFilter redirectUrlCookieFilter;

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
                        .userService(customOAuth2UserService) // 사용자 정보 서비스 설정

                )
                .successHandler(oAuthSuccessHandler)

        );

        // JWT 인증 필터 추가
        http.addFilterAfter(jwtAuthentificationFilter, UsernamePasswordAuthenticationFilter.class);
        http.addFilterBefore(redirectUrlCookieFilter, OAuth2AuthorizationRequestRedirectFilter.class);

        // 예외 처리 설정 추가
        http.exceptionHandling(exceptionHandling -> exceptionHandling
                .authenticationEntryPoint(new Http403ForbiddenEntryPoint()) // 인증 실패 핸들러 설정

        );




        return http.build();
    }
}

