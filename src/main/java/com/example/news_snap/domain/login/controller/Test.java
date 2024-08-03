package com.example.news_snap.domain.login.controller;

import com.example.news_snap.domain.login.entity.User;
import com.example.news_snap.domain.login.repository.UserRepository;
import com.example.news_snap.global.common.ApiResponse;
import com.example.news_snap.global.common.code.status.ErrorStatus;
import com.example.news_snap.global.common.exception.GeneralException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.example.news_snap.domain.login.entity.QUser.user;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/")
public class Test {

    private final UserRepository userRepository;

    @GetMapping("test")
    public ApiResponse<?> test(
            @AuthenticationPrincipal Long userId
            ) {
        User user1 = userRepository.findById(userId).orElseThrow(() -> new GeneralException(ErrorStatus._NOT_FOUND_USER));
        return ApiResponse.onSuccess(user1.getNickname());
    }
}
