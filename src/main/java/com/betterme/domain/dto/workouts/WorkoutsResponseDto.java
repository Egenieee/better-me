package com.betterme.domain.dto.workouts;

import com.betterme.domain.entity.Workouts;
import lombok.Builder;
import lombok.Getter;

@Getter
public class WorkoutsResponseDto {

    private final Long workoutsId;

    private final String name;
    private final String details;

    private final boolean isCompleted;

    @Builder
    public WorkoutsResponseDto(Workouts workouts) {
        this.workoutsId = workouts.getId();
        this.name = workouts.getName();
        this.details = workouts.getDetails();
        this.isCompleted = workouts.isCompleted();
    }
}
