package com.example.news_snap.domain.news.dto;

import com.example.news_snap.global.util.JsoupCrawling;
import org.jsoup.select.Elements;

import java.util.Optional;

// 네이버 뉴스 API response items 중, 필요한 데이터
public record NewsSummaryDto(
        String title,
        String link,
        String description
) {

    // 2차적으로 최종 반환될 추가 필드(이미지, 출판사) 크롤링하여 Building
    public NewsResponseDto toNewsResponseDto(JsoupCrawling jsoupCrawling) {
        String imgQuery = "#contents img";
        String publisherQuery = ".media_end_head.go_trans img";
        Optional<Elements> jsoupElementsIMG = jsoupCrawling.getJsoupElements(link, imgQuery);
        Optional<Elements> jsoupElementsPUB = jsoupCrawling.getJsoupElements(link, publisherQuery);

        if (jsoupElementsIMG.isPresent() || jsoupElementsPUB.isPresent()) {
            return NewsResponseDto.builder()
                    .title(title)
                    .newsURL(link)
                    .imageURL(jsoupElementsIMG.get().attr("data-src")) // +
                    .publisher(jsoupElementsPUB.get().attr("title")) // +
                    .build();
        }

        // 이미지 혹은 출판사 없을 시, 그대로 Building 하여 반환
        return NewsResponseDto.builder()
                .title(title)
                .newsURL(link)
                .build();
    }
}
