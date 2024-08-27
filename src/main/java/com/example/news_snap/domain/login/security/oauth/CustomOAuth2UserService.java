package com.example.news_snap.domain.login.security.oauth;


import com.example.news_snap.domain.login.entity.AuthProvider;
import com.example.news_snap.domain.login.entity.Authority;
import com.example.news_snap.domain.login.entity.User;
import com.example.news_snap.domain.login.entity.UserPrincipal;
import com.example.news_snap.domain.login.repository.UserRepository;
import com.example.news_snap.domain.login.security.oauth.info.OAuth2UserInfo;
import com.example.news_snap.domain.login.security.oauth.info.OAuth2UserInfoFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private static final String ALREADY_SIGNED_UP_SOCIAL = "already_signed_up_social";
    private static final String ALREADY_SIGNED_UP_LOCAL = "already_signed_up_local";

    private final UserRepository userRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User user = super.loadUser(userRequest);

        try {
            return this.process(userRequest, user);
        } catch (AuthenticationException ex) {
            throw ex;
        } catch (Exception ex) {
            log.error("CustomOAuth2UserService loadUser Error: {} ", ex.getMessage());
            throw new InternalAuthenticationServiceException(ex.getMessage(), ex.getCause());
        }
    }

    private OAuth2User process(OAuth2UserRequest userRequest, OAuth2User user) {
        AuthProvider providerType = AuthProvider.valueOf(userRequest.getClientRegistration().getRegistrationId().toUpperCase());

        OAuth2UserInfo userInfo = OAuth2UserInfoFactory.getOAuth2UserInfo(providerType, user.getAttributes());
        log.info("OAuth2UserInfo: {}", userInfo);

        User savedUser = userRepository.findByEmail(userInfo.getEmail());

        if (savedUser != null) {
            validateProvider(savedUser, providerType);
            updateUser(savedUser, userInfo);
        } else {
            savedUser = createUser(userInfo, providerType);
        }

        return UserPrincipal.create(savedUser, user.getAttributes());
    }


    private void validateProvider(User savedUser, AuthProvider providerType) {
        if (savedUser.getAuthProvider() == AuthProvider.NEWSSNAP) {
            log.error("CustomOAuth2UserService process Error: 기존 회원입니다. 자체 로그인을 이용해 주세요.");
            throw new OAuthProviderMissMatchException(ALREADY_SIGNED_UP_LOCAL);
        }

        if (providerType != savedUser.getAuthProvider()) {
            log.error("CustomOAuth2UserService process Error: 다른 소셜에서 가입된 이메일입니다. 해당 소셜 로그인을 이용해 주세요.");
            throw new OAuthProviderMissMatchException(ALREADY_SIGNED_UP_SOCIAL);
        }
    }

    private User createUser(OAuth2UserInfo userInfo, AuthProvider providerType) {
        User user = User.builder()
                .email(userInfo.getEmail())
                .nickname(userInfo.getName())
                .authProvider(providerType)
                .roles(Collections.singletonList(Authority.ROLE_USER))
                .build();

        return userRepository.saveAndFlush(user);
    }

    private void updateUser(User user, OAuth2UserInfo userInfo) {
        if (userInfo.getName() != null && !user.getNickname().equals(userInfo.getName())) {
            user.setNickname(userInfo.getName());
        }
        // 이메일이 변경되지 않도록 보장
        user.setEmail(user.getEmail());
        userRepository.saveAndFlush(user);
    }
}

