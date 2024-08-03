package com.example.news_snap.domain.login.entity;

import com.example.news_snap.domain.scrap.entity.Scrap;
import com.example.news_snap.global.common.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = "email")},name = "user")
public class User extends BaseEntity {
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    private Long userId;

    private String nickname;

    private String password;

    private String role;            //사용자 role (ex 어드민, 일반사용자 등)

    private String authProvider;    //OAuth 유저 정보 제공자

    @Column(nullable = false)
    private String email;

    private LocalTime alarmTime;

    private LocalDate birthDate;

    private Boolean pushAlarm;

    private LocalDate inactivatedAt;

    @Enumerated(EnumType.STRING)
    private Status status;

    @Enumerated(EnumType.STRING)
    private AlarmDay alarmDay;            //알람 요일

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Scrap> scrapList = new ArrayList<>();
}
