package com.example.news_snap.domain.login.dto;

import com.example.news_snap.domain.login.entity.AlarmDay;
import lombok.Builder;

import java.time.LocalDate;
import java.time.LocalTime;

@Builder
public record SignupUserDTO(
         String email,
         String nickname,
         String password,
         LocalDate birthDate,
         Boolean pushAlarm
) {

}
