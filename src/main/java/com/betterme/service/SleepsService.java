package com.betterme.service;

import com.betterme.domain.dto.sleeps.SleepsSaveRequestDto;
import com.betterme.domain.entity.BetterMe;
import com.betterme.domain.entity.Sleeps;
import com.betterme.repository.BetterMeRepository;
import com.betterme.repository.SleepsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Service
public class SleepsService {

    private final SleepsRepository sleepsRepository;

    private final BetterMeRepository betterMeRepository;

    private BetterMe findBetterMe(Long betterMeId) {
        return betterMeRepository.findById(betterMeId)
                .orElseThrow(() -> new IllegalArgumentException("해당 Better Me가 존재하지 않습니다. id = " + betterMeId));
    }

    public boolean hasSleeps(String betterMeId) {
        BetterMe betterMe = findBetterMe(Long.parseLong(betterMeId));

        return betterMe.getSleeps() != null;
    }

    public Long getSleepsId(String betterId) {
        BetterMe betterMe = findBetterMe(Long.parseLong(betterId));

        return betterMe.getSleeps().getId();
    }

    @Transactional
    public Long save(SleepsSaveRequestDto requestDto) {
        BetterMe betterMe = findBetterMe(requestDto.getBetterMeId());

        Sleeps sleeps = requestDto.toEntity(betterMe);

        Long savedSleepsId = sleepsRepository.save(sleeps).getId();

        log.info("sleeps is saved with sleeps id = " + savedSleepsId);

        return savedSleepsId;
    }


}
