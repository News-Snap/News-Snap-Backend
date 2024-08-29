package com.example.news_snap.domain.login.dto;

import lombok.Builder;

import java.time.LocalDate;

@Builder
public record SignupUserDTO(
         String email,
         String nickname,
         String password,
         LocalDate birthDate,
         Boolean pushAlarm
) {

}
