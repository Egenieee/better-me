package com.betterme.service;

import com.betterme.domain.dto.sleeps.SleepsResponseDto;
import com.betterme.domain.dto.sleeps.SleepsSaveRequestDto;
import com.betterme.domain.dto.sleeps.SleepsUpdateRequestDto;
import com.betterme.domain.entity.BetterMe;
import com.betterme.domain.entity.Sleeps;
import com.betterme.repository.BetterMeRepository;
import com.betterme.repository.SleepsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@Slf4j
@RequiredArgsConstructor
@Service
public class SleepsService {

    private final SleepsRepository sleepsRepository;

    private final BetterMeRepository betterMeRepository;

    private Sleeps findSleeps(Long sleepsId) {
        return sleepsRepository.findById(sleepsId)
                .orElseThrow(() -> new IllegalArgumentException("해당 Sleeps가 존재하지 않습니다. sleeps id = " + sleepsId));
    }

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

    public Long getBetterMeId(Long sleepsId) {
        Sleeps sleeps = findSleeps(sleepsId);

        return sleeps.getBetterMe().getId();
    }

    @Transactional
    public Long save(SleepsSaveRequestDto requestDto) {
        BetterMe betterMe = findBetterMe(requestDto.getBetterMeId());

        Sleeps sleeps = requestDto.toEntity(betterMe);

        Long savedSleepsId = sleepsRepository.save(sleeps).getId();

        log.info("sleeps is saved with sleeps id = " + savedSleepsId);

        return savedSleepsId;
    }

    public SleepsResponseDto getSleepsResponseDto(Long sleepsId) {
        Sleeps sleeps = findSleeps(sleepsId);

        return new SleepsResponseDto(sleeps);
    }

    public SleepsUpdateRequestDto getSleepsUpdateRequestDto(Long sleepsId) {
        Sleeps sleeps = findSleeps(sleepsId);

        return SleepsUpdateRequestDto.builder()
                .sleepsId(sleeps.getId())
                .sleepDate(sleeps.getSleepTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")))
                .sleepTime(sleeps.getSleepTime().format(DateTimeFormatter.ofPattern("HH:mm")))
                .wakeUpDate(sleeps.getWakeUpTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")))
                .wakeUpTime(sleeps.getWakeUpTime().format(DateTimeFormatter.ofPattern("HH:mm")))
                .isSuccess(sleeps.isSuccess())
                .build();
    }

    @Transactional
    public void update(SleepsUpdateRequestDto requestDto) {
        Sleeps sleeps = findSleeps(requestDto.getSleepsId());

        LocalDateTime sleepDateTime = LocalDate.parse(requestDto.getSleepDate()).atTime(LocalTime.parse(requestDto.getSleepTime()));
        LocalDateTime wakeUpDateTime = LocalDate.parse(requestDto.getWakeUpDate()).atTime(LocalTime.parse(requestDto.getWakeUpTime()));

        sleeps.update(sleepDateTime, wakeUpDateTime, requestDto.getIsSuccess());

        log.info("sleeps is updated with sleeps id = " + requestDto.getSleepsId());
    }

    @Transactional
    public void delete(Long sleepsId) {
        Sleeps sleeps = findSleeps(sleepsId);

        sleepsRepository.delete(sleeps);

        log.info("sleeps is deleted with sleeps id = " + sleepsId);
    }
}
