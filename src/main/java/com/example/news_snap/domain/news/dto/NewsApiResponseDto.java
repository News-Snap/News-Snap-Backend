package com.example.news_snap.domain.news.dto;

import com.example.news_snap.global.util.JsoupCrawling;

import java.util.List;
import java.util.stream.Collectors;

public record NewsApiResponseDto(
        List<NewsSummaryDto> items
) {

    // 네이버 뉴스 API로 조회한 Items의 네이버 뉴스만 필터링
    public List<NewsSummaryDto> getOnlyNaverNews() {
        return items.stream()
                .filter(item -> item.link().startsWith("https://n.news")) // HTML구조가 상이하여, 네이버 뉴스만
                .collect(Collectors.toList());
    }

    // 네이버 뉴스 링크로 접속하여 최종적으로 반환해줄 Dto 필드 크롤링하여 할당
    public List<NewsResponseDto> getNewsResponseDto() {
        return getOnlyNaverNews().stream()
                .map(item -> item.toNewsResponseDto(new JsoupCrawling()))
                .collect(Collectors.toList());
    }
}
