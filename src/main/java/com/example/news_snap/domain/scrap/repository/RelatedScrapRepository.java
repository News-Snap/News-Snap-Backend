package com.example.news_snap.domain.scrap.repository;

import com.example.news_snap.domain.scrap.entity.RelatedScrap;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RelatedScrapRepository extends JpaRepository<RelatedScrap, Long> {
}
