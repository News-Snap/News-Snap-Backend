package com.example.news_snap.domain.scrap.repository;

import com.example.news_snap.domain.scrap.entity.ScrapKeyword;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScrapKeywordRepository extends JpaRepository<ScrapKeyword, Long> {
}
