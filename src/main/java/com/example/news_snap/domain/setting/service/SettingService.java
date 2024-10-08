package com.example.news_snap.domain.setting.service;


import com.example.news_snap.domain.login.entity.AlarmDay;
import com.example.news_snap.domain.login.entity.User;
import com.example.news_snap.domain.login.repository.UserRepository;
import com.example.news_snap.domain.setting.dto.SettingRequest;
import com.example.news_snap.domain.setting.dto.SettingResponse;
import com.example.news_snap.global.common.code.status.ErrorStatus;
import com.example.news_snap.global.common.exception.handler.UserHandler;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class SettingService {

    private final UserRepository userRepository;

    public SettingResponse.settingDTO getSetting(User user) {
        return SettingResponse.settingDTO.builder()
                .nickname(user.getNickname())
                .alarmTime(user.getAlarmTime())
                .pushAlarm(user.getPushAlarm())
                .alarmDay(user.getAlarmDay().stream()
                        .map(AlarmDay::name) // Enum을 String으로 변환
                        .collect(Collectors.toList())) // 리스트로 수집
                .build();
    }

    public SettingResponse.updateNicknameResultDTO updateNickname(User user, SettingRequest.updateNicknameDTO request) {
        return SettingResponse.updateNicknameResultDTO.builder()
                .nickname(user.updateNickname(request.nickname()))
                .build();
    }

    public SettingResponse.updateAlarmDayResultDTO updateAlarmDay(User user, SettingRequest.updateAlarmDayDTO request) {
        // 문자열 리스트를 AlarmDay enum 리스트로 변환
        List<AlarmDay> alarmDays = request.alarmDay().stream()
                .map(AlarmDay::valueOf) // 문자열을 AlarmDay enum으로 변환
                .collect(Collectors.toList()); // 리스트로 수집

        List<AlarmDay> updatedAlarmDays = user.updateAlarmDay(alarmDays);

        return SettingResponse.updateAlarmDayResultDTO.builder()
                .alarmDay(updatedAlarmDays.stream()
                        .map(Enum::name) // enum을 문자열로 변환
                        .collect(Collectors.toList())) // 리스트로 수집
                .build();
    }


    public SettingResponse.updatePushAlarmResultDTO updatePushAlarm(User user, SettingRequest.updatePushAlarmDTO request) {
        return SettingResponse.updatePushAlarmResultDTO.builder()
                .pushAlarm(user.updatePushAlarm(request.pushAlarm()))
                .build();
    }

    public SettingResponse.updateAlarmTimeResultDTO updateAlarmTime(User user, SettingRequest.updateAlarmTimeDTO request) {
        return SettingResponse.updateAlarmTimeResultDTO.builder()
                .alarmTime(user.updateAlarmTime(request.alarmTime()))
                .build();
    }
}
