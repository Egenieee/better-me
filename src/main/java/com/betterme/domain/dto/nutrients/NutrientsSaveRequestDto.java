package com.betterme.domain.dto.nutrients;

import com.betterme.domain.entity.BetterMe;
import com.betterme.domain.entity.Nutrients;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NutrientsSaveRequestDto {

    private Long betterMeId;
    private String name;

    public Nutrients toEntity(BetterMe betterMe) {
        return Nutrients.builder()
                .betterMe(betterMe)
                .name(name)
                .build();
    }
}
