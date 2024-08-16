package com.example.news_snap.domain.setting.dto;

import com.example.news_snap.domain.login.entity.AlarmDay;
import lombok.Builder;

import java.time.LocalTime;

public record SettingResponse() {

    @Builder
    public record settingDTO(
            String nickname,
            boolean pushAlarm,
            AlarmDay alarmDay,
            LocalTime alarmTime
    ){}

    @Builder
    public record updateNicknameResultDTO(
            String nickname
    ){}

    @Builder
    public record updatePushAlarmResultDTO(
            boolean pushAlarm
    ){}

    @Builder
    public record updateAlarmDayResultDTO(
            AlarmDay alarmDay
    ){}

    @Builder
    public record updateAlarmTimeResultDTO(
            LocalTime alarmTime
    ){}


    @Builder
    public record addImprovementResultDTO(
            String improvement
    ){}
}
