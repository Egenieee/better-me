package com.betterme.domain.dto.reads;

import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Getter;
import org.springframework.format.annotation.NumberFormat;

@Getter
public class ReadsUpdateRequestDto {

    private final Long betterMeId;
    private final Long readsId;

    private final String name;

    @NumberFormat
    @Pattern(regexp = "^\\d+$", message = "숫자로 입력해주세요.")
    private final String firstPage;

    @NumberFormat
    @Pattern(regexp = "^\\d+$", message = "숫자로 입력해주세요.")
    private final String lastPage;

    private final String summary;

    @Builder
    public ReadsUpdateRequestDto(Long readsId, Long betterMeId, String name, String firstPage, String lastPage, String summary) {
        this.betterMeId = betterMeId;
        this.readsId = readsId;
        this.name = name;
        this.firstPage = firstPage;
        this.lastPage = lastPage;
        this.summary = summary;
    }
}
