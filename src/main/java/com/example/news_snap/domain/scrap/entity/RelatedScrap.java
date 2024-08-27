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
public class RelatedScrap {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long relatedScrapId;

    private String relatedUrl;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "following_scrap_id")
    private Scrap followingScrap;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "follower_scrap_id")
    private Scrap followerScrap;
}
