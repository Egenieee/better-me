package com.betterme.domain.dto.nutrients;

import lombok.Builder;
import lombok.Getter;

@Getter
public class NutrientsUpdateRequestDto {

    private final Long betterMeId;

    private final Long nutrientsId;

    private final String name;
    private final Boolean isTaken;

    @Builder
    public NutrientsUpdateRequestDto(Long nutrientsId, Long betterMeId, String name, boolean isTaken) {
        this.betterMeId = betterMeId;
        this.nutrientsId = nutrientsId;
        this.name = name;
        this.isTaken = isTaken;
    }
}
