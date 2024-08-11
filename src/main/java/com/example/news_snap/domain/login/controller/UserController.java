package com.example.news_snap.domain.login.controller;

import com.example.news_snap.domain.login.dto.LoginRequestDTO;
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
    /*
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "로그인 성공"),
            @ApiResponse(responseCode = "400", description = "로그인 실패")
    })*/
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
}