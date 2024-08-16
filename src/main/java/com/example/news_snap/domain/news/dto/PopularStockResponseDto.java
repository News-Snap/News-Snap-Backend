package com.example.news_snap.domain.news.dto;

import lombok.Builder;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Builder
public record PopularStockResponseDto(
        List<PopularStockDto> popularStocks
) {

    public PopularStockResponseDto sortByRank() {
        List<PopularStockDto> collect = popularStocks.stream()
                .sorted(Comparator.comparingInt(PopularStockDto::rank).reversed())
                .collect(Collectors.toList());

        return new PopularStockResponseDto(collect);
    }
}
