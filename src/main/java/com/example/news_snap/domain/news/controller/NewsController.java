package com.example.news_snap.domain.news.controller;

import com.example.news_snap.domain.news.service.NewsService;
import com.example.news_snap.global.common.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/news")
@RequiredArgsConstructor
@Tag(name = "News API")
public class NewsController {

    private final NewsService newsService;

    @Operation(summary = "헤드라인 뉴스 목록 조회", description = "Naver 경제 헤드라인 뉴스를 모두(10개) 조회합니다.")
    @GetMapping("/head-line")
    public ApiResponse<?> getHeadLineNews() {
        return ApiResponse.onSuccess(newsService.getHeadLineNews());
    }
}
