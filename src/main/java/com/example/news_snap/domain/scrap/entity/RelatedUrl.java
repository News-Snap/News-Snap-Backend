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
public class RelatedUrl {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long relatedUrlId;

    private String relatedUrl;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "scrap_id")
    private Scrap scrap;
}
