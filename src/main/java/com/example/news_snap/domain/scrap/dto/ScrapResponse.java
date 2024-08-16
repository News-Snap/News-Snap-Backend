package com.example.news_snap.domain.scrap.dto;

import com.example.news_snap.domain.scrap.entity.enums.FinancialTerms;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Builder
public record ScrapResponse() {

    @Builder
    public record PreviewDto(
            Long scrapId,
            String title,
            List<String> keywords,
            LocalDateTime updatedAt
    ) {
        public PreviewDto {
            keywords = keywords != null ? keywords : new ArrayList<>();
        }
    }
    @Builder
    public record DetailDto(
            String title,
            String content,
            String articleUrl,
            List<FinancialTerms> keywords,
            LocalDateTime modifiedAt,
            String fileUrl,
            List<String> relatedUrlList
    ) {
    }

    @Builder
    public record IdDto(
            Long scrapId
    ) {
    }

    @Builder
    public record KeywordDto (
            String keyword
    ) {}
}
