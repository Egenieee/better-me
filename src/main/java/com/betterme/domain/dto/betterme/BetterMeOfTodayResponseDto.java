package com.betterme.domain.dto.betterme;

import com.betterme.domain.entity.BetterMe;
import lombok.Builder;
import lombok.Getter;

import java.util.Map;

@Getter
public class BetterMeOfTodayResponseDto {

    private final Long id;
    private final String usersName;
    private final float progress;
    private final Map<String, String> habits;

    @Builder
    public BetterMeOfTodayResponseDto(BetterMe betterMe) {
        this.id = betterMe.getId();
        this.usersName = betterMe.getUsers().getUsersName();
        this.habits = betterMe.getHabitsAndUrl();
        this.progress = betterMe.getProgress();
    }
}
