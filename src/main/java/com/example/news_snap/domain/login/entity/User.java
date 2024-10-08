package com.example.news_snap.domain.login.entity;

import com.example.news_snap.domain.myWord.entity.MyWord;
import com.example.news_snap.domain.scrap.entity.Scrap;
import com.example.news_snap.global.common.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

    @Column(name = "roles")
    @ElementCollection(fetch = FetchType.EAGER)
    @Builder.Default
    private List<Authority> roles = new ArrayList<>();            //사용자 role (ex 어드민, 일반사용자 등)

    @Column(name= "auth_provider", length = 20)
    @Enumerated(EnumType.STRING)
    @NotNull
    private AuthProvider authProvider;    //OAuth 유저 정보 제공자

    @Column(nullable = false)
    private String email;

    private LocalTime alarmTime;


    private Boolean pushAlarm;

    private LocalDate inactivatedAt;

    @Enumerated(EnumType.STRING)
    private Status status;

    @ElementCollection(targetClass = AlarmDay.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "user_alarm_days", joinColumns = @JoinColumn(name = "user_id"))
    @Enumerated(EnumType.STRING)
    @Column(name = "alarm_day", nullable = false)
    private List<AlarmDay> alarmDay = new ArrayList<>();         //알람 요일

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
                ", roles='" + roles + '\'' +
                ", authProvider='" + authProvider + '\'' +
                ", email='" + email + '\'' +
                ", alarmTime=" + alarmTime +
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


    public List<AlarmDay> updateAlarmDay(List<AlarmDay> newAlarmDays) {
        // 문자열 리스트를 AlarmDay enum 리스트로 변환
        this.alarmDay = newAlarmDays; // 리스트로 수집

        return this.alarmDay; // 업데이트된 AlarmDay 리스트를 반환
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

