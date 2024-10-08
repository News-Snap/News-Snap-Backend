package com.example.news_snap.domain.news.controller;

import com.example.news_snap.domain.news.dto.NewsResponseDto;
import com.example.news_snap.domain.news.dto.NewsSearchOptions;
import com.example.news_snap.domain.news.service.NewsService;
import com.example.news_snap.global.common.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/news")
@RequiredArgsConstructor
@Tag(name = "News API")
public class NewsController {

    private final NewsService newsService;

    @Operation(summary = "헤드라인 뉴스 목록 조회", description = "Naver 경제 헤드라인 뉴스를 모두(10개) 조회합니다.")
    @GetMapping("/head-line")
    public ApiResponse<List<NewsResponseDto>> getHeadLineNews() {
        return ApiResponse.onSuccess(newsService.getHeadLineNews());
    }

    @Operation(summary = "뉴스 키워드 조회", description = "네이버 뉴스를 키워드로 검색합니다.")
    @GetMapping()
    public ApiResponse<?> searchNews(
            NewsSearchOptions searchOptions
    ) {
        return ApiResponse.onSuccess(newsService.searchNews(searchOptions));
    }

    @Operation(summary = "인기 검색어 조회", description = "네이버 증권의 인기 검색 키워드 top5를 조회합니다.")
    @GetMapping("/popular-keywords")
    public ApiResponse<?> getPopularKeywords() {
        return ApiResponse.onSuccess(newsService.getPopularStocks());
    }
}
