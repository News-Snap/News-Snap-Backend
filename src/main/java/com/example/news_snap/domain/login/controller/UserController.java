package com.example.news_snap.domain.login.controller;

import com.example.news_snap.domain.login.dto.LoginRequestDTO;
import com.example.news_snap.domain.login.dto.PasswordResetEmailDTO;
import com.example.news_snap.domain.login.dto.PasswordResetRequestDTO;
import com.example.news_snap.domain.login.dto.SignupUserDTO;
import com.example.news_snap.domain.login.entity.User;
//import com.example.news_snap.domain.login.security.TokenProvider;
import com.example.news_snap.domain.login.security.TokenProvider;
import com.example.news_snap.domain.login.service.UserService;
import com.example.news_snap.global.common.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;




@Slf4j
@RestController
@RequestMapping("/api/v1/auth")
@Tag(name = "userAPI")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    TokenProvider tokenProvider;

    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Operation(summary = "회원가입", description = "게스트 계정 회원가입")
    @PostMapping("/signup")
    public ApiResponse<?> registerUser(@RequestBody SignupUserDTO userDTO) {
            try {
            userService.createUser(userDTO);
            return ApiResponse.onSuccess("회원가입 성공");
        } catch (Exception e) {
            //나중에 responseBody 추가
            return ApiResponse.onFailure("회원가입 실패");

        }
    }

    @Operation(summary = "로그인", description = "게스트 계정 로그인")
    @PostMapping("/login")
    public ApiResponse<String> authenticateUser(@RequestBody LoginRequestDTO loginRequestDTO) {
        User user = userService.getByCredentials(
                loginRequestDTO.email(),
                loginRequestDTO.password(),
                passwordEncoder
        );
        if (user != null) {
            final String token = tokenProvider.createToken(user);

            return ApiResponse.onSuccess(token);
        } else {
            return ApiResponse.onFailure("로그인 실패");
        }
    }

    @Operation(summary = "비밀번호 재설정 이메일 발송", description = "이메일로 인증 코드를 전송합니다.")
    @PostMapping("/password-reset/email")
    public ApiResponse<?> sendVerificationCode(@RequestBody PasswordResetEmailDTO request) {
        try {
            userService.sendVerificationCode(request.email());
            return ApiResponse.onSuccess("인증 코드가 이메일로 전송되었습니다.");
        } catch (Exception e) {
            return ApiResponse.onFailure(e.getMessage());
        }
    }
    @Operation(summary = "비밀번호 재설정", description = "인증 코드를 이용하여 비밀번호를 재설정합니다.")
    @PostMapping("/password-reset")
    public ApiResponse<?> resetPassword(@RequestBody PasswordResetRequestDTO request) {
        try {
            userService.resetPassword(request);
            return ApiResponse.onSuccess("비밀번호가 재설정되었습니다.");
        } catch (Exception e) {
            return ApiResponse.onFailure(e.getMessage());
        }
    }


    @Operation(summary = "해당 이메일 유저 삭제", description = "해당 이메일 유저를 삭제합니다.")
    @DeleteMapping("/delete/{email}")
    public ApiResponse<String> deleteUser(
            @PathVariable(name = "email") String email
    ) {
        return ApiResponse.onSuccess(userService.deleteUser(email));
    }
}