package com.example.news_snap.domain.myWord.entity;

import com.example.news_snap.domain.login.entity.User;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MyWord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String word;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;


    public void initializeUser(User user) {
        this.user = user;
        user.getMyWordList().add(this);
    }

}
