package com.betterme.service;

import com.betterme.domain.dto.workouts.WorkoutsResponseDto;
import com.betterme.domain.entity.BetterMe;
import com.betterme.domain.entity.Workouts;
import com.betterme.repository.BetterMeRepository;
import com.betterme.repository.WorkoutsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class WorkoutsService {

    private final WorkoutsRepository workoutsRepository;

    private final BetterMeRepository betterMeRepository;

    private BetterMe findBetterMe(Long betterMeId) {
        return betterMeRepository.findById(betterMeId)
                .orElseThrow(() -> new IllegalArgumentException("해당 Better Me가 존재하지 않습니다. better me id = " + betterMeId));
    }

    public List<WorkoutsResponseDto> getWorkoutsList(Long betterMeId) {
        BetterMe betterMe = findBetterMe(betterMeId);

        List<WorkoutsResponseDto> workoutsList = new ArrayList<>();

        for (Workouts workouts : betterMe.getWorkouts()) {
            workoutsList.add(new WorkoutsResponseDto(workouts));
        }

        return workoutsList;
    }
}
