package com.example.news_snap.domain.login.security;

import com.example.news_snap.domain.login.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

//subject는 String userId 입니당 Long 아님 주의!!!
//@AuthentificationPrincipal String userId
//위에 어노테이션 컨트롤러 메서드 파라미터 마다 넣어주기
@Slf4j
@Service
public class TokenProvider {
    private static final String SECRET_KEY = "secret";

    public String createToken(User user) {
        //기한 지금으로부터 1일
        Date expiryDate = Date.from(
                Instant.now()
                        .plus(1, ChronoUnit.DAYS)
        );
        //JWT Token 생성
        return Jwts.builder()
                .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
                .setSubject(user.getUserId().toString())
                .setIssuer("news snap app")
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
}
/*
    public String validateToken(String token) {

        Claims claims = Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();
    }
}
*/