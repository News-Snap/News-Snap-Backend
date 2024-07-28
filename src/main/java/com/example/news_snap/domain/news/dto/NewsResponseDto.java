package com.example.news_snap.domain.news.dto;

import lombok.Builder;

@Builder
public record NewsResponseDto(
        String title, // 기사 제목
        String imageURL, // 이미지 URL
        String newsURL, // 뉴스 URL
        String publisher, // 출판사
//        String replyCnt, // 댓글 수
        String relatedNewsCnt, // 관련 뉴스 수
        String relatedURL // 관련 뉴스 URL
) {
}
