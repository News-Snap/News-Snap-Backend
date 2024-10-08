package com.example.news_snap.domain.login.repository;

import com.example.news_snap.domain.login.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);
    User findByEmailAndPassword(String email, String password);
    boolean existsByEmail(String email);

}
