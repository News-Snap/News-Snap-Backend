package com.example.news_snap.domain.login.entity;

import com.example.news_snap.domain.myWord.entity.MyWord;
import com.example.news_snap.domain.scrap.entity.Scrap;
import com.example.news_snap.global.common.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
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

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<MyWord> myWordList = new ArrayList<>();

    // 커스터마이징된 toString() 메서드
    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", nickname='" + nickname + '\'' +
                ", password='" + password + '\'' +
                ", role='" + role + '\'' +
                ", authProvider='" + authProvider + '\'' +
                ", email='" + email + '\'' +
                ", alarmTime=" + alarmTime +
                ", birthDate=" + birthDate +
                ", pushAlarm=" + pushAlarm +
                ", inactivatedAt=" + inactivatedAt +
                ", status=" + status +
                ", alarmDay=" + alarmDay +
                '}';
    }

    public String updateNickname(String newNickname){
        this.nickname = newNickname;
        return this.nickname;
    }

    public String updateAlarmDay(String newAlarmDay){
        this.alarmDay = AlarmDay.valueOf(newAlarmDay);
        return this.alarmDay.name();
    }

    public LocalTime updateAlarmTime(LocalTime newAlarmTime){
        this.alarmTime = newAlarmTime;
        return this.alarmTime;
    }
    public boolean updatePushAlarm(Boolean newPushAlarm){
        this.pushAlarm = newPushAlarm;
        return this.pushAlarm;
    }
}

