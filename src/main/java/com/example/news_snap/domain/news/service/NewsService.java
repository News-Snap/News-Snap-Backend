package com.example.news_snap.domain.news.service;

import com.example.news_snap.domain.news.dto.NewsResponseDto;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class NewsService {
    private final String NAVER_NEWS_URL = "https://news.naver.com";
    private final String ECONOMIC_NEWS_URL = NAVER_NEWS_URL + "/section/101"; //네이버 경제 뉴스 URL
    private final String HEADLINE_CLASS = ".sa_item._SECTION_HEADLINE";

    public List<NewsResponseDto> getHeadLineNews() {

        List<NewsResponseDto> newsList = new ArrayList<>();

        try {
            Document document = Jsoup.connect(ECONOMIC_NEWS_URL).get();
            Elements headLineList = document.select(HEADLINE_CLASS);

            for (Element e : headLineList) {
                String imageURL = e.select(".sa_thumb_inner").select("img").attr("data-src");
                String newsURL = e.select(".sa_text").select("a").attr("href");
                String title = e.select(".sa_text").select(".sa_text_strong").text();
                String publisher = e.select(".sa_text_press").text();
//                String replyCnt = e.select(".sa_text_info_left").select(".sa_text_cmt_COMMENT_COUNT_LIST").text();
                String relatedURL = e.select(".sa_text_info_right > a").attr("href");
                String relatedNewsCnt = e.select(".sa_text_info_right").select(".sa_text_cluster_num").text();

                NewsResponseDto news = NewsResponseDto.builder()
                        .imageURL(imageURL)
                        .newsURL(newsURL)
                        .title(title)
                        .publisher(publisher)
//                        .replyCnt(replyCnt)
                        .relatedURL(NAVER_NEWS_URL + relatedURL)
                        .relatedNewsCnt(relatedNewsCnt)
                        .build();

                newsList.add(news);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return newsList;
    }
}
