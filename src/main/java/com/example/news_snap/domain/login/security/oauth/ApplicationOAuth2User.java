package com.example.news_snap.domain.login.security.oauth;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;

public class ApplicationOAuth2User implements OAuth2User {
    private String email;
    private Collection<? extends GrantedAuthority> authorities;
    private Map<String, Object> attributes;

    public ApplicationOAuth2User(String email, Map<String, Object> attributes) {
        this.email = email;
        this.attributes = attributes;
        this.authorities = Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"));;

    }
    @Override
    public Map<String, Object> getAttributes() {
        return this.attributes;
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }
    @Override
    public String getName() {
        return this.email;
    }
}
