package com.example.news_snap.domain.setting.controller;



import com.example.news_snap.domain.setting.dto.SettingRequest;
import com.example.news_snap.domain.setting.dto.SettingResponse;
import com.example.news_snap.domain.setting.service.SettingService;
import com.example.news_snap.global.common.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/setting/{userId}")
public class SettingController {

    private final SettingService settingService;

    @Operation(summary = "설정사항 조회", description = "설정사항을 조회합니다.")
    @GetMapping("")
    public ApiResponse<SettingResponse.settingDTO> getSetting(@PathVariable(name="userId") Long userId) {
        return ApiResponse.onSuccess(settingService.getSetting(userId));
    }

    @Operation(summary = "닉네임 수정", description = "닉네임을 수정합니다.")
    @PatchMapping("/nickname")
    public ApiResponse<SettingResponse.updateNicknameResultDTO> updateNickname(@PathVariable(name="userId") Long userId, @RequestBody SettingRequest.updateNicknameDTO request){
        return ApiResponse.onSuccess(settingService.updateNickname(userId, request));
    }

    @Operation(summary = "푸쉬알람 여부 수정", description = "푸쉬알람 여부를 수정합니다.")
    @PatchMapping("/alarm")
    public ApiResponse<SettingResponse.updatePushAlarmResultDTO> updatePush(@PathVariable(name="userId") Long userId, @RequestBody SettingRequest.updatePushAlarmDTO request){
        return ApiResponse.onSuccess(settingService.updatePushAlarm(userId, request));
    }

    @Operation(summary = "알람요일 수정", description = "알람요일을 수정합니다.")
    @PatchMapping("/day")
    public ApiResponse<SettingResponse.updateAlarmDayResultDTO> updateDay(@PathVariable(name="userId") Long userId, @RequestBody SettingRequest.updateAlarmDayDTO request){
        return ApiResponse.onSuccess(settingService.updateAlarmDay(userId, request));
    }

    @Operation(summary = "알람시간 수정", description = "알람시간을 수정합니다.")
    @PatchMapping("/time")
    public ApiResponse<SettingResponse.updateAlarmTimeResultDTO> updateTime(@PathVariable(name="userId") Long userId, @RequestBody SettingRequest.updateAlarmTimeDTO request){
        return ApiResponse.onSuccess(settingService.updateAlarmTime(userId, request));
    }


}
