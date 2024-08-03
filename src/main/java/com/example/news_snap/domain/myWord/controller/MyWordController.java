package com.example.news_snap.domain.myWord.controller;

import com.example.news_snap.domain.myWord.dto.MyWordRequest;
import com.example.news_snap.domain.myWord.dto.MyWordResponse;
import com.example.news_snap.domain.myWord.service.MyWordService;
import com.example.news_snap.global.common.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/myWord/{userId}")
public class MyWordController {

    private final MyWordService myWordService;



    @Operation(summary = "내 단어장 조회", description = "내가 저장한 단어 목록 조회합니다.")
    @GetMapping("")
    public ApiResponse<List<MyWordResponse.MyWordDTO>> getMyWordList(@PathVariable(name="userId") Long userId) {
        return ApiResponse.onSuccess(myWordService.getMyWordList(userId));
    }


    @Operation(summary = "내 단어장 단어 추가", description = "내 단어장에 단어를 추가합니다.")
    @PostMapping("")
    public ApiResponse<MyWordResponse.addResultDTO> addMyWord(@PathVariable (name="userId") Long userId, @RequestBody MyWordRequest request) {
        return ApiResponse.onSuccess(myWordService.addMyWord(userId, request));
    }




}
