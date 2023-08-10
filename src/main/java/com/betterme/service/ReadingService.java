package com.betterme.service;

import com.betterme.domain.dto.reads.ReadingResponseDto;
import com.betterme.domain.dto.reads.ReadingSaveRequestDto;
import com.betterme.domain.dto.reads.ReadingUpdateRequestDto;
import com.betterme.domain.entity.BetterMe;
import com.betterme.domain.entity.Reading;
import com.betterme.repository.BetterMeRepository;
import com.betterme.repository.ReadingRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class ReadingService {

    private final ReadingRepository readingRepository;

    private final BetterMeRepository betterMeRepository;

    private Reading findReading(Long readingId) {
        return readingRepository.findById(readingId)
                .orElseThrow(() -> new IllegalArgumentException("해당 Reads가 존재하지 않습니다. reading id = " + readingId));
    }

    private BetterMe findBetterMe(Long betterMeId) {
        return betterMeRepository.findById(betterMeId)
                .orElseThrow(() -> new IllegalArgumentException("해당 Better Me가 존재하지 않습니다. better me id = " + betterMeId));
    }

    public Long getBetterMeId(Long readingId) {
        Reading reading = findReading(readingId);

        return reading.getBetterMe().getId();
    }

    public List<ReadingResponseDto> getReadsList(Long betterMeId) {
        BetterMe betterMe = findBetterMe(betterMeId);

        List<ReadingResponseDto> readingList = new ArrayList<>();

        for (Reading reading : betterMe.getReading()) {
            readingList.add(new ReadingResponseDto(reading));
        }

        return readingList;
    }

    @Transactional
    public Long save(ReadingSaveRequestDto requestDto) {
        BetterMe betterMe = findBetterMe(requestDto.getBetterMeId());

        Long savedReadingId = readingRepository.save(requestDto.toEntity(betterMe)).getId();

        log.info("Reading is saved with reads id = " + savedReadingId);

        return savedReadingId;
    }

    public ReadingUpdateRequestDto getUpdateRequestDto(Long readingId) {
        Reading reading = findReading(readingId);

        return ReadingUpdateRequestDto.builder()
                .betterMeId(reading.getBetterMe().getId())
                .readingId(reading.getId())
                .name(reading.getName())
                .firstPage(String.valueOf(reading.getFirstPage()))
                .lastPage(String.valueOf(reading.getLastPage()))
                .summary(reading.getSummary())
                .build();
    }

    @Transactional
    public void update(Long readingId, ReadingUpdateRequestDto requestDto) {
        Reading reading = findReading(readingId);

        reading.update(requestDto.getName(), Integer.parseInt(requestDto.getFirstPage()),
                Integer.parseInt(requestDto.getLastPage()), requestDto.getSummary());

        log.info("Reading is updated with reads id = " + reading.getId());
    }

    @Transactional
    public void delete(Long readingId) {
        Reading reading = findReading(readingId);

        readingRepository.delete(reading);

        log.info("Reading is deleted with reads id = " + reading.getId());
    }
}
