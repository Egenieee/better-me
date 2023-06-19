package com.betterme.service;

import com.betterme.domain.dto.nutrients.NutrientsResponseDto;
import com.betterme.domain.dto.nutrients.NutrientsSaveRequestDto;
import com.betterme.domain.entity.BetterMe;
import com.betterme.domain.entity.Nutrients;
import com.betterme.repository.BetterMeRepository;
import com.betterme.repository.NutrientsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class NutrientsService {

    private final NutrientsRepository nutrientsRepository;

    private final BetterMeRepository betterMeRepository;

    private BetterMe findBetterMe(Long betterMeId) {
        return betterMeRepository.findById(betterMeId)
                .orElseThrow(() -> new IllegalArgumentException("해당 BetterMe가 존재하지 않습니다. better me id = " + betterMeId));
    }

    @Transactional
    public Long save(NutrientsSaveRequestDto requestDto) {
        BetterMe betterMe = findBetterMe(requestDto.getBetterMeId());

        Long savedNutrientsId = nutrientsRepository.save(requestDto.toEntity(betterMe)).getId();

        log.info("Nutrients is saved with nutrients id = " + savedNutrientsId);

        return savedNutrientsId;
    }

    public List<NutrientsResponseDto> getNutrientsList(Long betterMeId) {
        BetterMe betterMe = findBetterMe(betterMeId);

        List<NutrientsResponseDto> nutrientsList = new ArrayList<>();

        for (Nutrients nutrients : betterMe.getNutrients()) {
            nutrientsList.add(new NutrientsResponseDto(nutrients));
        }

        return nutrientsList;
    }
}
