package com.example.news_snap.domain.login.security.oauth;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@Component
public class RedirectUrlCookieFilter extends OncePerRequestFilter {
    public static final String REDIRECT_URI_PARAM = "redirect_uri";
    private static final int MAX_AGE = 180;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (request.getRequestURI().startsWith("/auth/authorize")) {
            try {
                log.info("redirecting to " + request.getRequestURI());
                String redirectUri = request.getParameter(REDIRECT_URI_PARAM);  //request 파라미터에서 redirect_url을 가져온다.

                Cookie cookie = new Cookie(REDIRECT_URI_PARAM, redirectUri);
                cookie.setPath("/");
                cookie.setMaxAge(MAX_AGE);
                response.addCookie(cookie);
            } catch (Exception e) {
                logger.error("Could not set user authentication in security context", e);
                log.info("Unauthorized request");
            }
        }
                filterChain.doFilter(request, response);
        }
    }

