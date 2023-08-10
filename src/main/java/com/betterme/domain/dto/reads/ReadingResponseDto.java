package com.betterme.domain.dto.reads;

import com.betterme.domain.entity.Reading;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ReadingResponseDto {

    private final Long readingId;

    private final String name;
    private final int firstPage;
    private final int lastPage;

    @Builder
    public ReadingResponseDto(Reading reading) {
        this.readingId = reading.getId();
        this.name = reading.getName();
        this.firstPage = reading.getFirstPage();
        this.lastPage = reading.getLastPage();
    }
}
