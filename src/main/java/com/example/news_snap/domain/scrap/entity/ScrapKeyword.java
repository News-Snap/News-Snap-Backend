package com.example.news_snap.domain.scrap.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ScrapKeyword {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long scrapKeywordId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "keyword_id")
    private Keyword keyword;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "scrap_id")
    private Scrap scrap;
}
