package com.example.news_snap.domain.user.entity;
/*
import com.example.news_snap.domain.scrap.entity.Scrap;
import com.example.news_snap.domain.scrap.entity.enums.Status;
import com.example.news_snap.global.common.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    private String nickname;
    private String alarmTime;
    private String email;
    private String password;
    private String birthDate;
    private Status status;
    private LocalDate inactiveAt;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Scrap> scrapList = new ArrayList<>();
}
*/