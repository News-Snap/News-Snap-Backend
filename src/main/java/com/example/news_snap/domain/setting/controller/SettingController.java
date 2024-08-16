package com.example.news_snap.domain.setting.controller;



import com.example.news_snap.domain.setting.dto.SettingRequest;
import com.example.news_snap.domain.setting.dto.SettingResponse;
import com.example.news_snap.domain.setting.service.SettingService;
import com.example.news_snap.global.common.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/setting/{userId}")
public class SettingController {

    private final SettingService settingService;

    // 설정사항
    @GetMapping("")
    public ApiResponse<SettingResponse.settingDTO> getSetting(@PathVariable Long userId) {
        return ApiResponse.onSuccess(settingService.getSetting(userId));
    }

    // 닉네임 수정
    @PatchMapping("/nickname")
    public ApiResponse<SettingResponse.updateNicknameResultDTO> updateNickname(@PathVariable Long userId, @RequestBody SettingRequest.updateNicknameDTO request){
        return ApiResponse.onSuccess(settingService.updateNickname(userId, request));
    }

    // 푸쉬알람 여부
    @PatchMapping("/alarm")
    public ApiResponse<SettingResponse.updatePushAlarmResultDTO> updatePush(@PathVariable Long userId, @RequestBody SettingRequest.updatePushAlarmDTO request){
        return ApiResponse.onSuccess(settingService.updatePushAlarm(userId, request));
    }

    // 요일 수정
    @PatchMapping("/day")
    public ApiResponse<SettingResponse.updateAlarmDayResultDTO> updateDay(@PathVariable Long userId, @RequestBody SettingRequest.updateAlarmDayDTO request){
        return ApiResponse.onSuccess(settingService.updateAlarmDay(userId, request));
    }

    //시간 수정
    @PatchMapping("/time")
    public ApiResponse<SettingResponse.updateAlarmTimeResultDTO> updateTime(@PathVariable Long userId, @RequestBody SettingRequest.updateAlarmTimeDTO request){
        return ApiResponse.onSuccess(settingService.updateAlarmTime(userId, request));
    }

    // 개선사항 추가
    @PostMapping("/improvement")
    public ApiResponse<SettingResponse.addImprovementResultDTO> addImprovement(@PathVariable Long userId, @RequestBody SettingRequest.addImprovementDTO request){
        return ApiResponse.onSuccess(settingService.);
    }

}
