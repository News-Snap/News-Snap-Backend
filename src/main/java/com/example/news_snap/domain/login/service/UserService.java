package com.example.news_snap.domain.login.service;

import com.example.news_snap.domain.login.entity.User;
import com.example.news_snap.domain.login.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public User createUser(User user) {
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
    public User getByCredentials(String email, String password) {
        return userRepository.findByEmailAndPassword(email, password);
    }

}
