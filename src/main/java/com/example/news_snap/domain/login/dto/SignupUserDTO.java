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
         LocalTime alarmTime,
         LocalDate birthDate,
         AlarmDay alarmDay,
         Boolean pushAlarm
) {

}
