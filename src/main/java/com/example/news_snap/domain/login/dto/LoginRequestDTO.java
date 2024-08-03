package com.example.news_snap.domain.login.dto;

import lombok.Builder;

@Builder
public record LoginRequestDTO(
        String email,
        String password
) {

}