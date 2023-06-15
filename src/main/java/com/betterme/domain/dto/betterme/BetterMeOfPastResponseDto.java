package com.betterme.domain.dto.betterme;

import com.betterme.domain.entity.BetterMe;
import lombok.Builder;
import lombok.Getter;

import java.time.format.DateTimeFormatter;

@Getter
public class BetterMeOfPastResponseDto {

    private final Long betterMeId;
    private final String date;

    //추후 성취도 추가

    @Builder
    public BetterMeOfPastResponseDto(BetterMe betterMe) {
        this.betterMeId = betterMe.getId();
        this.date = betterMe.getCreatedDate().format(DateTimeFormatter.ofPattern("yyyy년 MM월 dd일"));
    }
}
