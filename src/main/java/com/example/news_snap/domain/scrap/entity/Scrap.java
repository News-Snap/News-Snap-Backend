package com.example.news_snap.domain.scrap.entity;

import com.example.news_snap.domain.user.entity.User;
import com.example.news_snap.global.common.BaseEntity;
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
    private Long scrapId;

    private String articleUrl;
    private String content;
    private String title;
    private String fileUrl;
    private String link;
    private LocalDate articleCreatedAt;

    @OneToMany(mappedBy = "scrap", cascade = CascadeType.ALL)
    private List<ScrapKeyword> scrapKeywordList = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
}
