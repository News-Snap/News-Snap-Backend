package com.example.news_snap.domain.scrap.entity;

import com.example.news_snap.domain.scrap.entity.enums.Word;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Keyword {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long keywordId;

    private Word word;

    @OneToMany(mappedBy = "keyword", cascade = CascadeType.ALL)
    private List<ScrapKeyword> scrapKeywordList = new ArrayList<>();
}
