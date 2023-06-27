package com.betterme.service;

import com.betterme.domain.dto.reads.ReadsResponseDto;
import com.betterme.domain.dto.reads.ReadsSaveRequestDto;
import com.betterme.domain.entity.BetterMe;
import com.betterme.domain.entity.Reads;
import com.betterme.repository.BetterMeRepository;
import com.betterme.repository.ReadsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class ReadsService {

    private final ReadsRepository readsRepository;

    private final BetterMeRepository betterMeRepository;

    private BetterMe findBetterMe(Long betterMeId) {
        return betterMeRepository.findById(betterMeId)
                .orElseThrow(() -> new IllegalArgumentException("해당 Better Me가 존재하지 않습니다. better me id = " + betterMeId));
    }

    public List<ReadsResponseDto> getReadsList(Long betterMeId) {
        BetterMe betterMe = findBetterMe(betterMeId);

        List<ReadsResponseDto> readsList = new ArrayList<>();

        for (Reads reads : betterMe.getReads()) {
            readsList.add(new ReadsResponseDto(reads));
        }

        return readsList;
    }

    public Long save(ReadsSaveRequestDto requestDto) {
        BetterMe betterMe = findBetterMe(requestDto.getBetterMeId());

        Long savedReadsId = readsRepository.save(requestDto.toEntity(betterMe)).getId();

        log.info("Reads is saved with reads id = " + savedReadsId);

        return savedReadsId;
    }
}
