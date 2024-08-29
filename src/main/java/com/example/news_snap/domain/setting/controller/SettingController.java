package com.example.news_snap.domain.setting.controller;



import com.example.news_snap.domain.login.entity.User;
import com.example.news_snap.domain.setting.dto.SettingRequest;
import com.example.news_snap.domain.setting.dto.SettingResponse;
import com.example.news_snap.domain.setting.service.SettingService;
import com.example.news_snap.global.common.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/setting")
public class SettingController {

    private final SettingService settingService;

    @Operation(summary = "설정사항 조회", description = "설정사항을 조회합니다.")
    @GetMapping("")
    public ApiResponse<SettingResponse.settingDTO> getSetting(
            @AuthenticationPrincipal User user) {
        return ApiResponse.onSuccess(settingService.getSetting(user));
    }

    @Operation(summary = "닉네임 수정", description = "닉네임을 수정합니다.")
    @PatchMapping("/nickname")
    public ApiResponse<SettingResponse.updateNicknameResultDTO> updateNickname(
            @AuthenticationPrincipal User user, @RequestBody SettingRequest.updateNicknameDTO request){
        return ApiResponse.onSuccess(settingService.updateNickname(user, request));
    }

    @Operation(summary = "푸쉬알람 여부 수정", description = "푸쉬알람 여부를 수정합니다.")
    @PatchMapping("/alarm")
    public ApiResponse<SettingResponse.updatePushAlarmResultDTO> updatePush(
            @AuthenticationPrincipal User user, @RequestBody SettingRequest.updatePushAlarmDTO request){
        return ApiResponse.onSuccess(settingService.updatePushAlarm(user, request));
    }

    @Operation(summary = "알람요일 수정", description = "알람요일을 수정합니다.")
    @PatchMapping("/day")
    public ApiResponse<SettingResponse.updateAlarmDayResultDTO> updateDay(
            @AuthenticationPrincipal User user, @RequestBody SettingRequest.updateAlarmDayDTO request){
        return ApiResponse.onSuccess(settingService.updateAlarmDay(user, request));
    }

    @Operation(summary = "알람시간 수정", description = "알람시간을 수정합니다.")
    @PatchMapping("/time")
    public ApiResponse<SettingResponse.updateAlarmTimeResultDTO> updateTime(
            @AuthenticationPrincipal User user, @RequestBody SettingRequest.updateAlarmTimeDTO request){
        return ApiResponse.onSuccess(settingService.updateAlarmTime(user, request));
    }


}
