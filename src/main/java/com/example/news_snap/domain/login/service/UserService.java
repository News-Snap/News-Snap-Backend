package com.example.news_snap.domain.login.service;

import com.example.news_snap.domain.login.dto.PasswordResetRequestDTO;
import com.example.news_snap.domain.login.dto.SignupUserDTO;
import com.example.news_snap.domain.login.entity.*;
import com.example.news_snap.domain.login.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.*;

@Slf4j
@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    //@Autowired
    //private JavaMailSender mailSender;


    public User createUser(SignupUserDTO userDTO) {
            if (userDTO == null || userDTO.password() == null) {
                throw new RuntimeException("비밀번호가 입력되지 않았습니다.");
            }
        User user = User.builder()
                .email(userDTO.email())
                .password(passwordEncoder.encode(userDTO.password()))
                .nickname(userDTO.nickname())
                .roles(Collections.singletonList(Authority.ROLE_USER))
                .alarmTime(LocalTime.of(9, 0)) // 9시를 LocalTime으로 설정
                .alarmDay(Arrays.asList(
                        AlarmDay.MONDAY,
                        AlarmDay.TUESDAY,
                        AlarmDay.WEDNESDAY,
                        AlarmDay.THURSDAY,
                        AlarmDay.FRIDAY,
                        AlarmDay.SATURDAY,
                        AlarmDay.SUNDAY
                )) // 모든 요일을 AlarmDay enum으로 설정
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
        /*
    private final Map<String, String> verificationCodes = new HashMap<>();

    public void sendVerificationCode(String email) {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new RuntimeException("해당 이메일로 등록된 사용자가 없습니다.");
        }

        String verificationCode = generateVerificationCode();
        verificationCodes.put(email, verificationCode);

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("비밀번호 재설정 인증 코드");
        message.setText("인증 코드: " + verificationCode);
        mailSender.send(message);

        log.info("인증 코드 {}가 {}로 전송되었습니다.", verificationCode, email);
    }

    public void resetPassword(PasswordResetRequestDTO request) {
        String storedCode = verificationCodes.get(request.email());
        if (storedCode == null || !storedCode.equals(request.verificationCode())) {
            throw new RuntimeException("인증 코드가 올바르지 않습니다.");
        }

        User user = userRepository.findByEmail(request.email());
        if (user == null) {
            throw new RuntimeException("사용자를 찾을 수 없습니다.");
        }

        user.setPassword(passwordEncoder.encode(request.newPassword()));
        userRepository.save(user);

        verificationCodes.remove(request.email());
    }

    private String generateVerificationCode() {
        return String.format("%06d", new Random().nextInt(999999));
    }
*/

    public String deleteUser(String email) {
        User user = userRepository.findByEmail(email);
        userRepository.delete(user);
        return "삭제되었습니다.";
    }
}

