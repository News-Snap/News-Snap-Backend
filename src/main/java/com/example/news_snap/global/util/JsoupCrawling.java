package com.example.news_snap.global.util;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Optional;

@Component
public class JsoupCrawling {

    public Connection getConnection(String url) {
        return Jsoup.connect(url);
    }

    public Optional<Elements> getJsoupElements(String url, String query) {

        Connection conn = getConnection(url);
        try {
            return Optional.of(conn.get().select(query));
        } catch (IOException e) {
            return Optional.empty();
        }
    }
}
