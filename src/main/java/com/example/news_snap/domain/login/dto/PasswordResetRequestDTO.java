package com.example.news_snap.domain.login.dto;

import lombok.Builder;

@Builder
public record PasswordResetRequestDTO(
        String email,
        String verificationCode,
        String newPassword) {}
