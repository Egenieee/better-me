package com.betterme.service;

import com.betterme.domain.entity.BetterMe;
import com.betterme.repository.BetterMeRepository;
import com.betterme.repository.SleepsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

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

}
