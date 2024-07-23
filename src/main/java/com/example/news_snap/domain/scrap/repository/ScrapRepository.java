package com.example.news_snap.domain.scrap.repository;

import com.example.news_snap.domain.scrap.entity.Scrap;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScrapRepository extends JpaRepository<Scrap, Long> {
}
