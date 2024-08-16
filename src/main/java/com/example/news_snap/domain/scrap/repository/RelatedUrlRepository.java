package com.example.news_snap.domain.scrap.repository;

import com.example.news_snap.domain.scrap.entity.RelatedUrl;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RelatedUrlRepository extends JpaRepository<RelatedUrl, Long> {
}
