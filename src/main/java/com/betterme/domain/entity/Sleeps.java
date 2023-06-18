package com.betterme.domain.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Duration;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@Entity
public class Sleeps extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private BetterMe betterMe;

    @Column
    private Long sleepGoal;

    @Column
    private LocalDateTime sleepTime;

    @Column
    private LocalDateTime wakeUpTime;

    @Column
    private boolean isSuccess;

    @Builder
    public Sleeps(BetterMe betterMe, LocalDateTime sleepTime, LocalDateTime wakeUpTime) {
        this.betterMe = betterMe;
        this.sleepGoal = Duration.between(sleepTime, wakeUpTime).toMinutes();
        this.sleepTime = sleepTime;
        this.wakeUpTime = wakeUpTime;
        this.isSuccess = false;
    }

    public void update(LocalDateTime sleepTime, LocalDateTime wakeUpTime, boolean isSuccess) {
        this.sleepTime = sleepTime;
        this.wakeUpTime = wakeUpTime;
        this.isSuccess = isSuccess;
        this.sleepGoal = Duration.between(sleepTime, wakeUpTime).toMinutes();
    }
}
