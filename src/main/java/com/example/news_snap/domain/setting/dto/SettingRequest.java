package com.example.news_snap.domain.setting.dto;



import com.example.news_snap.domain.login.entity.AlarmDay;

import java.time.LocalTime;

public record SettingRequest() {


    public record updateNicknameDTO(
            String nickname
    ){}


    public record updatePushAlarmDTO(
            boolean pushAlarm
    ){}

    public record updateAlarmDayDTO(
            AlarmDay alarmDay
    ){}


    public record updateAlarmTimeDTO(
            LocalTime alarmTime
    ){}

    public record addImprovementDTO(
            String improvement
    ){}





}
