package com.example.news_snap.domain.scrap.converter;

import com.example.news_snap.domain.scrap.entity.Keyword;
import com.example.news_snap.domain.scrap.entity.RelatedUrl;
import com.example.news_snap.domain.scrap.entity.Scrap;
import com.example.news_snap.domain.scrap.entity.enums.FinancialTerms;

import java.util.List;

public class ScrapConverter {

    public static Keyword toKeyword(FinancialTerms term, Scrap scrap) {
        return Keyword.builder()
                .term(term)
                .scrap(scrap)
                .build();
    }

    public static RelatedUrl toRelatedUrl(String url, Scrap savedScrap) {
        return RelatedUrl.builder()
                .relatedUrl(url)
                .scrap(savedScrap)
                .build();
    }
}
