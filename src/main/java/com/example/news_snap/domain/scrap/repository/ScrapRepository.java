package com.example.news_snap.domain.scrap.repository;

import com.example.news_snap.domain.scrap.entity.Scrap;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ScrapRepository extends JpaRepository<Scrap, Long>, ScrapRepositoryCustom {
}
