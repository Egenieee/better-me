package com.betterme.domain.dto.nutrients;

import com.betterme.domain.entity.Nutrients;
import lombok.Builder;
import lombok.Getter;

@Getter
public class NutrientsResponseDto {

    private final Long nutrientsId;
    private final String name;
    private final boolean isTaken;

    @Builder
    public NutrientsResponseDto(Nutrients nutrients) {
        this.nutrientsId = nutrients.getId();
        this.name = nutrients.getName();
        this.isTaken = nutrients.isTaken();
    }
}
