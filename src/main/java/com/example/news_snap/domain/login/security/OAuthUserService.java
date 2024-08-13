package com.example.news_snap.domain.login.security;

import com.example.news_snap.domain.login.entity.User;
import com.example.news_snap.domain.login.repository.UserRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Map;

@Slf4j
@Service
public class OAuthUserService extends DefaultOAuth2UserService {

    @Autowired
    private UserRepository userRepository;

    public OAuthUserService() {
        super();
    }

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        //DafaultOAuth2UserService의 기존 loadUser를 호출한다. 이 메서드가 user-info-uri를 이용해 사용자 정보 가졍옴
        final OAuth2User oAuth2User = super.loadUser(userRequest);

        try {
            //디버깅을 돕기 위해 사용자 정보가 어떻게 되는지 로깅한다. 테스팅 시에만 사용해야 한다.
            log.info("OAuth2User : {}",new ObjectMapper().writeValueAsBytes(oAuth2User.getAttributes()));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        //login 필드를 가져온다.
        // 네이버의 경우, 사용자 정보는 'response' 필드에 담겨 있음
        Map<String, Object> attributes = (Map<String, Object>) oAuth2User.getAttributes().get("response");
        String username = (String) attributes.get("name");
        String email = (String) attributes.get("email");
        String birthyear = (String) attributes.get("birthyear");
        String birthdate = (String) attributes.get("birthdate");
        LocalDate birthDate = null;
        try {
            if (birthyear != null && birthdate != null) {
                // birthyear와 birthday를 결합하여 LocalDate 생성
                String dateString = birthyear + "-" + birthdate; // "yyyy-MM-dd" 형식으로 조합
                birthDate = LocalDate.parse(dateString, DateTimeFormatter.ISO_OFFSET_DATE);
            }
        } catch (DateTimeParseException e) {
            log.error("Error parsing birthdate: {}", e.getMessage());
        }
        String authProvider = "Naver";
        User user = null;
        //유저가 존재하지 않으면 새로 생성
        if(!userRepository.existsByEmail(email)) {
            user = User.builder()
                    .email(email)
                    .nickname(username)
                    .authProvider(authProvider)
                    .birthDate(birthDate)
                    .build();
            //알람 설정 추가
            user = userRepository.save(user);
        }
        log.info("username : {} authProvider: {}",username, authProvider);
        return oAuth2User;
    }
}
