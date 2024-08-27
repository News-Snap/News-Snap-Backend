package com.example.news_snap.domain.login.security.oauth;

import com.example.news_snap.domain.login.security.TokenProvider;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;

import static com.example.news_snap.domain.login.security.oauth.RedirectUrlCookieFilter.REDIRECT_URI_PARAM;

@Slf4j
@Component
@AllArgsConstructor
public class OAuthSuccessHandler extends SimpleUrlAuthenticationSuccessHandler  {

    private static final String LOCAL_REDIRECT_URL = "프론트 url";

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        log.info("onAuthenticationSuccess");
        TokenProvider tokenProvider = new TokenProvider();
        String token = tokenProvider.createSocialToken(authentication);

        Optional<Cookie> optionalCookie = Arrays.stream(request.getCookies()).filter(cookie -> cookie.getName().equals(REDIRECT_URI_PARAM)).findFirst();
        Optional<String> redirect_uri = optionalCookie.map(Cookie::getValue);
        log.info("token: {}", token);
        //response.sendRedirect(redirect_uri.orElseGet(() -> LOCAL_REDIRECT_URL)+ "/{여기 프론트 함수이름}?token=" + token);
    }

        }
