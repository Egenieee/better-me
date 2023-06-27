package com.betterme.domain.dto.reads;

import com.betterme.domain.entity.Reads;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ReadsResponseDto {

    private final Long readsId;

    private final String name;
    private final int firstPage;
    private final int lastPage;

    @Builder
    public ReadsResponseDto(Reads reads) {
        this.readsId = reads.getId();
        this.name = reads.getName();
        this.firstPage = reads.getFirstPage();
        this.lastPage = reads.getLastPage();
    }
}
