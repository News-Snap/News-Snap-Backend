package com.example.news_snap.domain.setting.dto;

import com.example.news_snap.domain.login.entity.AlarmDay;
import lombok.Builder;

import java.time.LocalTime;

public record SettingResponse() {

    @Builder
    public record settingDTO(
            String nickname,
            boolean pushAlarm,
            String alarmDay,
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
            String alarmDay
    ){}

    @Builder
    public record updateAlarmTimeResultDTO(
            LocalTime alarmTime
    ){}

}
