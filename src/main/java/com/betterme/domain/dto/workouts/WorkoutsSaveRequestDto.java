package com.betterme.domain.dto.workouts;

import com.betterme.domain.entity.BetterMe;
import com.betterme.domain.entity.Workouts;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WorkoutsSaveRequestDto {

    private Long betterMeId;

    private String name;

    private String details;

    public Workouts toEntity(BetterMe betterMe) {
        return Workouts.builder()
                .betterMe(betterMe)
                .name(name)
                .details(details)
                .build();
    }
}
