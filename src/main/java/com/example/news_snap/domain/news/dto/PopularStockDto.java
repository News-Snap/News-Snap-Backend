package com.example.news_snap.domain.news.dto;

import lombok.Builder;

@Builder
public record PopularStockDto(
        String stock,
        Integer rank
) {}
