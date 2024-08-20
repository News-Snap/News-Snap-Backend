package com.example.news_snap.domain.scrap.repository;

import com.example.news_snap.domain.login.entity.User;
import com.example.news_snap.domain.scrap.entity.RelatedScrap;
import com.example.news_snap.domain.scrap.entity.Scrap;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RelatedScrapRepository extends JpaRepository<RelatedScrap, Long> {
}
