package com.betterme.domain.dto.reads;

import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Getter;
import org.springframework.format.annotation.NumberFormat;

@Getter
public class ReadingUpdateRequestDto {

    private final Long betterMeId;
    private final Long readingId;

    private final String name;

    @NumberFormat
    @Pattern(regexp = "^\\d+$", message = "읽기 시작한 페이지는 숫자 형식으로 입력해주세요.")
    private final String firstPage;

    @NumberFormat
    @Pattern(regexp = "^\\d+$", message = "읽은 마지막 페이지는 숫자 형식으로 입력해주세요.")
    private final String lastPage;

    private final String summary;

    @Builder
    public ReadingUpdateRequestDto(Long readingId, Long betterMeId, String name, String firstPage, String lastPage, String summary) {
        this.betterMeId = betterMeId;
        this.readingId = readingId;
        this.name = name;
        this.firstPage = firstPage;
        this.lastPage = lastPage;
        this.summary = summary;
    }
}
