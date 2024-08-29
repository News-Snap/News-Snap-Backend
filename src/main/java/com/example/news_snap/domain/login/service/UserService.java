package com.example.news_snap.domain.login.service;

import com.example.news_snap.domain.login.dto.SignupUserDTO;
import com.example.news_snap.domain.login.entity.AuthProvider;
import com.example.news_snap.domain.login.entity.Authority;
import com.example.news_snap.domain.login.entity.Status;
import com.example.news_snap.domain.login.entity.User;
import com.example.news_snap.domain.login.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Slf4j
@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();


    public User createUser(SignupUserDTO userDTO) {
            if (userDTO == null || userDTO.password() == null) {
                throw new RuntimeException("비밀번호가 입력되지 않았습니다.");
            }
            User user = User.builder()
                    .email(userDTO.email())
                    .password(passwordEncoder.encode(userDTO.password()))
                    .nickname(userDTO.nickname())
                    .roles(Collections.singletonList(Authority.ROLE_USER))
                    .alarmTime(userDTO.alarmTime())
                    .alarmDay(Collections.singletonList(userDTO.alarmDay()))
                    .pushAlarm(userDTO.pushAlarm())
                    .status(Status.ACTIVE)
                    .authProvider(AuthProvider.NEWSSNAP)
                    .build();

            if (user == null || user.getEmail() == null) {
                throw new RuntimeException("이메일을 확인해주세요");
            }
            final String email = user.getEmail();
            if (userRepository.findByEmail(email) != null) {
                log.warn("이미 존재하는 email {}", email);
                throw new RuntimeException("이미 존재하는 email입니다.");
            }

            final User userEntity = userRepository.save(user);
            return userEntity;
        }
        //matches 메서드 이용해 패스워드 같은지 확인
        //-> salting 고려해 두 값 비교
        public User getByCredentials ( final String email, final String password, final PasswordEncoder encoder){
            final User user = userRepository.findByEmail(email);
            if (user != null && encoder.matches(password, user.getPassword())) {
                return user;
            }
            return null;
        }

    public String deleteUser(String email) {
        User user = userRepository.findByEmail(email);
        userRepository.delete(user);
        return "삭제되었습니다.";
    }
}

