package com.betterme.domain.dto.betterme;

import com.betterme.domain.entity.BetterMe;
import lombok.Builder;
import lombok.Getter;

import java.time.format.DateTimeFormatter;
import java.util.Map;

@Getter
public class BetterMeResponseDto {

    private final Long id;
    private final String usersName;
    private final String date;
    private final float progress;
    private final Map<String, String> habits;

    @Builder
    public BetterMeResponseDto(BetterMe betterMe) {
        this.id = betterMe.getId();
        this.usersName = betterMe.getUsers().getUsersName();
        this.date = betterMe.getCreatedDate().format(DateTimeFormatter.ofPattern("yyyy년 MM월 dd일"));
        this.habits = betterMe.getHabitsAndUrl();
        this.progress = betterMe.getProgress();
    }
}
