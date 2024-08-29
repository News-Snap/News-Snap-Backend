package com.example.news_snap.domain.login.security;

import com.example.news_snap.domain.login.entity.User;
import com.example.news_snap.domain.login.repository.UserRepository;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;


@Slf4j
@Service
public class TokenProvider {
    @Autowired
    private UserRepository userRepository;

     /*
    //나중에 yml파일에
    /* app:
      jwtSecret: secret -> 나중에 입력
     */
    /*
    @Value("${app.jwtSecret}")
    private String SECRET_KEY;

    */
    private static final String SECRET_KEY = "secret";
    private static final String AUTHORITIES_KEY = "auth";

    public String createToken(User user) {
        //기한 지금으로부터 1일
        Date expiryDate = Date.from(
                Instant.now()
                        .plus(1, ChronoUnit.DAYS)
        );;

        //JWT Token 생성
        return Jwts.builder()
                .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
                .setSubject(user.getEmail())
                .setIssuer("NEWSSNAP")
                .setIssuedAt(new Date())
                .setExpiration(expiryDate)
                .compact();
    }
    public String createSocialToken(final Authentication authentication) {
        log.info("createSocialToken" + authentication);
        String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));
        //기한 지금으로부터 1일
        Date expiryDate = Date.from(
                Instant.now()
                        .plus(1, ChronoUnit.DAYS)
        );;

        //JWT Token 생성
        return Jwts.builder()
                .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
                .setSubject(authentication.getName())
                .claim(AUTHORITIES_KEY,authorities)
                .setIssuer("NEWSSNAP")
                .setIssuedAt(new Date())
                .setExpiration(expiryDate)
                .compact();
    }


    //토큰 유효시간 검사 추가
    public String validateToken(String token) {
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(SECRET_KEY)
                    .parseClaimsJws(token)
                    .getBody();

            // 토큰이 만료되었는지 확인 (추가)
            if (claims.getExpiration().before(new Date())) {
                throw new RuntimeException("Token expired");
            }

            return claims.getSubject();
        } catch (SignatureException ex) {
            log.error("Invalid JWT signature", ex);
            throw new RuntimeException("Invalid JWT signature");
        } catch (Exception ex) {
            log.error("Invalid JWT token", ex);
            throw new RuntimeException("Invalid JWT token");
        }

    }

    public String getIssuerFromToken(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody();
        return claims.getIssuer();
    }
        public User getUser(String email) {
        //추후 서비스 수정
        return (User) userRepository.findByEmail(email);
    }



}
