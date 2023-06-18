package com.betterme.domain.dto.sleeps;

import com.betterme.domain.entity.Sleeps;
import lombok.Getter;

import java.time.format.DateTimeFormatter;

@Getter
public class SleepsResponseDto {

    private final Long sleepsId;

    private final String sleepsGoal;
    private final String sleepTime;
    private final String sleepDate;
    private final String wakeUpDate;
    private final String wakeUpTime;
    private final Boolean isSuccess;

    public SleepsResponseDto(Sleeps sleeps) {
        this.sleepsId = sleeps.getId();
        this.sleepsGoal = sleeps.getSleepGoal() / 60 + "시간";
        this.sleepDate = sleeps.getSleepTime().toLocalDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        this.sleepTime = sleeps.getSleepTime().toLocalTime().format(DateTimeFormatter.ofPattern("HH시 mm분 취침"));
        this.wakeUpDate = sleeps.getWakeUpTime().toLocalDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        this.wakeUpTime = sleeps.getWakeUpTime().toLocalTime().format(DateTimeFormatter.ofPattern("HH시 mm분 기상"));
        this.isSuccess = sleeps.isSuccess();
    }
}
