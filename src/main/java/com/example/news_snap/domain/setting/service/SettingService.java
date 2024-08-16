package com.example.news_snap.domain.setting.service;

import com.example.news_snap.domain.login.entity.AlarmDay;
import com.example.news_snap.domain.login.entity.User;
import com.example.news_snap.domain.login.repository.UserRepository;
import com.example.news_snap.domain.setting.dto.SettingRequest;
import com.example.news_snap.domain.setting.dto.SettingResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class SettingService {

    private final UserRepository userRepository;

    public SettingResponse.settingDTO getSetting(Long userId){
        User user = userRepository.findById(userId).orElse(null);
        return SettingResponse.settingDTO.builder()
                .nickname(user.getNickname())
                .alarmTime(user.getAlarmTime())
                .pushAlarm(user.getPushAlarm())
                .alarmDay(user.getAlarmDay())
                .build();
    }

    public SettingResponse.updateNicknameResultDTO updateNickname(Long userId, SettingRequest.updateNicknameDTO request){
        User user = userRepository.findById(userId).orElse(null);
        return SettingResponse.updateNicknameResultDTO.builder()
                        .nickname(user.updateNickname(request.nickname()))
                .build();
    }

    public SettingResponse.updateAlarmDayResultDTO updateAlarmDay(Long userId, SettingRequest.updateAlarmDayDTO request){
        User user = userRepository.findById(userId).orElse(null);
        return SettingResponse.updateAlarmDayResultDTO.builder()
                .alarmDay(user.updateAlarmDay(request.alarmDay()))
                .build();
    }

    public SettingResponse.updatePushAlarmResultDTO updatePushAlarm(Long userId, SettingRequest.updatePushAlarmDTO request){
        User user = userRepository.findById(userId).orElse(null);
        return SettingResponse.updatePushAlarmResultDTO.builder()
                .pushAlarm(user.updatePushAlarm(request.pushAlarm()))
                .build();
    }

    public SettingResponse.updateAlarmTimeResultDTO updateAlarmTime(Long userId, SettingRequest.updateAlarmTimeDTO request){
        User user = userRepository.findById(userId).orElse(null);
        return SettingResponse.updateAlarmTimeResultDTO.builder()
                .alarmTime(user.updateAlarmTime(request.alarmTime()))
                .build();
    }




}
