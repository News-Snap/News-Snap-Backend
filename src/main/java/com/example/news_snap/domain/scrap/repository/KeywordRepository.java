package com.example.news_snap.domain.scrap.repository;

import com.example.news_snap.domain.scrap.entity.Keyword;
import org.springframework.data.jpa.repository.JpaRepository;

public interface KeywordRepository extends JpaRepository<Keyword, Long> {
}
