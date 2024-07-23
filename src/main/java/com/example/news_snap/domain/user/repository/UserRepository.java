package com.example.news_snap.domain.user.repository;

import com.example.news_snap.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
