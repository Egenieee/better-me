package com.betterme.domain.dto.workouts;

import lombok.Builder;
import lombok.Getter;

@Getter
public class WorkoutsUpdateRequestDto {

    private final Long workoutsId;

    private final Long betterMeId;

    private final String name;
    private final String details;

    private final Boolean isCompleted;

    @Builder
    public WorkoutsUpdateRequestDto(Long workoutsId, Long betterMeId, String name, String details, boolean isCompleted) {
        this.workoutsId = workoutsId;
        this.betterMeId = betterMeId;
        this.name = name;
        this.details = details;
        this.isCompleted = isCompleted;
    }
}
