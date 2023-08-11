package com.betterme.domain.dto.sleeps;

import jakarta.validation.constraints.AssertTrue;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

@Getter
@Setter
public class SleepsUpdateRequestDto {

    private Long sleepsId;

    private Boolean isSuccess;

    private String sleepDate;
    private String sleepTime;
    private String wakeUpDate;
    private String wakeUpTime;

    @AssertTrue(message = "취침 날짜의 형식이 잘못되었습니다. (예: 2024-01-01)")
    public boolean isSleepTimeDateValidFormat() {
        try {
            LocalDate sleepDate = LocalDate.parse(getSleepDate(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    @AssertTrue(message = "취침 시간의 형식이 잘못되었습니다. (예: 22:00)")
    public boolean isSleepTimeValidFormat() {
        try {
            LocalTime sleepTime = LocalTime.parse(getSleepTime(), DateTimeFormatter.ofPattern("HH:mm"));
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    @AssertTrue(message = "기상 날짜의 형식이 잘못되었습니다. (예: 2024-01-01)")
    public boolean isWakeUpDateValidFormat() {
        try {
            LocalDate wakeUpDate = LocalDate.parse(getWakeUpDate(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    @AssertTrue(message = "기상 시간의 형식이 잘못되었습니다. (예: 07:00)")
    public boolean isWakeUpTimeValidFormat() {
        try {
            LocalTime wakeUpTime = LocalTime.parse(getWakeUpTime(), DateTimeFormatter.ofPattern("HH:mm"));
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    @AssertTrue(message = "취침 시간은 기상 시간보다 이전이어야 합니다.")
    public boolean isSleepTimeBeforeWakeUpTime() {
        try {
            LocalDate sleepDate = LocalDate.parse(getSleepDate());
            LocalTime sleepTime = LocalTime.parse(getSleepTime());

            LocalDate wakeUpDate = LocalDate.parse(getWakeUpDate());
            LocalTime wakeUpTime = LocalTime.parse(getWakeUpTime());

            LocalDateTime sleepDateTime = sleepDate.atTime(sleepTime);
            LocalDateTime wakeUpDateTime = wakeUpDate.atTime(wakeUpTime);

            return sleepDateTime.isBefore(wakeUpDateTime);
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    @Builder
    public SleepsUpdateRequestDto(String sleepDate, String sleepTime, String wakeUpDate, String wakeUpTime, boolean isSuccess, Long sleepsId) {
        this.sleepsId = sleepsId;
        this.isSuccess = isSuccess;
        this.sleepDate = sleepDate;
        this.sleepTime = sleepTime;
        this.wakeUpDate = wakeUpDate;
        this.wakeUpTime = wakeUpTime;
    }
}
