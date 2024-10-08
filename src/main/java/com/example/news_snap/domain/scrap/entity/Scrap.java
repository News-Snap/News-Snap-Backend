package com.example.news_snap.domain.scrap.entity;

import com.example.news_snap.domain.login.entity.User;
import com.example.news_snap.domain.scrap.dto.ScrapRequest;
import com.example.news_snap.domain.scrap.entity.enums.FinancialTerms;
import com.example.news_snap.global.common.BaseEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Scrap extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "scrap_id")
    private Long scrapId;

    private String articleUrl;
    private String content;
    private String title;
    private String fileUrl;
    private LocalDate articleCreatedAt;

    @OneToMany(mappedBy = "scrap", cascade = CascadeType.ALL)
    private List<Keyword> keywordList = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "followerScrap", cascade = CascadeType.ALL)
    private List<RelatedScrap> followingRelatedScrapList = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "followingScrap", cascade = CascadeType.ALL)
    private List<RelatedScrap> followerRelatedScrapList = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    public void update(ScrapRequest.ContentDto request) {
        this.articleUrl = request.articleUrl();
        this.content = request.content();
        this.title = request.title();
    }

    public void uploadFile(String url) {
        this.fileUrl = url;
    }

    public List<FinancialTerms> getTermList() {
        List<FinancialTerms> financialTermsList = new ArrayList<>();

        for (Keyword keyword : keywordList) {
            financialTermsList.add(keyword.getTerm());
        }

        return financialTermsList;
    }
}
