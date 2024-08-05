package com.example.news_snap.domain.scrap.repository;

import com.example.news_snap.domain.login.entity.User;
import com.example.news_snap.domain.scrap.dto.ScrapResponse;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ScrapRepositoryCustom {
    List<ScrapResponse.PreviewDto> findScrapByConditions(User user, String keyword, LocalDate date);

    List<ScrapResponse.KeywordDto> findTopKeywordByUser(User user);
}
