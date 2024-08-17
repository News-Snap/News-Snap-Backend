package com.example.news_snap.domain.news.repository;

import com.example.news_snap.domain.news.dto.PopularStockDto;
import com.example.news_snap.domain.news.dto.PopularStockResponseDto;
import com.example.news_snap.global.util.JsoupCrawling;
import lombok.RequiredArgsConstructor;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class CrawlingRepository {

    private final JsoupCrawling jsoupCrawling;

    public PopularStockResponseDto getPopularStockResponseDto() {

        final String NAVER_PAY_FINANCE_URL = "https://finance.naver.com/";
        String query = ".aside_area.aside_popular table tbody tr"; // 인기검색어 컴포넌트

        List<PopularStockDto> stockDtoList = new ArrayList<>();

        jsoupCrawling.getJsoupElements(NAVER_PAY_FINANCE_URL, query).get().stream()
                .forEach(element ->
                        {
                            PopularStockDto build = PopularStockDto.builder()
                                    .stock(element.select("th").text().substring(2))
                                    .rank(Integer.parseInt(element.select("th em").text().substring(0, 1)))
                                    .build();
                            stockDtoList.add(build);
                        }
                );

        return new PopularStockResponseDto(stockDtoList);
    }
}
