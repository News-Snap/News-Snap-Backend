package com.example.news_snap.domain.myWord.repository;

import com.example.news_snap.domain.myWord.entity.MyWord;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MyWordRepository extends JpaRepository<MyWord, Long> {
}
