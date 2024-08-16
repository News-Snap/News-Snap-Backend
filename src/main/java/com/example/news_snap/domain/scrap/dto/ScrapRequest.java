package com.example.news_snap.domain.scrap.dto;

import com.example.news_snap.domain.scrap.entity.enums.FinancialTerms;

import java.time.LocalDate;
import java.util.List;

public record ScrapRequest() {

    public record ContentDto(
            String title,
            List<FinancialTerms> keywords,
            String articleUrl,
            LocalDate articleCreatedAt,
            String content,
            List<String> relatedUrlList
    ) {}
}
