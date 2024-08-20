package com.example.news_snap.domain.scrap.repository;

import com.example.news_snap.domain.login.entity.User;
import com.example.news_snap.domain.scrap.entity.Scrap;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ScrapRepository extends JpaRepository<Scrap, Long>, ScrapRepositoryCustom {
    @Query("SELECT a FROM Scrap a WHERE a.user = :user AND a != :scrap")
    List<Scrap> findAllByUserExceptScrap(User user, Scrap scrap);
}
