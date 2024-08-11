package com.example.news_snap.domain.news.repository;

import com.example.news_snap.domain.news.dto.NewsApiResponseDto;
import com.example.news_snap.domain.news.dto.NewsSearchOptions;
import com.example.news_snap.global.common.code.status.ErrorStatus;
import com.example.news_snap.global.common.exception.handler.NewsHandler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Repository
public class NewsApiResponseRepository {

    @Value("${X-Naver-Client-Id}")
    private String newsApiClient;
    @Value("${X-Naver-Client-Secret}")
    private String newsApiKey;

    // 네이버 뉴스 API로 뉴스 items를 검색해 가져오는 메서드
    public Optional<NewsApiResponseDto> getNewsApiResponseDto(NewsSearchOptions options) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = getNaverApiRequestHeaders();

        String url = "https://openapi.naver.com/v1/search/news.json?query=" + options.getQuery()
                + "&display=" + options.getDisplay()
                + "&start=" + options.getStart()
                + "&sort=" + options.getSort();
        ResponseEntity<NewsApiResponseDto> newsApiResponseDtoEntity = restTemplate.exchange(url, HttpMethod.GET,
                new HttpEntity<>(headers), NewsApiResponseDto.class);

        if (newsApiResponseDtoEntity.getStatusCode().is2xxSuccessful()) {
            return Optional.ofNullable(newsApiResponseDtoEntity.getBody());
        }

        // 네이버 뉴스 API가 정상적으로 호출이 안될 시 예외처리
        throw new NewsHandler(ErrorStatus._UNAVAILABLE_NEWS_API);
    }

    // 네이버 앱 ClientID 및 Secret key Header 할당하는 코드
    private HttpHeaders getNaverApiRequestHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-Naver-Client-Id", newsApiClient);
        headers.set("X-Naver-Client-Secret", newsApiKey);
        return headers;
    }
}
