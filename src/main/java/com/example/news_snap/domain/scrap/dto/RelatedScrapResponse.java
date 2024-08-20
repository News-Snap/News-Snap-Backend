package com.example.news_snap.domain.scrap.dto;

import com.example.news_snap.domain.scrap.entity.enums.FinancialTerms;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;

public record RelatedScrapResponse() {

    @Builder
    public record PreviewDto (
        String title,
        List<FinancialTerms> termList,
        LocalDateTime updatedAt
    ) {}

    @Builder
    public record DetailDto (
        Long relateScrapId,
        String relatedUrl,
        Long scrapId,
        Long followingScrapId
    ) {}
}
