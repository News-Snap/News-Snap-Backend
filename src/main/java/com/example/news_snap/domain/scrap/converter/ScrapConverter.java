package com.example.news_snap.domain.scrap.converter;

import com.example.news_snap.domain.scrap.entity.Keyword;
import com.example.news_snap.domain.scrap.entity.RelatedScrap;
import com.example.news_snap.domain.scrap.entity.Scrap;
import com.example.news_snap.domain.scrap.entity.enums.FinancialTerms;

public class ScrapConverter {

    public static Keyword toKeyword(FinancialTerms term, Scrap scrap) {
        return Keyword.builder()
                .term(term)
                .scrap(scrap)
                .build();
    }

    public static RelatedScrap toRelatedScrap(String url, Scrap savedScrap) {
        return RelatedScrap.builder()
                .relatedUrl(url)
                .build();
    }
}
