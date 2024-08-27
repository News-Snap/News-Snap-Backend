package com.example.news_snap.domain.login.security;

import com.example.news_snap.domain.login.entity.AuthProvider;
import com.example.news_snap.domain.login.entity.User;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component
public class JwtAuthentificationFilter extends OncePerRequestFilter {

    @Autowired
    private TokenProvider tokenProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            // 요청에서 토큰 가져오기
            String token = parseBearerToken(request);
            log.info("Filter is running...");

            // 토큰 검사하기. JWT이므로 인가 서버에 요청하지 않고도 검증 가능.
            if (token != null && !token.equalsIgnoreCase("null")) {
                // 사용자 ID 가져오기. 위조된 경우 예외처리된다.
                String email = tokenProvider.validateToken(token);
                log.info("Authenticated user: " + email);

                // 사용자 정보를 가져온다.
                //CustomUserDetails userDetails = tokenProvider.getUserDetails(userId);
                User user = tokenProvider.getUser(email);
                String issuer = tokenProvider.getIssuerFromToken(token);

                // 사용자 권한 가져오기
                List<SimpleGrantedAuthority> authorities = user.getRoles().stream()
                        .map(role -> new SimpleGrantedAuthority(role.name())) // Authority 객체를 문자열로 변환
                        .collect(Collectors.toList());

                try {
                    // 발급자를 AuthProvider Enum으로 변환
                    AuthProvider authProvider = AuthProvider.valueOf(issuer.toUpperCase());

                    // user 객체에 발급자를 설정
                    user.setAuthProvider(authProvider);

                    // 로그 출력
                    log.info("Token issuer: " + user.getAuthProvider());
                } catch (IllegalArgumentException e) {
                    log.error("Invalid auth provider: " + issuer, e);
                }


                // 인증 완료, SecurityContextHolder에 등록해야 인증된 사용자로 인식된다.
                AbstractAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(

                        user,
                        null,
                        authorities
                );
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
                securityContext.setAuthentication(authentication);
                SecurityContextHolder.setContext(securityContext);
            }
        } catch (Exception ex) {
            logger.error("Could not set user authentication in security context", ex);
        }

        filterChain.doFilter(request, response);
    }

    private String parseBearerToken(HttpServletRequest request) {
        // HTTP 요청의 헤더를 파싱해 Bearer 토큰을 리턴한다.
        String bearerToken = request.getHeader("Authorization");

        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }
}
